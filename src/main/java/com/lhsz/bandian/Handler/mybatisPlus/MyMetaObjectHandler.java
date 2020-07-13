package com.lhsz.bandian.Handler.mybatisPlus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lhsz.bandian.pojo.HttpResult;
import com.lhsz.bandian.security.LoginUser;
import com.lhsz.bandian.security.SecurityService;
import com.lhsz.bandian.security.TokenService;
import com.lhsz.bandian.utils.HttpUtils;
import com.lhsz.bandian.utils.ServletUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author lizhiguo
 * 2020/7/8 15:32
 */
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
            uId=loginUser.getUser().getUserId();
        }catch (Exception e){
            try {
                HttpUtils.write(ServletUtils.getResponse(), e.getMessage());
            }catch (Exception e1){
                e1.printStackTrace();
            }

        }
        return uId;
    }
    @Override
    public void insertFill(MetaObject metaObject) {
        //判断字段是否存在
        if(check(metaObject,CREATIONTIME)){
            setInsertFieldValByName(CREATIONTIME, LocalDateTime.now(), metaObject);
        }
        if(check(metaObject,CREATORID)){
            setInsertFieldValByName(CREATORID, getLoginUserId(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if(check(metaObject,LASTMODIFICATIONTIME)){
            setUpdateFieldValByName(LASTMODIFICATIONTIME, LocalDateTime.now(), metaObject);
        }
        if(check(metaObject,LASTMODIFIERID)){
            setUpdateFieldValByName(LASTMODIFIERID, getLoginUserId(), metaObject);
        }
    }

}
