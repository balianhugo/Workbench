/**
 * 资源管理页面
 * Created by Administrator on 2016-12-30.
 */
Ext.define('WB.view.sys.Resource', {
    extend: 'Ext.container.Container',
    alias: 'widget.resourcelist',
    requires: ["WB.store.sys.Resource", 'WB.view.sys.ResourceForm'],

    // 自定义界面权限配置，对应界面功能操作
    authority: {
        add: false,
        update: false,
        remove: false
    },

    // 记录当前操作的对应菜单 ID
    currentMenuId: 0,

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'border',
            items: [
                {
                    xtype: 'treepanel',
                    useArrows: true,
                    rootVisible: false,
                    width: 200,
                    region: 'west',
                    listeners: {
                        itemclick: {
                            fn: function (view, record, item, index, e, eOpts) {
                                // 根据菜单 ID 加载右边的资源列表信息
                                me.currentMenuId = record.raw.menuId;
                                me.down('grid').getStore().load({
                                    params: {
                                        menuId: me.currentMenuId
                                    }
                                });
                            }
                        }
                    }
                },
                {
                    xtype: 'grid',
                    region: 'center',
                    store: Ext.create('store.resource'),
                    columns: [
                        {text: '资源', dataIndex: 'name', flex: 1},
                        {text: '链接', dataIndex: 'href', flex: 1},
                        {text: '描述', dataIndex: 'description', flex: 2}
                    ],
                    viewConfig: {
                        loadingText: '读取中...'
                    },
                    selModel: {
                        mode: 'MULTI'
                    },
                    selType: 'checkboxmodel',
                    tbar: [{
                        text: '新增',
                        hidden: me.authority.add,
                        handler: function () {
                            me.addResource()
                        }
                    }, {
                        text: '修改',
                        hidden: me.authority.update,
                        handler: function () {
                            me.updateResource();
                        }
                    }, {
                        text: '删除',
                        hidden: me.authority.remove,
                        handler: function () {
                            Ext.Msg.show({
                                title:'提示',
                                msg: '您确定要删除当前选项吗？',
                                buttons: Ext.Msg.YESNO,
                                icon: Ext.Msg.QUESTION,
                                fn: function(btn) {
                                    if (btn === 'yes') {
                                        me.delResource();
                                    }
                                }
                            });
                        }
                    }]
                }
            ]
        });

        me.on('beforerender', me.loadMenuData, me);

        me.callParent(arguments);
    },

    // 加载关联菜单数据
    loadMenuData: function () {
        var me = this;
        Ext.Ajax.request({
            url: contextPath + '/menu/list',
            success: function (response, opts) {
                me.down('treepanel').setRootNode(Ext.decode(response.responseText));
            },
            failure: function (response, opts) {
                Ext.Msg.alert('失败', '数据加载失败');
            }
        });
    },

    // 新增
    addResource: function () {
        var me = this;
        if(me.currentMenuId !== 0) {
            Ext.createWidget('resourceform', {
                title: '新增',
                menuId: me.currentMenuId,
                wbCallBack: function () {
                    me.down('grid').getStore().load({
                        params: {
                            menuId: me.currentMenuId
                        }
                    });
                }
            }).show();
        }else{
            Ext.Msg.alert('提示', '请选择一个菜单进行操作！');
        }
    }
    ,

    // 修改
    updateResource: function () {
        var me = this;
        var sm = me.down('grid').getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length == 1) {
                var resourceRecord = models[0];
                Ext.createWidget('resourceform', {
                    title: '修改',
                    resourceUpdate: true,
                    resourceRecord: resourceRecord,
                    wbCallBack: function () {
                        me.down('grid').getStore().load({
                            params: {
                                menuId: me.currentMenuId
                            }
                        });
                    }
                }).show();
            } else {
                Ext.Msg.alert('提示', '请选择一条数据进行操作！');
            }
        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    }
    ,

    // 删除
    delResource: function () {
        var me = this;
        var sm = me.down('grid').getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            var ids = [];
            Ext.Array.each(models, function (model, index, itSelf) {
                ids.push(model.get('resourceId'));
            });

            var mask = Ext.MessageBox.wait("资源删除中...");
            Ext.Ajax.request({
                url: contextPath + '/resource/delete',
                params: {
                    ids: ids
                },
                success: function (response, opts) {
                    mask.close();
                    var data = Ext.decode(response.responseText);
                    Ext.Msg.alert('成功', data.msg, function () {
                        me.down('grid').getStore().load({
                            params: {
                                menuId: me.currentMenuId
                            }
                        });
                    });
                },
                failure: function (response, opts) {
                    mask.close();
                    if (response.status == "200") {
                        var data = Ext.decode(response.responseText);
                        Ext.Msg.alert('失败', data.msg);
                    } else {
                        Ext.Msg.alert('失败', response.status);
                    }
                }
            });

        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    }
});