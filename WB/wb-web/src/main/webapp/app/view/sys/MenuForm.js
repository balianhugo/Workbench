/**
 * 菜单新增更新表单
 * Created by Administrator on 2016-12-12.
 */
Ext.define('WB.view.sys.MenuForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.menuform',
    requires: ['WB.store.sys.Role', 'WB.view.sys.MenuSelectIcons'],

    // 自定义属性，用于区别是新增还是更新
    menuUpdate: false,
    // 当 menuUpdate 为 true 时加载到 form 表单数据对象
    menuRecord: {},

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'fit',
            constrain: true,
            modal: true,
            items: [{
                xtype: 'form',
                bodyPadding: '0 50 0 0',
                width: 400,
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'right',
                    msgTarget: 'side'
                    //labelWidth: 60
                },
                defaultType: 'textfield',
                items: [{
                    inputType: 'hidden',
                    name: 'menuId'
                }, {
                    fieldLabel: '名  称',
                    name: 'name',
                    maxLength: 20,
                    allowBlank: false
                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '图  标',
                    layout: 'hbox',
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'iconcls',
                            flex: 1
                        }, {
                            xtype: 'button',
                            text: '选择',
                            handler: function () {
                               me.selectIcons();
                            }
                        }
                    ]
                }, {
                    fieldLabel: '链  接',
                    name: 'url',
                    maxLength: 50
                }, {
                    fieldLabel: '父级 ID',
                    name: 'parentMenuId',
                    xtype: 'numberfield',
                    maxLength: 11,
                    hideTrigger: true,
                    keyNavEnabled: false,
                    mouseWheelEnabled: false,
                    allowBlank: false
                }, {
                    fieldLabel: '展示序列',
                    name: 'sequence',
                    maxLength: 5,
                    allowBlank: false
                }, {
                    fieldLabel: '菜单级别',
                    name: 'level',
                    xtype: 'numberfield',
                    maxLength: 1,
                    hideTrigger: true,
                    keyNavEnabled: false,
                    mouseWheelEnabled: false,
                    allowBlank: false
                }],
                buttons: [{
                    text: '重置',
                    handler: function () {
                        if (me.menuUpdate) {
                            me.loadMenuData();
                        } else {
                            this.up('form').getForm().reset();
                        }
                    }
                }, {
                    text: '保存',
                    formBind: true,
                    disabled: true,
                    handler: function () {
                        me.menuSave();
                    }
                }]
            }]
        });

        if (me.menuUpdate) {
            me.on('afterrender', me.loadMenuData, me);
        }
        me.callParent(arguments);
    },

    /* 加载数据 */
    loadMenuData: function () {
        var form = this.down('form').getForm();
        form.loadRecord(this.menuRecord);
    },

    /* 提交表单 */
    menuSave: function () {
        var me = this;
        var url = '';
        if (me.menuUpdate) {
            url = contextPath + '/menu/update';
        } else {
            url = contextPath + '/menu/add';
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

    // 菜单图标选择
    selectIcons: function () {
        var me = this;
        var form = me.down('form').getForm();
        Ext.createWidget('menuicons', {
            wbCallBack: function (iconpath) {
                form.findField('iconcls').setValue(iconpath);
            }
        }).show();
    },

    // 回调函数
    wbCallBack: function () {

    }
});