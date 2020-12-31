package com.lhsz.bandian.sys.DTO;


import java.io.Serializable;
import java.util.List;

/**
 * @author lizhiguo
 * 2020/7/10 17:45
 */
public class AppData  implements Serializable {
    private static final long serialVersionUID = 1L;
    public AppApplicationDTO app;
    public AppUserDTO user;
    public List<AppResourceDTO> menu;

    public AppApplicationDTO getApp() {
        return app;
    }

    public void setApp(AppApplicationDTO app) {
        this.app = app;
    }

    public AppUserDTO getUser() {
        return user;
    }

    public void setUser(AppUserDTO user) {
        this.user = user;
    }

    public List<AppResourceDTO> getMenu() {
        return menu;
    }

    public void setMenu(List<AppResourceDTO> menu) {
        this.menu = menu;
    }
}
