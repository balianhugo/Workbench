/**
 * WB 平台左侧菜单页面
 *
 * Created by Administrator on 2016-11-14.
 */
Ext.define('WB.view.main.MainLeft', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.mainleft',

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            title: 'mainleft',
            id: 'nav-main-left',
            width: 200,
            defaults: {
                rootVisible: false,
                listeners: {
                    itemclick: {
                        fn: function (view, record, item, index, e, eOpts) {
                            me.leftNavClick(record);
                        }
                    }
                }
            },
            layout: {
                // layout-specific configs go here
                type: 'accordion'
            },
            defaultType: 'treepanel',
            items: []
        });
        me.callParent(arguments);
    },

    // 左边导航菜单点击
    leftNavClick: function (record) {
        var navMainCenter = Ext.getCmp('nav-main-center');
        var tabId = 'tab-' + record.get('id');
        var tab = navMainCenter.setActiveTab(tabId);
        if (Ext.isEmpty(tab)) {
            navMainCenter.add({
                id: tabId,
                title: record.get('text'),
                icon: record.get('icon'),
                closable: true,
                html: record.get('text'),
                loader: {
                    // 这里注意，由于 url 是自己扩展加入的属性，ExtJS 自动生成的 record 不会生成 get 属性的对应方法，所以采用直接读取方式
                    url: contextPath + record.raw.url,
                    loadMask: '加载中...',
                    autoLoad: (record.raw.url !== null && record.raw.url !== ''),
                    scripts: true
                }
            }).show();
        }
    }
});