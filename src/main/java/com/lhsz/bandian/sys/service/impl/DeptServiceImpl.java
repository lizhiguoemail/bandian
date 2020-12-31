package com.lhsz.bandian.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.DTO.query.QueryDeptDTO;
import com.lhsz.bandian.sys.DTO.result.DeptDTO;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import com.lhsz.bandian.sys.DTO.result.TreeDataDTO;
import com.lhsz.bandian.sys.entity.Dept;
import com.lhsz.bandian.sys.mapper.DeptMapper;
import com.lhsz.bandian.sys.service.IDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-10
 */
@Slf4j
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Override
    public TreeDTO getTreeDTO() {
        TreeDTO treeDTO=new TreeDTO();
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("path,sort_id");
        List<TreeDataDTO> list=convertTree(list(queryWrapper));
        if(list.size()>0){
            treeDTO.setExpandedKeys(list.get(0).getId());
        }
        treeDTO.setNodes(list);
        return treeDTO;
    }

    @Override
    public List<DeptDTO> listDTO(QueryDeptDTO queryDeptDTO) {
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        if(queryDeptDTO.getName()!=null&&!"".equals(queryDeptDTO.getName().trim()))
        {
            queryWrapper.like("name",queryDeptDTO.getName());
        }
        queryWrapper.eq("enabled",queryDeptDTO.isEnabled());
        try {
            List<Dept> list1 = list(queryWrapper);
            List<Dept> list = addParentDept(list1);
            return convertList(list);
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    private List<DeptDTO> convertList(List<Dept> list) {
        List<DeptDTO> dtoList=new ArrayList<>();
        list.forEach(dept -> {
            DeptDTO deptDTO=new DeptDTO();
            BeanUtils.copyProperties(dept,deptDTO);
            deptDTO.setId(dept.getDeptId());
            deptDTO.setLeaf(!checkLeaf(dept.getDeptId(),list));
            dtoList.add(deptDTO);
        });
        return dtoList;
    }


    private List<TreeDataDTO> convertTree(List<Dept> list) {
        List<TreeDataDTO> deptDTOList=new ArrayList<>();
        list.forEach(dept -> {
            TreeDataDTO treeDataDTO=new TreeDataDTO();
            treeDataDTO.setId(dept.getDeptId());
//            treeDataDTO.setChecked(dept);
            treeDataDTO.setCode(dept.getCode());
            treeDataDTO.setExpanded(true);
            treeDataDTO.setKey(dept.getDeptId());
            treeDataDTO.setLeaf(!checkLeaf(dept.getDeptId(),list));
            treeDataDTO.setLevel(dept.getLevel());
            treeDataDTO.setTitle(dept.getName());
            treeDataDTO.setParentId(dept.getParentId());
            deptDTOList.add(treeDataDTO);
        });
       return buildMenuTree(deptDTOList,null);
//        return deptDTOList;
    }

    private  List<TreeDataDTO> buildMenuTree(List<TreeDataDTO> menuList,String pid) {
        List<TreeDataDTO> treeList = new ArrayList<>();
        menuList.forEach(menu -> {
            if (StringUtils.equals(pid, menu.getParentId())) {
                menu.setChildren(buildMenuTree(menuList, menu.getId()));
                treeList.add(menu);
            }
        });
        return treeList;
    }

    /**
     *
     * @param deptId
     * @param list
     * @return 如果list.parentId=deptId ，return true
     */
    private boolean checkLeaf(String deptId, List<Dept> list) {
        boolean bool=false;
        if(deptId!=null) {
            for (int i = 0; i < list.size(); i++) {
                Dept dept=list.get(i);
                if(deptId.equals(dept.getParentId())){
                    bool=true;
                    log.debug(deptId+"== is true===="+dept.getName()+dept.getDeptId());
                    break;
                }
            }
        }
        return bool;
    }
    private List<Dept> addParentDept(List<Dept> list) {
        List<Dept> result=new ArrayList<>();
        //获取path, 根据PATH获取父级菜单ID，再从数据库查询全部数据
        StringBuffer ids=new StringBuffer();
        list.forEach(obj->{
            String path = obj.getPath();
            String rid=obj.getDeptId();
            String[] split = path.split(",");
            ids.append("'").append(rid).append("',");
            for (String s : split) {
                if(!rid.equals(s)){
                    ids.append("'").append(s).append("',");
                }
            }
        });
        if(ids.length()>0){
            ids.deleteCharAt(ids.length()-1);
            QueryWrapper<Dept> queryWrapper =new QueryWrapper<>();
            queryWrapper.inSql("dept_id",ids.toString());
            queryWrapper.orderByAsc("path,sort_id");
            result=list(queryWrapper);
        }
        return result;
    }
}
