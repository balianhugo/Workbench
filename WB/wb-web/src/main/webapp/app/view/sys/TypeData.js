/**
 * 数据类型管理页面
 * Created by Administrator on 2017/9/23.
 */
Ext.define('WB.view.sys.TypeData', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.typedatalist',
    requires: ['WB.store.sys.TypeData', 'WB.view.sys.TypeDataForm'],

    // 自定义界面权限配置，对应界面功能操作
    authority: {
        add:false,
        update:false,
        page:false,
        list:false,
        remove:false
    },

    initComponent: function () {
        var me = this;
        me.store = Ext.create("WB.store.sys.TypeData");
        Ext.apply(me, {
            columns: [
                {text: '名称', dataIndex: 'name', flex: 1},
                {
                    text: '类型',
                    dataIndex: 'type',
                    flex: 1,
                    renderer: function (value) {
                        switch (value) {
                            case 0:
                                return "字符串";
                            case 1:
                                return "SQL语句";
                            case 2:
                                return "参数值";
                            default:
                                return "--";
                        }
                    }

                },
                {text: '数值', dataIndex: 'value', flex: 2},
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
                    me.addType();
                }
            }, {
                text: '修改',
                hidden: me.authority.update,
                handler: function () {
                    me.updateType();
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
                                me.delType();
                            }
                        }
                    });

                }
            }, '->', {
                xtype: 'textfield',
                fieldLabel: '名称',
                name: 'name',
                labelAlign: 'right',
                labelWidth: 50,
                maxLength: 20
            }, {
                xtype: 'combo',
                fieldLabel: '类型',
                name: 'type',
                labelAlign: 'right',
                labelWidth: 50,
                editable: false,
                store: {
                    fields: ['name', 'value'],
                    data : [
                        {"name":"字符串", "value": 0},
                        {"name":"SQL语句", "value": 1},
                        {"name":"参数值", "value": 2}
                    ]
                },
                queryMode: 'local',
                displayField: 'name',
                valueField: 'value'
            }, {
                text: '查询',
                hidden: me.authority.list,
                handler: function () {
                    me.searchType();
                }
            }, {
                text: '重置',
                handler: function () {
                    me.down('textfield[name=name]').reset();
                    me.down('textfield[name=type]').reset();
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

    // 查询方法
    searchType: function () {
        var name = this.down('textfield[name=name]');
        var type = this.down('textfield[name=type]');
        if (name.isValid()) {
            this.getStore().proxy.extraParams = {
                name: name.getValue(),
                type: type.getValue()
            };
            this.getStore().loadPage(1);
        } else {
            Ext.Msg.alert('提示', '查询参数不合法！');
        }
    },

    // 新增
    addType: function () {
        var me = this;
        Ext.createWidget('typedataform', {
            title: '新增',
            wbCallBack: function () {
                me.getStore().load();
            }
        }).show();
    },

    // 修改
    updateType: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length === 1) {
                var userRecord = models[0];
                Ext.createWidget('typedataform', {
                    title: '修改',
                    isUpdate: true,
                    dataRecord: userRecord,
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
    delType: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            var ids = [];
            Ext.Array.each(models, function (model, index, itSelf) {
                ids.push(model.get('name'));
            });

            var mask = Ext.MessageBox.wait("数据删除中...");
            Ext.Ajax.request({
                url: contextPath + '/typeData/delete',
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
                    if (response.status === 200) {
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