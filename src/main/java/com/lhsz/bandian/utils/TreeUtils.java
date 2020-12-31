package com.lhsz.bandian.utils;

import com.lhsz.bandian.DTO.BaseTree;
import com.lhsz.bandian.sys.DTO.result.MoudelParentDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/24 11:30
 */
public class TreeUtils {
    public static List<BaseTree> buildMenuTree(List<BaseTree> menuList, String pid) {
        List<BaseTree> treeList = new ArrayList<>();
        menuList.forEach(menu -> {
            if (StringUtils.equals(pid, menu.getParentId())) {
                menu.setChildren(buildMenuTree(menuList, menu.getId()));
                treeList.add(menu);
            }
        });
        return treeList;
    }
}
