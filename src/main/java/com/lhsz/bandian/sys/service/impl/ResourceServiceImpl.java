package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.sys.entity.Resource;
import com.lhsz.bandian.sys.mapper.ResourceMapper;
import com.lhsz.bandian.sys.service.IPermissionService;
import com.lhsz.bandian.sys.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    IPermissionService permissionService;
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public boolean del(String id) {
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("resource_id",id);
       boolean b1= permissionService.remove(queryWrapper);
       boolean b2= this.removeById(id);
       if(b2){
           return true;
       }
        return false;
    }
}
