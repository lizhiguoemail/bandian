package com.lhsz.bandian.sys.DTO;

import com.lhsz.bandian.sys.entity.Application;
import com.lhsz.bandian.sys.entity.Resource;
import com.lhsz.bandian.sys.entity.User;

import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/10 17:45
 */
public class AppData {
    public Application app;
    public User user;
    public List<ResourceDTO> menu;

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ResourceDTO> getMenu() {
        return menu;
    }

    public void setMenu(List<ResourceDTO> menu) {
        this.menu = menu;
    }
}
