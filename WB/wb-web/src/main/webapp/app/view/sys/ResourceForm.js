/**
 * 资源新增更新表单
 * Created by Administrator on 2016-12-12.
 */
Ext.define('WB.view.sys.ResourceForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.resourceform',
    requires: ['WB.store.sys.Resource'],

    // 自定义属性，用于区别是新增还是更新
    resourceUpdate: false,
    // 当 userUpdate 为 true 时加载到 form 表单数据对象
    resourceRecord: {},

    // 关联的菜单 ID
    menuId: '',

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'fit',
            modal: true,
            items: [{
                xtype: 'form',
                bodyPadding: 10,
                border: false,
                width: 300,
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'right',
                    msgTarget: 'side',
                    labelWidth: 50
                },
                defaultType: 'textfield',
                items: [{
                    inputType: 'hidden',
                    name: 'menuId',
                    value: me.menuId
                },{
                    inputType: 'hidden',
                    name: 'resourceId'
                }, {
                    fieldLabel: '资  源',
                    name: 'name',
                    maxLength: 20,
                    allowBlank: false
                }, {
                    fieldLabel: '链  接',
                    name: 'href',
                    maxLength: 50,
                    allowBlank: false
                }, {
                    xtype: 'textarea',
                    fieldLabel: '描  述',
                    grow: true,
                    name: 'description',
                    maxLength: 50,
                    allowBlank: false
                }],
                buttons: [{
                    text: '重置',
                    handler: function () {
                        if (me.resourceUpdate) {
                            me.loadResourceData();
                        } else {
                            this.up('form').getForm().reset();
                        }
                    }
                }, {
                    text: '保存',
                    formBind: true,
                    disabled: true,
                    handler: function () {
                        me.resourceSave();
                    }
                }]
            }]
        });

        if (me.resourceUpdate) {
            me.on('afterrender', me.loadResourceData, me);
        }
        me.callParent(arguments);
    },

    /* 加载数据 */
    loadResourceData: function () {
        var form = this.down('form').getForm();
        form.loadRecord(this.resourceRecord);
    },

    /* 提交表单 */
    resourceSave: function () {
        var me = this;
        var url = '';
        if (me.resourceUpdate) {
            url = contextPath + '/resource/update';
        } else {
            url = contextPath + '/resource/add';
        }
        var form = me.down('form').getForm();
        if (form.isValid()) {
            form.submit({
                url: url,
                waitMsg: '系统处理中...',
                success: function (form, action) {
                    Ext.Msg.alert('成功', action.result.msg, function () {
                        me.wbCallBack();
                        me.close();
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
    },

    // 回调函数
    wbCallBack: function () {

    }
});