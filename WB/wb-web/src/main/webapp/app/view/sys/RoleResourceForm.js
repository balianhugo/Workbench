/**
 * 角色资源管理
 * Created by Administrator on 2017-1-17.
 */
Ext.define('WB.view.sys.RoleResourceForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.roleresourceform',
    requires: ["WB.store.sys.RoleResource", 'WB.view.sys.ResourceForm'],

    // 自定义界面权限配置，对应界面功能操作
    authority: {
        update: false
    },

    // 扩展属性用于加载数据时的查询参数角色 ID
    roleId: '',

    // 记录当前操作的对应菜单 ID
    currentMenuId: '',

    initComponent: function () {
        var me = this;

        me.store = Ext.create('store.roleresource',{
            listeners: {
                load: {
                    fn: function (s, records, successful, eOpts) {
                        // 回显初始化 Grid 选择项
                        Ext.Array.each(records, function (record, index, itSelf) {
                            if (record.raw.bind) {
                                me.down('grid').getSelectionModel().select(record, true, false);
                            }
                        });
                    }
                }
            }
        });

        Ext.apply(me, {
            maximizable: true,
            width: 900,
            height: 500,
            modal: true,
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
                                        menuId: me.currentMenuId,
                                        roleId: me.roleId
                                    }
                                });
                            }
                        }
                    }
                },
                {
                    xtype: 'grid',
                    region: 'center',
                    store: me.store,
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
                        text: '保存',
                        hidden: me.authority.add,
                        handler: function () {
                            me.addRoleResource();
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
            url: contextPath + '/role/resourceList',
            params: {
                roleId: me.roleId
            },
            success: function (response, opts) {
                me.down('treepanel').setRootNode(Ext.decode(response.responseText));
            },
            failure: function (response, opts) {
                Ext.Msg.alert('失败', response.status);
            }
        });
    },

    // 保存关联资源数据
    addRoleResource: function(){
        var me = this;

        if(me.down('grid').getStore().getCount() == 0){
            Ext.Msg.alert('提示', '当前没有可关联的资源数据！');
            return false;
        }

        var sm = me.down('grid').getSelectionModel();
        var models = sm.getSelection();
        var ids = [];
        Ext.Array.each(models, function (model, index, itSelf) {
            ids.push(model.get('resourceId'));
        });

        var mask = Ext.MessageBox.wait("角色资源更新中...");
        Ext.Ajax.request({
            url: contextPath + '/role/updateResource',
            params: {
                ids: ids,
                menuId: me.currentMenuId,
                roleId: me.roleId
            },
            success: function (response, opts) {
                mask.close();
                var data = Ext.decode(response.responseText);
                Ext.Msg.alert('成功', data.msg);
            },
            failure: function (response, opts) {
                mask.close();
                if (response.status === 200) {
                    var data = Ext.decode(response.responseText);
                    Ext.Msg.alert('失败', data.msg);
                } else {
                    Ext.Msg.alert('失败', response.status);
                }
            }
        });

    }

});