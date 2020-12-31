package com.lhsz.bandian.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhsz.bandian.DTO.SelectDTO;
import com.lhsz.bandian.cms.DTO.query.QuerydeclareCategoryDTO;
import com.lhsz.bandian.cms.DTO.request.CmsChannelDTO;
import com.lhsz.bandian.cms.entity.CmsChannel;
import com.lhsz.bandian.cms.mapper.CmsChannelMapper;
import com.lhsz.bandian.cms.service.ICmsChannelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhsz.bandian.sys.DTO.result.TreeDTO;
import com.lhsz.bandian.sys.DTO.result.TreeDataDTO;
import com.lhsz.bandian.utils.ChineseUtil;
import com.lhsz.bandian.utils.Convert;
import com.lhsz.bandian.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 文章频道 服务实现类
 * </p>
 *
 * @author lizhiguo
 * @since 2020-07-30
 */
@Slf4j
@Service
public class CmsChannelServiceImpl extends ServiceImpl<CmsChannelMapper, CmsChannel> implements ICmsChannelService {

    @Override
    public List<CmsChannelDTO> selectList(QuerydeclareCategoryDTO querydeclareCategoryDTO) {
        QueryWrapper<CmsChannel> queryWrapper2 = new QueryWrapper<>();
        if(querydeclareCategoryDTO.getName()!=null&&!"".equals(querydeclareCategoryDTO.getName().trim()))
        {
            queryWrapper2.like("name",querydeclareCategoryDTO.getName());
        }
        queryWrapper2.eq("enabled",querydeclareCategoryDTO.isEnabled());
        List<CmsChannel> list1 = list(queryWrapper2);
        Set<String> pid= new HashSet<>();
        for (int i = 0; i < list1.size(); i++) {
            CmsChannel cmsChannel=list1.get(i);
            String path = cmsChannel.getPath();
            String[] split = path.split(",");
            for (int i1 = 0; i1 < split.length; i1++) {
                String s = split[i1];
                if(!pid.contains(s)){
                    pid.add(s);
                }
            }
        }
        List<CmsChannel> list2=new ArrayList<>();
        QueryWrapper<CmsChannel> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("path");
        if(pid.size()>0){
            queryWrapper.in("channel_id",pid);
            list2 = list(queryWrapper);
        }

        List<CmsChannelDTO> list=convertList(list2);
        return list;
    }

    @Override
    public CmsChannelDTO getCmsChannel(String id) {
        CmsChannel declareCategory = baseMapper.selectById(id);
        CmsChannelDTO jtDeclareCategoryDTO = new CmsChannelDTO();
        BeanUtils.copyProperties(declareCategory,jtDeclareCategoryDTO);
        jtDeclareCategoryDTO.setId(declareCategory.getChannelId());
        return jtDeclareCategoryDTO;
    }

    @Override
    public CmsChannel getCmsChannelCode(String code) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("code", code);
        return getOne(queryWrapper);
    }

    @Override
    public TreeDTO getTreeDTO() {
        TreeDTO treeDTO=new TreeDTO();
        QueryWrapper<CmsChannel> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("path");
        List<TreeDataDTO> list=convertTree(list(queryWrapper));
        if(list.size()>0){
            treeDTO.setExpandedKeys(list.get(0).getId());
        }
        treeDTO.setNodes(list);
        return treeDTO;
    }

    @Override
    public void addCmsChannel(CmsChannelDTO cmsChannelDTO) {
        CmsChannel declare = new CmsChannel();
        CmsChannel parent=getById(cmsChannelDTO.getParentId());
        String id= UUID.randomUUID().toString();
        BeanUtils.copyProperties(cmsChannelDTO,declare);
        cmsChannelDTO.setId(declare.getChannelId());
        declare.setPath(parent.getPath()+id+",");
        declare.setChannelId(id);
        ChineseUtil chinese = new ChineseUtil();
        declare.setPinYin(chinese.getPinYinHeadChar(cmsChannelDTO.getName()));
        declare.setLevel(parent.getLevel()+1);
        save(declare);
    }

    @Override
    public void updateCmsChannel(CmsChannelDTO cmsChannelDTO) {
        CmsChannel declareCategory = new CmsChannel();
        ChineseUtil chinese = new ChineseUtil();
        cmsChannelDTO.setPinYin(chinese.getPinYinHeadChar(cmsChannelDTO.getName()));
        BeanUtils.copyProperties(cmsChannelDTO,declareCategory);
        declareCategory.setChannelId(cmsChannelDTO.getId());
        updateById(declareCategory);
    }

    @Override
    public int delCmsChannelDTO(String id) {
        return baseMapper.deleteById(id);
    }

//    @Override
//    public int deleteCmsChannelByIds(String ids) throws Exception {
//        String[] list = ids.split(",");
//          for(String id : list) {
//              return baseMapper.deleteById(id);
//          }
//    }

    @Override
    public List<SelectDTO> listAllByParentId(String parentId) {
        List<SelectDTO> result = new ArrayList<>();
        QueryWrapper<CmsChannel> queryWrapper = new QueryWrapper<>();
        if(parentId!=null&&!"".equals(parentId.trim())){
            queryWrapper.eq("parent_id",parentId);
        }else{
            queryWrapper.eq("leval",1);
        }
        list(queryWrapper).forEach(obj->result.add(new SelectDTO(obj.getChannelId(),obj.getName(),obj.getCode(),obj.getEnabled())));

        return result;
    }

    private List<CmsChannelDTO> convertList(List<CmsChannel> list) {
        List<CmsChannelDTO> jtDeclareCategoryDTOS = new ArrayList<>();
        list.forEach(obj -> {
            CmsChannelDTO declareCategoryDTO=new CmsChannelDTO();
            BeanUtils.copyProperties(obj,declareCategoryDTO);
            declareCategoryDTO.setId(obj.getChannelId());
            declareCategoryDTO.setLeaf(!checkLeaf(obj.getChannelId(),list));
            jtDeclareCategoryDTOS.add(declareCategoryDTO);
        });
        return jtDeclareCategoryDTOS;
    }

    private boolean checkLeaf(String id, List<CmsChannel> list) {
        boolean bool=false;
        if(id!=null) {
            for (int i = 0; i < list.size(); i++) {
                CmsChannel dept = list.get(i);
                if (id.equals(dept.getParentId())) {
                    bool = true;
                    log.debug(id + "== getParentId is true 不是叶子====" + dept.getName() + dept.getChannelId());
                    break;
                }
            }
        }
        return bool;
    }

    private List<TreeDataDTO> convertTree(List<CmsChannel> list) {
        List<TreeDataDTO> deptDTOList=new ArrayList<>();
        list.forEach(declareCategory -> {
            TreeDataDTO treeDataDTO=new TreeDataDTO();
            treeDataDTO.setId(declareCategory.getChannelId());
            treeDataDTO.setCode(declareCategory.getCode());
            treeDataDTO.setExpanded(true);
            treeDataDTO.setKey(declareCategory.getChannelId());
            treeDataDTO.setLeaf(!checkLeaf(declareCategory.getChannelId(),list));
            treeDataDTO.setLevel(declareCategory.getLevel());
            treeDataDTO.setTitle(declareCategory.getName());
            treeDataDTO.setParentId(declareCategory.getParentId());
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

}
