/**
 * 管理菜单页面
 * Created by Administrator on 2016-12-12.
 */
Ext.define('WB.view.sys.Menu', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.menulist',
    requires: ['WB.view.sys.MenuForm', "WB.store.sys.Menu"],

    // 自定义界面权限配置，对应界面功能操作
    authority: {
        add: false,
        update: false,
        remove: false
    },

    initComponent: function () {
        var me = this;
        me.store = Ext.create("store.menu");
        Ext.apply(me, {
            useArrows: true,
            rootVisible: false,
            columns: [
                {xtype: 'treecolumn', text: '名称', dataIndex: 'name',  width: 200},
                {text: 'ID', dataIndex: 'menuId', flex: 1},
                {text: '图标', dataIndex: 'iconcls', flex: 2},
                {text: '链接', dataIndex: 'url', flex: 1},
                {text: '父级 ID', dataIndex: 'parentMenuId', flex: 1},
                {text: '展示序列', dataIndex: 'sequence', flex: 1},
                {text: '菜单级别', dataIndex: 'level', flex: 1}
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
                    me.addMenu();
                }
            }, {
                text: '修改',
                hidden: me.authority.update,
                handler: function () {
                    me.updateMenu();
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
                                me.delMenu();
                            }
                        }
                    });
                }
            }]
        });

        me.callParent(arguments);
    },

    // 新增
    addMenu: function () {
        var me = this;
        Ext.createWidget('menuform', {
            title: '新增',
            wbCallBack: function () {
                me.getStore().load();
            }
        }).show();
    },

    // 修改
    updateMenu: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length == 1) {
                var menuRecord = models[0];
                Ext.createWidget('menuform', {
                    title: '修改',
                    menuUpdate: true,
                    menuRecord: menuRecord,
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

    // 删除
    delMenu: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            var ids = [];
            Ext.Array.each(models, function (model, index, itSelf) {
                ids.push(model.get('menuId'));
            });

            var mask = Ext.MessageBox.wait("菜单删除中...");
            Ext.Ajax.request({
                url: contextPath + '/menu/delete',
                params: {
                    ids: ids
                },
                success: function (response, opts) {
                    mask.close();
                    var data = Ext.decode(response.responseText);
                    Ext.Msg.alert('成功', data.msg, function () {
                        me.getStore().load();
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