package dev.gavin.wb.vo;

import dev.gavin.wb.model.WbUserRole;

/**
 * 用户角色绑定业务对象
 * Created by Administrator on 2016-12-19.
 */
public class WbUserRoleBindVO extends WbUserRole {

    private boolean bind = false;

    public boolean isBind() {
        return bind;
    }

    public void setBind(boolean bind) {
        this.bind = bind;
    }

}
