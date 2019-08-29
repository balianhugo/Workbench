/**
 * 管理角色页面
 * Created by Administrator on 2016-12-12.
 */
Ext.define('WB.view.sys.Role', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.rolelist',
    requires: [
        'WB.store.sys.Role',
        'WB.view.sys.RoleForm',
        'WB.view.sys.RoleMenuForm',
        'WB.view.sys.RoleResourceForm'
    ],

    // 自定义界面权限配置，对应界面功能操作
    authority: {
        add: false,
        update: false,
        remove: false,
        menu: false,
        resource: false
    },

    initComponent: function () {
        var me = this;
        me.store = Ext.create("WB.store.sys.Role");
        Ext.apply(me, {
            columns: [
                {text: '角色', dataIndex: 'roleName', flex: 1},
                {text: '描述', dataIndex: 'description', flex: 2}
            ],
            viewConfig: {
                loadingText: '读取中...'
            },
            selModel: {
                mode: 'SINGLE'
            },
            selType: 'checkboxmodel',
            tbar: [{
                text: '新增',
                hidden: me.authority.add,
                handler: function () {
                    me.addRole();
                }
            }, {
                text: '修改',
                hidden: me.authority.update,
                handler: function () {
                    me.updateRole();
                }
            }, {
                text: '删除',
                hidden: me.authority.remove,
                handler: function () {
                    Ext.Msg.show({
                        title:'提示',
                        msg: '您确定要删除当前选项吗？',
                        buttons: Ext.Msg.YESNO,
                        icon: Ext.Msg.WARNING,
                        fn: function(btn) {
                            if (btn === 'yes') {
                                me.delRole();
                            }
                        }
                    });
                }
            }, {
                text: '菜单',
                hidden: me.authority.menu,
                handler: function () {
                    me.roleMenu();
                }
            }, {
                text: '资源',
                hidden: me.authority.resource,
                handler: function () {
                    me.roleResource();
                }
            }],
            dockedItems: [{
                xtype: 'pagingtoolbar',
                store: me.getStore(),
                dock: 'bottom',
                displayInfo: true
            }]
        });

        this.callParent(arguments);
    },

    // 新增用户
    addRole: function () {
        var me = this;
        Ext.createWidget('roleform', {
            title: '新增',
            wbCallBack: function () {
                me.getStore().load();
            }
        }).show();
    },

    // 修改用户
    updateRole: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length == 1) {
                var roleRecord = models[0];
                Ext.createWidget('roleform', {
                    title: '修改',
                    roleUpdate: true,
                    roleRecord: roleRecord,
                    wbCallBack: function () {
                        me.getStore().load();
                    }
                }).show();
            } else {
                Ext.Msg.alert('提示', '请选择一条数据进行操作！');
            }
        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    },

    // 删除用户
    delRole: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length == 1) {
                var roleId = models[0].get('roleId');
                var mask = Ext.MessageBox.wait("角色删除中...");
                Ext.Ajax.request({
                    url: contextPath + '/role/delete',
                    params: {
                        roleId: roleId
                    },
                    success: function (response, opts) {
                        mask.close();
                        var data = Ext.decode(response.responseText);
                        Ext.Msg.alert('提示', data.msg, function () {
                            me.getStore().load();
                        });
                    },
                    failure: function (response, opts) {
                        mask.close();
                        if (response.status === 200) {
                            var data = Ext.decode(response.responseText);
                            Ext.Msg.alert('提示', data.msg);
                        } else {
                            Ext.Msg.alert('提示', response.status);
                        }
                    }
                });
            } else {
                Ext.Msg.alert('提示', '请选择一条数据进行操作！');
            }
        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    },

    // 角色菜单
    roleMenu: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length == 1) {
                var roleId = models[0].get('roleId');
                Ext.createWidget('rolemenuform', {
                    title: '菜单',
                    roleId: roleId
                }).show();
            } else {
                Ext.Msg.alert('提示', '请选择一条数据进行操作！');
            }
        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    },

    // 角色资源
    roleResource: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length == 1) {
                var roleId = models[0].get('roleId');
                Ext.createWidget('roleresourceform', {
                    title: '资源',
                    roleId: roleId
                }).show();
            } else {
                Ext.Msg.alert('提示', '请选择一条数据进行操作！');
            }
        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    }
});