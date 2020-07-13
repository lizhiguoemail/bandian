package com.lhsz.bandian.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lhsz.bandian.sys.entity.Application;
import com.lhsz.bandian.sys.entity.User;
import com.lhsz.bandian.sys.service.IApplicationService;
import com.lhsz.bandian.utils.*;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
//        String token = getToken(request);
        String token = getToken(request);

        if (StringUtils.isNotEmpty(token))
        {

            Claims claims =  getClaimsFromToken(token);
            String username = (String) claims.get(USERNAME);
            Long createtime = (Long) claims.get(CREATED);
            LoginUser user = redisCache.getCacheObject(setRedisTokenId(username,createtime));
            return user;
        }
        return null;
    }
    /**
     * 获取登录用户实体
     *
     * @return 用户信息
     */
    public User getLoginUserToUser()
    {
        String token = getToken(HttpUtils.getHttpServletRequest());
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims =  getClaimsFromToken(token);
            String username = (String) claims.get(USERNAME);
            Long createtime = (Long) claims.get(CREATED);
            LoginUser user = redisCache.getCacheObject(setRedisTokenId(username,createtime));
            return user.getUser();
        }
        return null;
    }
    /**
     * 获取登录用户实体
     *
     * @return 用户信息
     */
    public Application getLoginUserToApp()
    {
        String token = getToken(HttpUtils.getHttpServletRequest());
        if (StringUtils.isNotEmpty(token))
        {
            Claims claims =  getClaimsFromToken(token);
            String username = (String) claims.get(USERNAME);
            Long createtime = (Long) claims.get(CREATED);
            LoginUser user = redisCache.getCacheObject(setRedisTokenId(username,createtime));
            return user.getApplication();
        }
        return null;
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

            Claims claims =  getClaimsFromToken(token);
            String username = (String) claims.get(USERNAME);
            Long createtime = (Long) claims.get(CREATED);
            LoginUser user = redisCache.getCacheObject(setRedisTokenId(username,createtime));
            return user;
        }
        return null;
    }


    /**
     * 删除用户身份信息
     */
    public void delLoginUser(LoginUser loginUser)
    {
        if (loginUser!=null)
        {
            String redisTokenId = setRedisTokenId(loginUser.getUsername(),loginUser.getLoginTime());
            redisCache.deleteObject(redisTokenId);
        }
    }
    private String setRedisTokenId(String name,Long createtime){
        return  name+"."+createtime;

    }


    /**
     * 加入把登录信息加入redis缓存
     * @param redisTokenId
     * @param loginUser
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
    public  String createToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(3);
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        Long createtime=System.currentTimeMillis();
        claims.put(USERNAME, loginUser.getUsername());
        claims.put(CREATED, createtime);
        claims.put(AUTHORITIES, loginUser.getAuthorities());
        setUserAgent(loginUser);
        loginUser.setLoginTime(createtime);
        loginUser.setExpireTime(createtime+expireTime);
        String token=generateToken(claims);//生成token
        loginUser.setToken(token);

        addRadisForLoginUser(setRedisTokenId(loginUser.getUsername(),createtime), loginUser);
        return token;
    }
    /**
     * 生成令牌
     *
     * @param authentication 用户
     * @return 令牌
     */
    public  String createToken(Authentication authentication,String clientId) {
        Map<String, Object> claims = new HashMap<>(3);
        LoginUser loginUser=(LoginUser) authentication.getPrincipal();
        Long createtime=System.currentTimeMillis();
        claims.put(USERNAME, loginUser.getUsername());
        claims.put(CREATED, createtime);
        claims.put(AUTHORITIES, loginUser.getAuthorities());
        setUserAgent(loginUser);
        loginUser.setLoginTime(createtime);
        loginUser.setExpireTime(createtime+expireTime);
        String token=generateToken(claims);//生成token
        loginUser.setToken(token);
        HttpServletRequest request = HttpUtils.getHttpServletRequest();
        if(clientId==null || "".equals(clientId.trim())){
            throw new UsernameNotFoundException("ClientId 不能为空");
        }
        Application application = applicationService.getOne(new QueryWrapper<Application>().eq("code",clientId));
        if(application==null){
            throw new UsernameNotFoundException("ClientId 错误");
        }
        loginUser.setApplication(application);
        addRadisForLoginUser(setRedisTokenId(loginUser.getUsername(),createtime), loginUser);
        return token;
    }
    /**
     * 生成令牌
     *
     * @param authentication 用户
     * @return 令牌
     */
    public  String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(USERNAME, securityService.getUsername(authentication));
        claims.put(CREATED, new Date());
        claims.put(AUTHORITIES, authentication.getAuthorities());
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
    public  String getUsernameFromToken(String token) {
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
     * @param token 令牌
     * @return 用户名
     */
    public  Authentication getAuthenticationeFromRedis(HttpServletRequest request) {
        Authentication authentication = null;
        // 获取请求携带的令牌
      LoginUser loginUser =  getLoginUser(request);
      if(loginUser!=null){

      }
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
                Object authors = claims.get(AUTHORITIES);
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                if (authors != null && authors instanceof List) {
                    for (Object object : (List) authors) {
                        authorities.add(new GrantedAuthorityImpl((String) ((Map) object).get("authority")));
                    }
                }
                authentication = new JwtAuthenticatioToken(username, null, authorities, token);
            } else {
                if(validateToken(token, securityService.getUsername())) {
                    // 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
                    authentication = securityService.getAuthentication();
                }
            }
        }
        return authentication;
    }

    /**
     * 根据请求令牌获取登录认证信息
     * @param token 令牌
     * @return 用户名
     */
    public  Authentication getAuthenticationeFromToken(HttpServletRequest request) {
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
                Object authors = claims.get(AUTHORITIES);
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                if (authors != null && authors instanceof List) {
                    for (Object object : (List) authors) {
                        authorities.add(new GrantedAuthorityImpl((String) ((Map) object).get("authority")));
                    }
                }
                authentication = new JwtAuthenticatioToken(username, null, authorities, token);
            } else {
                if(validateToken(token, securityService.getUsername())) {
                    // 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
                    authentication = securityService.getAuthentication();
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
    public Claims getClaimsFromToken(String token) {
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
     * @param token
     * @param username
     * @return
     */
    public Boolean validateToken(String token, String username) {
        String userName = getUsernameFromToken(token);
        if(userName==null)
        {
            throw new RuntimeException("令牌过期");
        }
        return (userName.equals(username) && !isTokenExpired(token));
    }

    /**
     * 刷新令牌
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            Long createtime=System.currentTimeMillis();
            claims.put(CREATED, createtime);
            refreshedToken = generateToken(claims);
            LoginUser loginUser = getLoginUser(token);
            loginUser.setLoginTime(createtime);
            loginUser.setExpireTime(createtime+expireTime);
            addRadisForLoginUser(setRedisTokenId(loginUser.getUsername(),createtime),loginUser);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    private String getRedisTokenId(String token)
    {
        LoginUser loginUser=getLoginUser(token);
        Claims claims = getClaimsFromToken(token);
        Long cratetime=(Long)claims.get(CREATED);
        String redisTokenId=setRedisTokenId(loginUser.getUsername(),cratetime);
        return redisTokenId;
    }
    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public  Boolean isTokenExpired(String token) {
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
    public  String getToken(HttpServletRequest request) {
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
}
