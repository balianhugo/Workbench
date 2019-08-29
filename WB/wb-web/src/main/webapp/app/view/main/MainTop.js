/**
 * WB 平台顶部页面
 *
 * Created by Administrator on 2016-11-14.
 */
Ext.define('WB.view.main.MainTop', {
    extend: 'Ext.container.Container',
    alias: 'widget.maintop',

    initComponent: function () {
        Ext.apply(this, {
            weight: '100%',
            minWidth: 1024,
            // style: {
            //     backgroundColor: '#8fc33a'
            // },
            cls: 'nav-header',
            layout: 'column',
            defaultType: 'container',
            items: [{
                html: '<div class="nav-left-wrap">WB 系统管理平台</div>',
                width: 280
            }, {
                xtype: 'dataview',
                id: 'nav-main-top',
                loadingText: '读取中...',
                store: {
                    fields: ['data', 'icon', 'text', 'id'],
                    data: wb.nav.menus // 改用全局变量中的菜单数据
                    // fixme: 之前用的是异步获取菜单数据，这样界面初始化会多一次请求，界面渲染也会慢一些体验不好
                    // proxy: {
                    //     type: 'ajax',
                    //     url: 'menus',
                    //     reader: {
                    //         type: 'json',
                    //         root: 'menus'
                    //     }
                    // },
                    // listeners: {
                    //     load: {
                    //         fn: function (s, records, successful, eOpts) {
                    //             var navMainLeft = Ext.getCmp('nav-main-left');
                    //             navMainLeft.setTitle(records[0].get('text'));
                    //             navMainLeft.removeAll();
                    //             navMainLeft.add(records[0].get('data'));
                    //         }
                    //     }
                    // },
                    // autoLoad: true
                },
                tpl: new Ext.XTemplate(
                    '<tpl for=".">',
                    '<div class="nav-wrap" id="{id}">',
                    '<img src="{icon}" />',
                    '<span>{text}</span>',
                    '</div>',
                    '</tpl>'
                ),
                itemSelector: 'div.nav-wrap',
                listeners: {
                    itemclick: {
                        fn: function (view, record, item, index, e, eOpts) {
                            var navMainLeft = Ext.getCmp('nav-main-left');
                            navMainLeft.setTitle(record.get('text'));
                            navMainLeft.removeAll();
                            navMainLeft.add(record.get('data'));
                        }
                    },
                    render: {
                        fn: function (view, e0pts) {
                            var navMainLeft = Ext.getCmp('nav-main-left');
                            navMainLeft.setTitle(view.getStore().first().get('text'));
                            navMainLeft.removeAll();
                            navMainLeft.add(view.getStore().first().get('data'));
                        }
                    }
                },
                columnWidth: 1

            }, {
                xtype: 'dataview',
                store: {
                    fields: ['icon', 'text', 'id'],
                    data: [
                        {icon: contextPath + '/img/topmenu/logout.png', text: '退出', id: 'wb-logout'},
                        {icon: contextPath + '/img/topmenu/user.png', text: wb.user.nickName, id: 'user-name'}
                    ]
                },
                tpl: new Ext.XTemplate(
                    '<div class="nav-right">',
                    '<tpl for=".">',
                    '<div class="nav-right-wrap" id="{id}">',
                    '<img src="{icon}" />',
                    '<span>{text}</span>',
                    '</div>',
                    '</tpl>',
                    '</div>'
                ),
                itemSelector: 'div.nav-right-wrap',
                listeners: {
                    itemclick: {
                        fn: function (view, record, item, index, e, eOpts) {
                            if (record.get('id') === 'wb-logout') {

                                Ext.Msg.show({
                                    title:'提示',
                                    msg: '您确定要退出系统吗？',
                                    buttons: Ext.Msg.YESNO,
                                    icon: Ext.Msg.QUESTION,
                                    fn: function(btn) {
                                        if (btn === 'yes') {
                                            window.location.href = contextPath + "/public/doLogout";
                                        }
                                    }
                                });

                            }
                        }
                    }
                }
                // width: 180
            }]
        });
        this.callParent(arguments);
    }
});