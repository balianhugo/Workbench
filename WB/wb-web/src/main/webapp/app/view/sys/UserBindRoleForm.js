/**
 * 用户绑定角色操作表单
 * Created by Administrator on 2016-12-19.
 */
Ext.define('WB.view.sys.UserBindRoleForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.userbindroleform',

    userId: '', // 扩展属性用于加载数据时的查询参数

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'fit',
            modal: true,
            items: [{
                xtype: 'form',
                bodyPadding: 10,
                border: false,
                autoScroll: true,
                width: 300,
                height: 450,
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'right',
                    labelWidth: 50
                },
                defaultType: 'checkboxfield',
                buttons: [{
                    text: '重置',
                    handler: function () {
                        me.loadBindData();
                    }
                }, {
                    text: '保存',
                    formBind: true,
                    disabled: true,
                    handler: function () {
                        me.bindSave();
                    }
                }]
            }]
        });
        me.on('beforerender', me.loadBindData, me);

        me.callParent(arguments);
    },

    // 加载角色绑定数据
    loadBindData: function () {
        var me = this;
        Ext.Ajax.request({
            url: contextPath + '/user/bindRoles',
            params: {
                userId: me.userId
            },
            success: function (response, opts) {
                var json = Ext.decode(response.responseText);
                var form = me.down('form');
                form.removeAll();
                Ext.Array.each(json.data, function (item, index, countriesItSelf) {
                    form.add({
                        boxLabel: item.roleName + ' --- ' + item.description,
                        checked: item.bind,
                        name: 'roleId',
                        inputValue: item.roleId
                    });
                });
            },
            failure: function (response, opts) {
                Ext.Msg.alert('失败', response.status);
            }
        });
    },

    // 保存绑定信息
    bindSave: function () {
        var me = this;
        var form = me.down('form').getForm();
        form.submit({
            url: contextPath + '/user/bind',
            params: {
                userId: me.userId
            },
            waitMsg: '系统处理中...',
            success: function (form, action) {
                Ext.Msg.alert('成功', action.result.msg, function () {
                    me.close();
                });
            },
            failure: function (form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.CLIENT_INVALID:
                        Ext.Msg.alert('失败', '表单提交了不合法的数据！');
                        break;
                    case Ext.form.action.Action.CONNECT_FAILURE:
                        Ext.Msg.alert('失败', '服务器连接失败！');
                        break;
                    case Ext.form.action.Action.SERVER_INVALID:
                        Ext.Msg.alert('失败', action.result.msg);
                }
            }
        });
    }
});