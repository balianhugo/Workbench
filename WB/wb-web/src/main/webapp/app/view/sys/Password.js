/**
 * 用户密码修改
 * Created by Administrator on 2017/4/17.
 */
Ext.define('WB.view.sys.Password', {
    extend: 'Ext.form.Panel',
    alias: 'widget.password',

    // 自定义界面权限配置，对应界面功能操作
    authority: {
        update: false
    },

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            url: '',
            border: false,
            bodyPadding: 20,
            buttonAlign : 'left',
            autoScroll:true,
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 75
            },
            defaults: {
                anchor: '100%'
            },

            items: [{
                xtype: 'fieldset',
                title: '用户密码',
                defaultType: 'textfield',
                padding: '5 15 10 15',
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    allowBlank: false
                },
                items: [{
                    inputType: 'hidden',
                    name: 'userId',
                    value: wb.user.userId
                },{
                    fieldLabel: '旧密码',
                    inputType: 'password',
                    name: 'oldPassword',
                    maxLength: 15
                },{
                    fieldLabel: '新密码',
                    inputType: 'password',
                    name: 'newPassword',
                    maxLength: 15
                },{
                    fieldLabel: '确认新密码',
                    inputType: 'password',
                    name: 'checkNewPassword',
                    maxLength: 15
                }]
            }],

            buttons: [{
                text: '重置',
                handler: function () {
                    me.getForm().reset();
                }
            }, {
                text: '保存',
                hidden: me.authority.updatePassword,
                formBind: true,
                disabled: true,
                handler: function () {
                    me.updatePassword();
                }
            }]

        });

        me.callParent(arguments);
    },

    /* 提交表单信息 */
    updatePassword: function () {
        var me = this;
        var url = contextPath + '/person/updatePassword';

        var form = me.getForm();
        if (form.isValid()) {

            var newPassword = form.findField('newPassword').getValue();
            var checkNewPassword = form.findField('checkNewPassword').getValue();

            if(newPassword !== checkNewPassword){
                Ext.Msg.alert("提示", "新密码两次输入值不一致！");
                return false;
            }

            form.submit({
                url: url,
                waitMsg: '系统处理中...',
                success: function (form, action) {
                    Ext.Msg.alert('成功', action.result.msg, function(){
                        window.location.href = contextPath + "/public/doLogout";
                    });
                },
                failure: function (form, action) {
                    switch (action.failureType) {
                        case Ext.form.action.Action.CLIENT_INVALID:
                            Ext.Msg.alert('失败', '表单提交了不合法的数据！');
                            break;
                        case Ext.form.action.Action.CONNECT_FAILURE:
                            Ext.Msg.alert('失败', '服务器连接超时！');
                            break;
                        case Ext.form.action.Action.SERVER_INVALID:
                            Ext.Msg.alert('失败', action.result.msg);
                    }
                }
            });
        }

    }

});