package com.lhsz.bandian.jt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.Exception.NoticeException;
import com.lhsz.bandian.jt.DTO.response.JtAwardsTypeDTO;
import com.lhsz.bandian.jt.entity.AwardsType;
import com.lhsz.bandian.jt.mapper.AwardsTypeMapper;
import com.lhsz.bandian.jt.service.IAwardsTypeService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import com.lhsz.bandian.sys.DTO.result.TreeDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 资历级别 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@Service
public class AwardsTypeServiceImpl extends ServiceImpl<AwardsTypeMapper, AwardsType> implements IAwardsTypeService {

    @Override
    public List<JtAwardsTypeDTO> listAwardsType(AwardsType awardsType) {
        QueryWrapper<AwardsType> queryWrapper2 = new QueryWrapper<>();
        if(awardsType.getName()!=null&&!"".equals(awardsType.getName().trim()))
        {
            queryWrapper2.like("name",awardsType.getName());
        }
        if(awardsType.getEnabled()!=null) {
            queryWrapper2.eq("enabled", awardsType.getEnabled());
        }
        List<AwardsType> list1 = list(queryWrapper2);
        Set<String> ids= new HashSet<>();
        for (int i = 0; i < list1.size(); i++) {
            AwardsType awardsType1=list1.get(i);
            String path = awardsType1.getPath();
            String[] split = path.split(",");
            for (int i1 = 0; i1 < split.length; i1++) {
                String s = split[i1];
                if(!ids.contains(s)){
                    ids.add(s);
                }
            }
        }
        List<AwardsType> list2=new ArrayList<>();
        QueryWrapper<AwardsType> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        if(ids.size()>0){
            queryWrapper.in("type_id",ids);
            list2 = list(queryWrapper);
        }
        List<JtAwardsTypeDTO> result=convertList(list2);

        for (int i = 0; i < result.size(); i++) {
            JtAwardsTypeDTO item = result.get(i);
            List<JtAwardsTypeDTO> childrens = result.stream().filter(t -> t.getParentId() != null && t.getParentId().equals(item.getTypeId()))
                    .sorted(comparing(JtAwardsTypeDTO::getCode))
//                    .map(JtDeclareCategoryDTO::getId)
                    .collect(toList());

            if (childrens.size() > 0) {
                result.get(i).setChildren(childrens);
            }
        }
        return result;
    }

    @Override
    public JtAwardsTypeDTO getAwardsType(String id) {
        AwardsType awardsType = getById(id);
        JtAwardsTypeDTO jtAwardsTypeDTO = new JtAwardsTypeDTO();
        BeanUtils.copyProperties(awardsType,jtAwardsTypeDTO);
        jtAwardsTypeDTO.setId(id);
        return jtAwardsTypeDTO;
    }

    @Override
    public void addAwardsType(JtAwardsTypeDTO jtAwardsTypeDTO) {
        check(jtAwardsTypeDTO);
        AwardsType parent=getById(jtAwardsTypeDTO.getParentId());
        AwardsType awardsType = new AwardsType();
        String id= UUID.randomUUID().toString();
        BeanUtils.copyProperties(jtAwardsTypeDTO,awardsType);
        jtAwardsTypeDTO.setId(awardsType.getTypeId());
        awardsType.setPath(parent.getPath()+id+",");
        awardsType.setTypeId(id);
        awardsType.setLevel(parent.getLevel()+1);
        save(awardsType);
    }

    @Override
    public void updateAwardsType(JtAwardsTypeDTO jtAwardsTypeDTO) {
        checkUpdate(jtAwardsTypeDTO);
        AwardsType awardsType = new AwardsType();
        BeanUtils.copyProperties(jtAwardsTypeDTO,awardsType);
        awardsType.setTypeId(jtAwardsTypeDTO.getId());
        updateById(awardsType);
    }

    @Override
    public int delAwardsType(String id) {
        int deleteById = baseMapper.deleteById(id);
        return deleteById;
    }

    @Override
    public TreeDTO getTreeDTO() {
        TreeDTO treeDTO=new TreeDTO();
        QueryWrapper<AwardsType> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("path");
        List<TreeDataDTO> list=convertTree(list(queryWrapper));
        if(list.size()>0){
            treeDTO.setExpandedKeys(list.get(0).getId());
        }
        treeDTO.setNodes(list);
        return treeDTO;
    }

