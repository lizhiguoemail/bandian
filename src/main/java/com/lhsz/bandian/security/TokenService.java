package com.lhsz.bandian.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.config.properties.BandianProperties;
import com.lhsz.bandian.sys.entity.Application;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IApplicationService;
import com.lhsz.bandian.sys.service.IUserService;
import com.lhsz.bandian.utils.*;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lizhiguo
 * 2020/7/6 16:37
 */
@Slf4j
@Component
public class TokenService {


    @Autowired
    private RedisCache redisCache;
    /**
     * 用户名称
     */
    private  final String USERNAME = Claims.SUBJECT;
    /**
     * 创建时间
     */
    private  final String CREATED = "created";
    /**
     * 权限列表
     */
    private  final String AUTHORITIES = "authorities";
    /**
     * 登陆次数
     */
    private static final String LOGINCOUNT = "loginCount";

    /**
     * 密钥
     */
    private  final String SECRET = "com.lhsz.bandian.lzg";
    @Value("${token.expireTime}")
    private int expireTime;
    @Value("${token.header}")
    private String header;
    /**
     * 有效期12小时
     */
//    private  final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private IApplicationService applicationService;
    @Autowired
    private IUserService userService;

    /**
     * 获取登录用户实体
     *
     * @return 用户信息
     */
    public User getLoginUserToUser()
    {
        LoginUser user = getLoginUser(HttpUtils.getHttpServletRequest());
        return user.getUser();
    }

