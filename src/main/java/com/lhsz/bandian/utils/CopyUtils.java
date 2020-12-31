package com.lhsz.bandian.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author lizhiguo
 * 2020/7/27 10:57
 */
public class CopyUtils {

   public static void copyProperties(Object source,Object target){
       BeanUtils.copyProperties(source,target);
//       copy(source,target);
    }
    private static void copy(Object source, Object target) {
        BeanCopier copier=BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }
/*    public static void copy(Object srcObj, Object destObj) {
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier = null;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        copier.copy(srcObj, destObj, null);

    }
    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }*/

}
