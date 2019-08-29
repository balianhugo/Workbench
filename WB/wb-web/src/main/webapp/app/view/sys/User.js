/**
 * 管理用户页面
 * Created by Administrator on 2016-11-25.
 */
Ext.define('WB.view.sys.User', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.userlist',
    requires: [
        'WB.store.sys.User',
        'WB.view.sys.UserForm',
        'WB.view.sys.UserBindRoleForm'
    ],

    // 自定义界面权限配置，对应界面功能操作
    authority: {
        search: false,
        add: false,
        update: false,
        remove: false,
        bind: false,
        freeze: false
    },

    initComponent: function () {
        var me = this;
        // 初始化时 grid 和 paging 必须使用同一个 store 对象，用 store 懒加载方式，paging 会获取不到对象，因此这里优先初始化 store
        me.store = Ext.create("WB.store.sys.User");
        Ext.apply(me, {
            //store: {type: 'user'}, //store 懒加载方式
            columns: [
                {text: '账号', dataIndex: 'loginName', flex: 2},
               // {text: '密码', dataIndex: 'loginPassword', flex: 1},
                {text: '姓名', dataIndex: 'nickName', flex: 2},
                {
                    text: '性别',
                    dataIndex: 'gender',
                    flex: 1,
                    renderer: function (value) {
                        switch (value) {
                            case 'F':
                                return "女";
                            case 'M':
                                return "男";
                            default:
                                return "保密";
                        }
                    }
                },
                {text: '手机号', dataIndex: 'mobile', flex: 2},
                {text: '邮箱', dataIndex: 'email', flex: 2},
                {text: '部门', dataIndex: 'orgName', flex: 2},
                {
                    text: '状态',
                    dataIndex: 'status',
                    flex: 1,
                    renderer: function (value) {
                        switch (value) {
                            case 0:
                                return "正常";
                            case 1:
                                return "冻结";
                            case 2:
                                return "删除";
                        }
                        return "未知";
                    }
                }
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
                    me.addUser();
                }
            }, {
                text: '修改',
                hidden: me.authority.update,
                handler: function () {
                    me.updateUser();
                }
            }, {
                text: '冻结',
                hidden: true,
                handler: function () {
                    Ext.Msg.show({
                        title:'提示',
                        msg: '您确定要冻结当前用户吗？',
                        buttons: Ext.Msg.YESNO,
                        icon: Ext.Msg.WARNING,
                        fn: function(btn) {
                            if (btn === 'yes') {
                                me.freezeUser();
                            }
                        }
                    });

                }
            }, {
                text: '角色',
                hidden: me.authority.bind,
                handler: function () {
                    me.bindRole();
                }
            }, '->', {
                xtype: 'textfield',
                fieldLabel: '账号',
                name: 'loginName',
                labelAlign: 'right',
                labelWidth: 50,
                maxLength: 20
            }, {
                xtype: 'combo',
                fieldLabel: '状态',
                name: 'status',
                labelAlign: 'right',
                labelWidth: 50,
                editable: false,
                store: {
                    fields: ['name', 'value'],
                    proxy: {
                        type: 'ajax',
                        url: contextPath + '/wb/typeData?name=wb_user_status&type=0',
                        reader: {
                            type: 'json',
                            root: 'data',
                            successProperty: 'success'
                        }
                    }
                },
                queryMode: 'remote',
                displayField: 'name',
                valueField: 'value'
            }, {
                text: '查询',
                hidden: me.authority.search,
                handler: function () {
                    me.searchUser();
                }
            }, {
                text: '重置',
                handler: function () {
                    me.down('textfield[name=loginName]').reset();
                    me.down('combo[name=status]').reset();
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
    searchUser: function () {
        var loginName = this.down('textfield[name=loginName]');
        if (loginName.isValid()) {
            var status = this.down('combo[name=status]');
            this.getStore().proxy.extraParams = {
                loginName: loginName.getValue(),
                status: status.getValue()
            };
            this.getStore().loadPage(1);
        } else {
            Ext.Msg.alert('提示', '查询参数不合法！');
        }
    },

    // 新增用户
    addUser: function () {
        var me = this;
        Ext.createWidget('userform', {
            title: '新增',
            wbCallBack: function () {
                me.getStore().load();
            }
        }).show();
    },

    // 修改用户
    updateUser: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length === 1) {
                var userRecord = models[0];
                Ext.createWidget('userform', {
                    title: '修改',
                    userUpdate: true,
                    userRecord: userRecord,
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

    // 冻结用户
    freezeUser: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            var ids = [];
            Ext.Array.each(models, function (model, index, itSelf) {
                ids.push(model.get('userId'));
            });

            var mask = Ext.MessageBox.wait("系统处理中...");
            Ext.Ajax.request({
                url: contextPath + '/user/freezeUser',
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
    },

    // 用户绑定角色
    bindRole: function () {
        var me = this;
        var sm = me.getSelectionModel();
        if (sm.hasSelection()) {
            var models = sm.getSelection();
            if (models.length == 1) {
                var userId = models[0].get('userId');
                Ext.createWidget('userbindroleform', {
                    title: '角色',
                    userId: userId
                }).show();
            } else {
                Ext.Msg.alert('提示', '请选择一条数据进行操作！');
            }
        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    }
});