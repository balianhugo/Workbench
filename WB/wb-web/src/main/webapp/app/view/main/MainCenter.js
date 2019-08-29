/**
 * WB 平台展示区页面
 *
 * Created by Administrator on 2016-11-14.
 */
Ext.define('WB.view.main.MainCenter', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.maincenter',

    requires: ['Ext.ux.TabCloseMenu'],

    initComponent: function () {
        Ext.apply(this, {
            id: 'nav-main-center',
            autoScroll: true,
            defaultType: 'container',
            defaults: {
                layout: 'fit',
                autoScroll: true
            },
            plugins: [{
                ptype: 'tabclosemenu',
                closeTabText: '关闭当前',
                closeOthersTabsText: '关闭其他',
                closeAllTabsText: '关闭所有'
            }],
            items: [{
                id: 0,
                title: '主页',
                icon: contextPath + '/img/icons/home--arrow.png',
                loader: {
                    url: contextPath + '/wb/home',
                    loadMask: '加载中...',
                    autoLoad: true,
                    scripts: true
                }
            }]
        });
        this.callParent(arguments);
    }
});