    private List<JtAwardsTypeDTO> convertList(List<AwardsType> list) {
        List<JtAwardsTypeDTO> result = new ArrayList<>();
        list.forEach(obj->result.add(new JtAwardsTypeDTO(obj).setLeaf(!checkLeaf(obj.getTypeId(),list))));
        return result;
    }

    /**
     * 是否还存在父级元素,找出末尾叶子节点
     *
     * @author gewx
     *
     * @param list   叶子节点
     * @param id 节点标记
     * @return boolean true 存在, false 不存在
     **/
    private boolean checkLeaf(String id, List<AwardsType> list) {
        /*boolean bool=false;
        if(id!=null)
            for (int i = 0; i < list.size(); i++) {
                AwardsType dept=list.get(i);
                if(id.equals(dept.getParentId())){
                    bool=true;
                    log.debug(id+"== getParentId is true 不是叶子===="+dept.getName());
                    break;
                }
            }

        return bool;*/
        return list.stream().anyMatch(val -> val.getParentId()!=null&&val.getParentId().equals(id));
    }
    @Override
    public List<SelectDTO> listAllByParentId(String parentId) {
        List<SelectDTO> result = new ArrayList<>();
        QueryWrapper<AwardsType> queryWrapper = new QueryWrapper<>();
        if(parentId!=null&&!"".equals(parentId.trim())){
            queryWrapper.eq("parent_id",parentId);
        }else{
            queryWrapper.eq("level",1);
        }
        queryWrapper.orderByAsc("code");
        for (AwardsType obj : list(queryWrapper)) {
            result.add(new SelectDTO(obj.getTypeId(), obj.getName(), obj.getCode(), !obj.getEnabled()));    
        }

        return result;
    }

    private List<TreeDataDTO> convertTree(List<AwardsType> list) {
        List<TreeDataDTO> deptDTOList=new ArrayList<>();
        list.forEach(obj -> {
            TreeDataDTO treeDataDTO=new TreeDataDTO();
            treeDataDTO.setId(obj.getTypeId());
            treeDataDTO.setCode(obj.getCode());
            treeDataDTO.setExpanded(true);
            treeDataDTO.setKey(obj.getTypeId());
            treeDataDTO.setLeaf(!checkLeaf(obj.getTypeId(),list));
            treeDataDTO.setLevel(obj.getLevel());
            treeDataDTO.setTitle(obj.getName());
            treeDataDTO.setParentId(obj.getParentId());
            deptDTOList.add(treeDataDTO);
        });
        return buildMenuTree(deptDTOList,null);
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
    private void check(JtAwardsTypeDTO obj) {
        String code=obj.getCode();
        String name = obj.getName();
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("code",code);
        queryWrapper.eq("name",name);
        if(list(queryWrapper).size()>0){
            throw new NoticeException("类别名称或编码已存在！");
        }

    }
    private void checkUpdate(JtAwardsTypeDTO obj) {
        String code=obj.getCode();
        String name = obj.getName();
        AwardsType oldObj = getById(obj.getId());
        if(!oldObj.getCode().equals(code)||!oldObj.getName().equals(name)){
            QueryWrapper queryWrapper =new QueryWrapper();
            if(!oldObj.getCode().equals(code)){
                queryWrapper.eq("code",code);
                queryWrapper.or();
            }
            if(!oldObj.getName().equals(name)){
                queryWrapper.eq("name",name);
            }

            if(list(queryWrapper).size()>0){
                throw new NoticeException("类别名称或编码已存在！");
            }
        }
    }

    @Override
    public JtAwardsTypeDTO getAwardsTypeNameByCode(String code) {
        QueryWrapper<AwardsType> queryWrapper = new QueryWrapper<>();
        if(code!=null && !"".equals(code.trim())){
            queryWrapper.eq("code",code);
        }
        AwardsType awardsType = baseMapper.selectOne(queryWrapper);
        JtAwardsTypeDTO jtAwardsTypeDTO = new JtAwardsTypeDTO(awardsType);
        return jtAwardsTypeDTO;
    }
}
