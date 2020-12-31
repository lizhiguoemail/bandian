package com.lhsz.bandian.sys.DTO;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lhsz.bandian.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lizhiguo
 * 2020/7/20 14:42
 */
public class Extend {
    private String icon;
    private boolean expanded;
    private String method;

    public Extend(String icon,boolean expanded,String method){
        this.icon=icon;
        this.expanded=expanded;
        this.method=method;
    }

    public  Extend(String jsonStirng){
        Map map= JSONObject.parseObject(jsonStirng);
        if(map!=null){
            this.icon=map.get("Icon")+"";
            if(map.get("Expanded")==null||"null".equals(map.get("Expanded"))||"NULL".equals(map.get("Expanded"))){
                this.expanded=false;
            }
            else{
                this.expanded= (boolean) map.get("Expanded");
            }

            this.method=map.get("Method")+"";
        }
    }

    public String toJson(){
      Map map =new HashMap();
        map.put("Icon",this.icon);
        map.put("Expanded",this.expanded);
        map.put("Method",this.method);
        return JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
    }

    public String getIcon() {
        return icon;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getMethod() {
        return method;
    }
}
