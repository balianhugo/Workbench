/**
 * 平台登入 ExtJS 页面
 *
 * Created by Administrator on 2016-11-17.
 */
Ext.define("WB.view.Login", {
    extend: 'Ext.container.Viewport',

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'center'
            },

            items: [{
                xtype: 'form',
                title: 'WB 系统管理平台',
                bodyPadding: 10,
                border: false,
                width: 300,

                // The form will submit an AJAX request to this URL when submitted
                url: contextPath + '/public/doLogin',

                // Fields will be arranged vertically, stretched to full width
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'right',
                    labelWidth: 50
                },

                // The fields
                defaultType: 'textfield',
                items: [{
                    fieldLabel: '账 号',
                    name: 'loginName',
                    maxLength: 20,
                    allowBlank: false
                }, {
                    fieldLabel: '密 码',
                    inputType: 'password',
                    name: 'loginPassword',
                    maxLength: 15,
                    allowBlank: false
                }],

                // Reset and Submit buttons
                buttons: [{
                    text: '重置',
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                }, {
                    text: '登入',
                    formBind: true, //only enabled once the form is valid
                    disabled: true,
                    handler: function () {
                        me.doLogin();
                    }
                }]
            }]
        });
        this.callParent(arguments);
    },

    //提交处理
    doLogin: function () {
        var form = this.down('form').getForm();
        if (form.isValid()) {
            form.submit({
                waitMsg:'系统登入处理中',
                success: function (form, action) {

                    Ext.MessageBox.show({
                        msg: action.result.msg,
                        width: 300,
                        wait: true,
                        waitConfig: {interval: 200},
                        animateTarget: form
                    });

                    window.location.href = contextPath + action.result.data;
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

Ext.onReady(function () {
    var login = Ext.create("WB.view.Login");

    //绑定回车事件
    new Ext.util.KeyNav({
        target : Ext.getBody(),
        enter  : function(e){
            login.doLogin();
        },
        scope : this
    });
});