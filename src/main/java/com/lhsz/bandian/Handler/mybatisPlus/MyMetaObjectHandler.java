package com.lhsz.bandian.Handler.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lhsz.bandian.security.LoginUser;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author lizhiguo
 * 2020/7/8 15:32
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 创建时间
     */
    private String CREATIONTIME ="creationTime";

    /**
     * 创建人编号
     */
    private String CREATORID="creatorId";

    /**
     * 最后修改时间
     */
    private String LASTMODIFICATIONTIME="lastModificationTime";

    /**
     * 最后修改人编号
     */
    private String LASTMODIFIERID="lastModifierId";

    @Autowired
    private TokenService tokenService;

    private boolean check(MetaObject metaObject,String attributeName){
        boolean bool = false;
        if(metaObject.hasSetter(attributeName)){//判断字段是否存在
            Object object = getFieldValByName(attributeName, metaObject);
            if(object == null ){ // 先判断该值是否为空，为空才填充
                bool= true;
            }
        }
        return bool;
    }
    private String getLoginUserId(){
        String uId=null;
        try {
            LoginUser loginUser=tokenService.getLoginUser(ServletUtils.getRequest());
            if(loginUser!=null&&loginUser.getUser()!=null) {
                uId=loginUser.getUser().getUserId();
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return uId;
    }
    @Override
    public void insertFill(MetaObject metaObject) {
        //判断字段是否存在
        if(check(metaObject,CREATIONTIME)){
            strictInsertFill(metaObject,CREATIONTIME,LocalDateTime.class,LocalDateTime.now());
        }
        if(check(metaObject,CREATORID)){
            strictInsertFill(metaObject,CREATORID,String.class ,getLoginUserId());
        }
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        if(check(metaObject,LASTMODIFICATIONTIME)){
            strictUpdateFill(metaObject,LASTMODIFICATIONTIME,LocalDateTime.class, LocalDateTime.now());
        }
        if(check(metaObject,LASTMODIFIERID)){
            strictUpdateFill(metaObject,LASTMODIFIERID,String.class, getLoginUserId());

        }
    }

}