    /**
     * 获取登录用户实体
     *
     * @return 用户信息
     */
    public Application getLoginUserToApp()
    {
        LoginUser user = getLoginUser(HttpUtils.getHttpServletRequest());
        return user.getApplication();
    }
    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)
    {
        String token = getToken(request);
        return getLoginUser(token);
    }
    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser()
    {
        return getLoginUser(HttpUtils.getHttpServletRequest());
    }
    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String token)
    {
        // 获取请求携带的令牌
//        String token = getToken(request);

        if (StringUtils.isNotEmpty(token))
        {
           /* Claims claims =  getClaimsFromToken(token);
            String username = (String) claims.get(USERNAME);
            int loginCount = (int) claims.get(LOGINCOUNT);
            return redisCache.getCacheObject(createRedisKey(username,loginCount));*/
            return redisCache.getCacheObject(getRedisKey(token));

        }
        return null;
    }


    /**
     * 删除用户身份信息
     */
    void delLoginUser(LoginUser loginUser)
    {
        if (loginUser!=null)
        {
            String redisTokenId = createRedisKey(loginUser.getUsername(),loginUser.getUser().getLoginCount());
            redisCache.deleteObject(redisTokenId);
        }
    }
    private String createRedisKey(String name,int login_count){
        return  name+"."+login_count;  //2020-7-17 去掉时间，直接用username作为键，方便统一控制登录用户token
//        return  name; 2020年8月11日 还原之前的设置，解决登陆会被其他人注销的BUG
        //#true 同一个账号只能登陆一次，false 同一个账号可以同时登陆

    }
    /**
     * 更新缓存
     * 更新当前登陆用户缓存
     */

    public void updateLoginUserToUser(){
        User user=userService.findByUsername(getUsername());
        LoginUser loginUser=redisCache.getCacheObject(getRedisKey());
        if(loginUser!=null){
            loginUser.setUser(user);
            addRadisForLoginUser(getRedisKey(),loginUser);
            log.info("用户 {} 缓存已更新"+user.getUserName());
        }else{
            log.info("用户 {} 缓存失败,缓存不存在或已过期"+user.getUserName());
        }

    }
    /**
     * 更新缓存
     * 更新指定用户缓存
     * @param user 指定用户对象
     */

    public void updateLoginUserToUser(User user){
        //单用户登陆时才生效，允许多处登陆时，不生效
        if(BandianProperties.isSingleLogin){
            LoginUser loginUser=redisCache.getCacheObject(createRedisKey(user.getUserName(),user.getLoginCount()));
            if(loginUser!=null){
                loginUser.setUser(user);
                addRadisForLoginUser(createRedisKey(user.getUserName(),user.getLoginCount()),loginUser);
                log.info("用户{}缓存已更新"+user.getUserName());
            }else{
                log.info("用户{}缓存失败,缓存不存在或已过期"+user.getUserName());
            }
        }
    }


    /**
     * 加入把登录信息加入redis缓存
     * @param redisTokenId redisKey
     * @param loginUser 登陆对象
     */
    private void addRadisForLoginUser(String redisTokenId, LoginUser loginUser) {
        redisCache.setCacheObject(redisTokenId,loginUser, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser)
    {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 生成令牌
     *
     * @param authentication 用户
     * @return 令牌
     */
    String createToken(Authentication authentication, String clientId) {
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        Long createtime=System.currentTimeMillis();
        setUserAgent(loginUser);
        loginUser.setLoginTime(createtime);
        loginUser.setExpireTime(createtime+expireTime);
        String token=generateToken(loginUser.getUsername(),createtime,authentication.getAuthorities(),loginUser.getUser().getLoginCount());//生成token
        loginUser.setToken(token);
        setApplication(loginUser,clientId);
        if(BandianProperties.isSingleLogin){
            redisCache.deleteObject(createRedisKey(loginUser.getUsername(),loginUser.getUser().getLoginCount()-1));
        }
        addRadisForLoginUser(createRedisKey(loginUser.getUsername(),loginUser.getUser().getLoginCount()), loginUser);
        return token;
    }

    private void setApplication(LoginUser loginUser, String clientId) {
        if(clientId==null || "".equals(clientId.trim())){
            throw new NoticeException("ClientId 不能为空");
        }
        Application application = applicationService.getOne(new QueryWrapper<Application>().eq("code",clientId));
        if(application==null){
            throw new NoticeException("ClientId 错误");
        }
        if(!clientId.equals(loginUser.getUser().getClientId())){
            throw new NoticeException("登录失败！用户不存在！");
        }
        loginUser.setApplication(application);
    }

    /**
     * 生成令牌
     * @param userName 用户名
     * @param created 创建时间
     * @param authorities 权限
     * @return 令牌
     */
    private String generateToken(String userName, Long created, Collection<? extends GrantedAuthority> authorities, int loginCount) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(USERNAME, userName);
        claims.put(CREATED, created);
//        claims.put(AUTHORITIES, authorities);
        claims.put(LOGINCOUNT, loginCount);
        return generateToken(claims);
    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private  String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expireTime);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    private String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 根据请求令牌获取登录认证信息
     * @param request 上下文
     * @return 用户名
     */
    Authentication getAuthenticationeFromToken(HttpServletRequest request) {
        Authentication authentication = null;
        // 获取请求携带的令牌
        String token = getToken(request);
        if(token != null) {
            // 请求令牌不能为空
            if(securityService.getAuthentication() == null) {
                // 上下文中Authentication为空
                Claims claims = getClaimsFromToken(token);
                if(claims == null) {
                    return null;
                }
                String username = claims.getSubject();
                if(username == null) {
                    return null;
                }
                if(isTokenExpired(token)) {
                    return null;
                }
//              2020年8月14日  400 Bad Request 权限数据太多放到token中会报错
               /* Object authors = claims.get(AUTHORITIES);
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (authors instanceof List) {
                    for (Object object : (List) authors) {
                        authorities.add(new GrantedAuthorityImpl((String) ((Map) object).get("authority")));
                    }
                }*/
              LoginUser loginUser= redisCache.getCacheObject(createRedisKey(claims.get(USERNAME)+"",Integer.parseInt(claims.get(LOGINCOUNT)+"")));
//                User user=userService.findByUsername(username); 2020年7月23日 还是不要放入user，直接放入username,然后通过username从redis中获取LoginUser
                authentication = new JwtAuthenticatioToken(username, null, loginUser.getGrantedAuthorities(), token);//username 改为user 2020年7月23日 lizhiguo
            } else {
                if(validateToken(token, securityService.getUsername())) {
                    // 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
                    authentication = securityService.getAuthentication();
                }
            }
            if(authentication !=null){
                LoginUser loginUser=getLoginUser(token);
                if(loginUser==null){
                    authentication=null;
                    /*if(isSingleLogin){
                        throw new InsufficientAuthenticationException("账号在别处登陆或已注销！如有疑问，请联系管理员。");
                    }*/
                }
            }

        }

        return authentication;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 验证令牌
     * @param token token令牌
     * @param username 用户名
     * @return boolen
     */
    private Boolean validateToken(String token, String username) {
        String userName = getUsernameFromToken(token);
        if(userName==null)
        {
            throw new NoticeException("令牌过期");
        }
        return (userName.equals(username) && !isTokenExpired(token));
    }

    /**
     * 刷新令牌
     * @param token 旧令牌
     * @return 新的令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            Long createtime=System.currentTimeMillis();
            claims.put(CREATED, createtime);
            int loginCount = (int) claims.get(LOGINCOUNT);
            refreshedToken = generateToken(claims);
            LoginUser loginUser = getLoginUser(token);
            loginUser.setLoginTime(createtime);
            loginUser.setExpireTime(createtime+expireTime);
            addRadisForLoginUser(createRedisKey(loginUser.getUsername(),loginCount),loginUser);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    private String getRedisKey(String token)
    {
        Claims claims = getClaimsFromToken(token);
        int loginCount = (int) claims.get(LOGINCOUNT);
        String userName = (String) claims.get(USERNAME);
        return createRedisKey(userName,loginCount);
    }
    private String getRedisKey()
    {
       return getRedisKey(getToken());
    }
    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取请求token
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        String tokenHead = "Bearer ";
        if(token == null) {
            token = request.getHeader("token");
        } else if(token.contains(tokenHead)){
            token = token.substring(tokenHead.length());
        }
        if("".equals(token)) {
            token = null;
        }
        return token;
    }
    /**
     * 获取请求token
     * @return token
     */
    private String getToken() {
        return getToken(HttpUtils.getHttpServletRequest());
    }
    public String getUsername(){
       return securityService.getUsername();
    }
}
