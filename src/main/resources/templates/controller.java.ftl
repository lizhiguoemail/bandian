package ${package.Controller};

import com.lhsz.bandian.DTO.HttpResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import com.lhsz.bandian<#if package.ModuleName??>.${package.ModuleName}</#if>.service.${table.serviceName};
import com.lhsz.bandian<#if package.ModuleName??>.${package.ModuleName}</#if>.entity.${entity};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*方法命名规范：
*1、listPage 表示分页查询
*2、list 不分页
*3、add、update、delete 开头的方法系统自动记录操作日志
*4、不自动记录日志的方法想要记录日志使用OperLog
* @author ${author}
* @since ${date}
* @version v1.0
*/
<#if restControllerStyle>
    @Api(tags = {"${table.comment!}"})
    @RestController
<#else>
    @Controller
</#if>
    @RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
    public class ${table.controllerName} extends ${superControllerClass} {
<#else>
    public class ${table.controllerName} {
</#if>
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};

    /**
    * 查询分页数据
    */
    @ApiOperation(value = "查询分页数据")
    @GetMapping()
    public HttpResult findListByPage(){
        startPage();
        List<${entity}> list=${(table.serviceName?substring(1))?uncap_first}.list();
        return HttpResult.ok(getDataTable(list));
    }


    /**
    * 根据id查询
    */
    @ApiOperation(value = "根据id查询数据")
    @GetMapping("/{id}")
    public HttpResult getById(@PathVariable("id") String id){
        return HttpResult.ok(${(table.serviceName?substring(1))?uncap_first}.getById(id));
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增数据")
    @PostMapping()
    public HttpResult add(@RequestBody ${entity} ${entity?uncap_first}){
        if( ${(table.serviceName?substring(1))?uncap_first}.save(${entity?uncap_first})){
            return HttpResult.succee();
        }else {
            return HttpResult.fail();
        }
    }

    /**
    * 根据ID删除用户
    * @param id 用户ID
    * @return 成功或失败
    */
    @PreAuthorize("hasAuthority('<#if package.ModuleName??>${package.ModuleName}:</#if>${entity?uncap_first}:delete')")
    @DeleteMapping("/{id}")
    public HttpResult deleteById(@PathVariable("id") String id){
        if(${(table.serviceName?substring(1))?uncap_first}.removeById(id)){
            return HttpResult.succee();
        }else {
            return HttpResult.fail();
        }
    }

    /**
    * 删除
    */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/delete")
    public HttpResult delete(@RequestParam("ids") List<String> ids){
        if(${(table.serviceName?substring(1))?uncap_first}.removeByIds(ids)){
             return HttpResult.succee();
        }else{
             return HttpResult.fail();
        }
    }
    /**
    * 删除 目前的前端使用此方法
    */
    @ApiOperation(value = "删除数据")
    @RequestMapping(value = "/delete")
    public HttpResult deleteByIds(@RequestBody() String ids){
        ids= ids.replace("\"", "");
        String[] split = ids.split(",");
        if(${(table.serviceName?substring(1))?uncap_first}.removeByIds(Arrays.asList(split))){
            return HttpResult.succee();
        }else{
            return HttpResult.fail();
        }
    }

    /**
    * 修改
    */
    @ApiOperation(value = "更新数据")
    @PutMapping()
    public HttpResult update(@RequestBody ${entity} ${entity?uncap_first}){
        if(${(table.serviceName?substring(1))?uncap_first}.updateById(${entity?uncap_first})){
             return HttpResult.succee();
        }else{
             return HttpResult.fail();
        }
    }

}
</#if>