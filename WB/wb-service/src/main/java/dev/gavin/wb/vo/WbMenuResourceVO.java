package dev.gavin.wb.vo;

import dev.gavin.wb.model.WbMenuResource;

/**
 * 菜单与资源绑定业务对象
 * Created by Administrator on 2017-2-28.
 */
public class WbMenuResourceVO extends WbMenuResource {

    private boolean bind = false;

    public boolean isBind() {
        return bind;
    }

    public void setBind(boolean bind) {
        this.bind = bind;
    }

}
