//package com.lhsz.bandian;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.lhsz.bandian.sys.entity.User;
//import com.lhsz.bandian.sys.mapper.UserMapper;
//import com.lhsz.bandian.sys.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.useRepresentation;
//
//import java.io.Serializable;
//import java.util.*;
//
///**
// * @author lizhiguo
// * 2020/7/9 9:47
// */
//@SpringBootTest
//public class PageTest {
//
//    @Autowired
//    private UserMapper mapper;
//
//    @Test
//    void Test(){
//
//        // pagehelper
//        PageInfo<User> info = PageHelper.startPage(1, 2).doSelectPageInfo(() -> mapper.selectById(1278893081939664897L));
//        assertThat(info.getTotal()).isEqualTo(1L);
//        List<User> list = info.getList();
//        assertThat(list).isNotEmpty();
//        assertThat(list.size()).isEqualTo(1);
//        //mp
//        IPage<User> mpPage = mapper.selectPage(new Page<>(1, 2), Wrappers.<User>query().eq("user_id", 1278893081939664897L));
//        assertThat(mpPage.getTotal()).isEqualTo(1);
//        List<User> records =mpPage.getRecords();
//        assertThat(records).isNotNull();
//        assertThat(records.size()).isEqualTo(1);
//    }
//
//    void TestIn(){
//        // pagehelper
////        语法形式为 () -> {}，其中 () 用来描述参数列表，{} 用来描述方法体，-> 为 lambda运算符 ，读作(goes to)。
//        List<Long> ids = Arrays.asList(1278893081939664897L, 1280803990182215682L);
//        PageInfo<User> info=PageHelper.startPage(1,5)
//                .doSelectPageInfo(()->mapper.selectList(Wrappers.<User>query().in("user_id",ids)));
//        assertThat(info.getTotal()).isEqualTo(2L);
//        List<User> list =info.getList();
//        assertThat(list).isNotNull();
//        assertThat(list.size()).isEqualTo(2);
//
//        //mp
//        IPage<User> mpPage = mapper.selectPage(new Page<>(1,5),Wrappers.<User>query().in("user_id",ids));
//        assertThat(mpPage.getTotal()).isEqualTo(2);
//        List<User> records =mpPage.getRecords();
//        assertThat(records).isNotNull();
//        assertThat(records.size()).isEqualTo(2);
//
//    }
//    @Test
//    void Test2(){
//
//        // pagehelper
//        PageHelper.startPage(1, 2);
////        assertThat(page.getTotal()).isEqualTo(1L);
//        User user = (User) mapper.selectById(1278893081939664897L);
//        assertThat(user).isNotNull();
//        assertThat(user.getUserName()).isEqualTo("lizhiguo");
//
//    }
//}
