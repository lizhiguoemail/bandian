package com.lhsz.bandian.jt.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.jt.DTO.query.QuerydeclareCategoryDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareCategoryDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareSpecialtyDTO;
import com.lhsz.bandian.jt.DTO.response.JtDeclareTitleDTO;
import com.lhsz.bandian.jt.entity.DeclareCategory;
import com.lhsz.bandian.jt.entity.DeclareSpecialty;
import com.lhsz.bandian.jt.entity.DeclareTitle;
import com.lhsz.bandian.jt.mapper.DeclareCategoryMapper;
import com.lhsz.bandian.jt.service.IDeclareCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.jt.service.IDeclareTitleService;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import com.lhsz.bandian.sys.DTO.result.TreeDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.AttributeList;
import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 职称系列类别 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-17
 */
@Slf4j
@Service
public class DeclareCategoryServiceImpl extends ServiceImpl<DeclareCategoryMapper, DeclareCategory> implements IDeclareCategoryService {

    @Autowired
    IDeclareTitleService declareTitleService;

    @Override
    public List<JtDeclareCategoryDTO> listDeclareCategory(QuerydeclareCategoryDTO querydeclareCategoryDTO) {

        QueryWrapper<DeclareCategory> queryWrapper2 = new QueryWrapper<>();
        if (querydeclareCategoryDTO.getName() != null && !"".equals(querydeclareCategoryDTO.getName().trim())) {
            queryWrapper2.like("name", querydeclareCategoryDTO.getName());
        }
        queryWrapper2.eq("enabled", querydeclareCategoryDTO.isEnabled());
        List<DeclareCategory> list1 = list(queryWrapper2);
        Set<String> pid = new HashSet<>();
        for (int i = 0; i < list1.size(); i++) {
            DeclareCategory declareCategory = list1.get(i);
            String path = declareCategory.getPath();
            String[] split = path.split(",");
            for (int i1 = 0; i1 < split.length; i1++) {
                String s = split[i1];
                if (!pid.contains(s)) {
                    pid.add(s);
                }
            }
        }
        List<DeclareCategory> list2 = new ArrayList<>();
        QueryWrapper<DeclareCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("code");
        if (pid.size() > 0) {
            queryWrapper.in("category_id", pid);
            list2 = list(queryWrapper);
        }
        List<JtDeclareCategoryDTO> list = convertList(list2);
        for (int i = 0; i < list.size(); i++) {
            JtDeclareCategoryDTO item = list.get(i);
            List<JtDeclareCategoryDTO> childrens = list.stream().filter(t -> t.getParentId() != null && t.getParentId().equals(item.getCategoryId()))
                    .sorted(comparing(JtDeclareCategoryDTO::getCode))
//                    .map(JtDeclareCategoryDTO::getId)
                    .collect(toList());

            if (childrens.size() > 0) {
                list.get(i).setChildren(childrens);
            }
        }
        return list;
    }


    private List<JtDeclareCategoryDTO> convertList(List<DeclareCategory> list) {
        List<JtDeclareCategoryDTO> jtDeclareCategoryDTOS = new ArrayList<>();
        list.forEach(obj -> {
            JtDeclareCategoryDTO declareCategoryDTO = new JtDeclareCategoryDTO();
            BeanUtils.copyProperties(obj, declareCategoryDTO);
            declareCategoryDTO.setId(obj.getCategoryId());
            declareCategoryDTO.setLeaf(!checkLeaf(obj.getCategoryId(), list));
            jtDeclareCategoryDTOS.add(declareCategoryDTO);
        });
        return jtDeclareCategoryDTOS;
    }

    private boolean checkLeaf(String id, List<DeclareCategory> list) {
        boolean bool = false;
        if (id != null) {
            for (int i = 0; i < list.size(); i++) {
                DeclareCategory dept = list.get(i);
                if (id.equals(dept.getParentId())) {
                    bool = true;
                    log.debug(id + "== getParentId is true 不是叶子====" + dept.getName() + dept.getCategoryId());
                    break;
                }
            }
        }
        return bool;
    }

    @Override
    public JtDeclareCategoryDTO getDeclareCategory(String id) {
        DeclareCategory declareCategory = baseMapper.selectById(id);
        JtDeclareCategoryDTO jtDeclareCategoryDTO = new JtDeclareCategoryDTO();
        BeanUtils.copyProperties(declareCategory, jtDeclareCategoryDTO);
        jtDeclareCategoryDTO.setId(declareCategory.getCategoryId());
        return jtDeclareCategoryDTO;
    }

    @Override
    public void addDeclareCategory(JtDeclareCategoryDTO jtDeclareCategoryDTO) {
        DeclareCategory declareCategory = new DeclareCategory();
        DeclareCategory parent = getById(jtDeclareCategoryDTO.getParentId());
        String id = UUID.randomUUID().toString();
        BeanUtils.copyProperties(jtDeclareCategoryDTO, declareCategory);
        jtDeclareCategoryDTO.setId(declareCategory.getCategoryId());
        declareCategory.setPath(parent.getPath() + id + ",");
        declareCategory.setCategoryId(id);
        declareCategory.setLevel(parent.getLevel() + 1);
        save(declareCategory);
    }

