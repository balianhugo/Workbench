/**
 * WB ExtJS启动页面
 * Created by Administrator on 2016-11-9.
 */
Ext.Loader.setConfig({enabled: true, disableCaching: false});
Ext.Loader.setPath('Ext.ux', '../libs/ExtJS-4.2.5/ux/');
Ext.application({
    name: 'WB',
    appFolder: '../app',
    autoCreateViewport: true,
    launch: function () {

        // 初始化全局 tip 提示
        Ext.tip.QuickTipManager.init();

        // 异步请求处理
        Ext.Ajax.on('requestcomplete', function (conn, response, options) {

            if(response.getResponseHeader !== null && typeof response.getResponseHeader === 'function'){

                // 异步请求超时标识（这里是根据后台定义返回的字段来判断）
                if (response.getResponseHeader('sessionstatus') === 'timeout') {
                    Ext.Msg.alert("提示", "登录超时，请重新登录！", function () {
                        window.location.href = contextPath + "/public/login";
                    });
                }

                // 异步请求权限（这里是根据后台定义返回的字段来判断）
                if (response.getResponseHeader('sessionstatus') === '403') {
                    Ext.Msg.alert("提示", "您没有足够的权限！");
                    return false;
                }
            }

        });
    }
});