    @Override
    public void updateDeclareCategory(JtDeclareCategoryDTO jtDeclareCategoryDTO) {
        DeclareCategory declareCategory = new DeclareCategory();
        BeanUtils.copyProperties(jtDeclareCategoryDTO, declareCategory);
        declareCategory.setCategoryId(jtDeclareCategoryDTO.getId());
        updateById(declareCategory);
    }

    @Override
    public int delDeclareCategory(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public TreeDTO getTreeDTO() {
        TreeDTO treeDTO = new TreeDTO();
        QueryWrapper<DeclareCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("path");
        List<TreeDataDTO> list = convertTree(list(queryWrapper));
        if (list.size() > 0) {
            treeDTO.setExpandedKeys(list.get(0).getId());
        }
        treeDTO.setNodes(list);
        return treeDTO;
    }

    @Override
    public List<JtDeclareCategoryDTO> getTree() {
        QueryWrapper<DeclareCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("path");
        List<JtDeclareCategoryDTO> list = convert(list(queryWrapper));
        List<JtDeclareCategoryDTO> jtDeclareCategoryList = buildMenuTree2(list, null);
        List<DeclareTitle> list1 = declareTitleService.list();
        for (JtDeclareCategoryDTO jtDeclareCategoryDTO : jtDeclareCategoryList) {//1 全部类别
            for (JtDeclareCategoryDTO child : jtDeclareCategoryDTO.getChildren()) {// 2大类别
                for (JtDeclareCategoryDTO childChild : child.getChildren()) {   //  3小类别
                    List<DeclareTitle> titleList = new ArrayList<>();
                    for (DeclareTitle title : list1) { // 添加名称
                        if (childChild.getCategoryId().equals(title.getCategoryId())) {
                            titleList.add(title);
                        }
                    }
                    childChild.setTitleDTOList(titleList);
                }
            }
        }
        return jtDeclareCategoryList;

    }

    private List<JtDeclareCategoryDTO> convert(List<DeclareCategory> list) {
        List<JtDeclareCategoryDTO> result = new ArrayList<>();
        for (DeclareCategory obj : list) {
            result.add(new JtDeclareCategoryDTO(obj));
        }
        return result;
    }


    @Override
    public List<SelectDTO> listAllByParentId(String parentId) {
        List<SelectDTO> result = new ArrayList<>();
        QueryWrapper<DeclareCategory> queryWrapper = new QueryWrapper<>();
        if (parentId != null && !"".equals(parentId.trim())) {
            queryWrapper.eq("parent_id", parentId);
        } else {
            queryWrapper.eq("level", 2);
        }
        queryWrapper.orderByAsc("code");
        list(queryWrapper).forEach(obj -> result.add(new SelectDTO(obj.getCategoryId(), obj.getName(), obj.getCode(), !obj.getEnabled())));

        return result;
    }

    private List<TreeDataDTO> convertTree(List<DeclareCategory> list) {
        List<TreeDataDTO> deptDTOList = new ArrayList<>();
        for (DeclareCategory declareCategory : list) {
            TreeDataDTO treeDataDTO = new TreeDataDTO();
            treeDataDTO.setId(declareCategory.getCategoryId());
//            treeDataDTO.setChecked(dept);
            treeDataDTO.setCode(declareCategory.getCode());
            treeDataDTO.setExpanded(true);
            treeDataDTO.setKey(declareCategory.getCategoryId());
            treeDataDTO.setLeaf(!checkLeaf(declareCategory.getCategoryId(), list));
            treeDataDTO.setLevel(declareCategory.getLevel());
            treeDataDTO.setTitle(declareCategory.getName());
            treeDataDTO.setParentId(declareCategory.getParentId());
            deptDTOList.add(treeDataDTO);
        }
        return buildMenuTree(deptDTOList, null);
    }

    private List<TreeDataDTO> buildMenuTree(List<TreeDataDTO> menuList, String pid) {
        List<TreeDataDTO> treeList = new ArrayList<>();
        menuList.forEach(menu -> {
            if (StringUtils.equals(pid, menu.getParentId())) {
                menu.setChildren(buildMenuTree(menuList, menu.getId()));
                treeList.add(menu);
            }
        });
        return treeList;
    }

    private List<JtDeclareCategoryDTO> buildMenuTree2(List<JtDeclareCategoryDTO> menuList, String pid) {
        List<JtDeclareCategoryDTO> treeList = new ArrayList<>();
        menuList.forEach(menu -> {
            if (StringUtils.equals(pid, menu.getParentId())) {
                menu.setChildren(buildMenuTree2(menuList, menu.getId()));
                treeList.add(menu);
            }
        });
        return treeList;
    }
}
