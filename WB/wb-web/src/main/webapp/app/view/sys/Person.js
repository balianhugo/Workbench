/**
 * 用户个人页面
 * Created by Administrator on 2016-11-25.
 */
Ext.define('WB.view.sys.Person', {
    extend: 'Ext.form.Panel',
    alias: 'widget.persondetail',
    requires: ['WB.model.sys.User'],

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
                labelWidth: 50
            },
            defaults: {
                anchor: '100%'
            },

            items: [{
                xtype: 'fieldset',
                title: '用户信息',
                defaultType: 'textfield',
                padding: '5 15 10 15',
                layout: 'anchor',
                defaults: {
                    anchor: '100%'
                },
                items: [{
                    inputType: 'hidden',
                    name: 'userId'
                },{
                    fieldLabel: '账  号',
                    readOnly: true,
                    name: 'loginName',
                    maxLength: 20,
                    allowBlank: false
                }, {
                    fieldLabel: '姓  名',
                    name: 'nickName',
                    maxLength: 15,
                    allowBlank: false
                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '性  别',
                    layout: 'hbox',
                    defaultType: 'radiofield',
                    defaults: {
                       // flex: 1
                        width: 75
                    },
                    items: [{
                        boxLabel: '男',
                        name: 'gender',
                        checked: true,
                        inputValue: 'M'
                    }, {
                        boxLabel: '女',
                        name: 'gender',
                        inputValue: 'F'
                    }, {
                        boxLabel: '保密',
                        name: 'gender',
                        hidden: true,
                        inputValue: 'C'
                    }]
                },{
                    xtype: 'combo',
                    fieldLabel: '部门',
                    hidden: true,
                    name: 'orgId',
                    editable: false,
                    listeners: {
                        change: {
                            fn: function(thisE, newValue, oldValue, eOpts){
                                me.getForm().findField("orgName").setValue(thisE.getRawValue());
                            }
                        }
                    },
                    store: {
                        autoLoad: true,
                        fields: [
                            {name: 'name', type: 'string'},
                            {name: 'value', type: 'int'}
                        ],
                        listeners: {
                            load: {
                                fn: function(thisE, newValue, oldValue, eOpts){
                                    var orgcombo = me.getForm().findField('orgId');
                                    orgcombo.queryMode='local';
                                }
                            }
                        },
                        proxy: {
                            type: 'ajax',
                            url: contextPath + '/wb/typeData?name=doc_dept_select2&type=1',
                            reader: {
                                type: 'json',
                                root: 'data',
                                successProperty: 'success'
                            }
                        }
                    },
                    triggerAction : 'all',
                    queryMode: 'remote',
                    displayField: 'name',
                    valueField: 'value'
                },{
                    fieldLabel: '部  门',
                    readOnly: true,
                    name: 'orgName',
                    allowBlank: true
                },{
                    fieldLabel: '职  位',
                    name: 'position',
                    maxLength: 40,
                    allowBlank: true
                }, {
                    fieldLabel: '手机号',
                    name: 'mobile',
                    xtype: 'numberfield',
                    regex: /^1\d{10}$/,
                    regexText: '手机号码不合法',
                    minLength: 11,
                    maxLength: 11,
                    hideTrigger: true,
                    keyNavEnabled: false,
                    mouseWheelEnabled: false,
                    allowBlank: false
                },{
                    fieldLabel: '邮  箱',
                    name: 'email',
                    maxLength: 60,
                    vtype: 'email',
                    allowBlank: false
                }]
            }],

            buttons: [{
                text: '重置',
                handler: function () {
                    me.loadUserData();
                }
            }, {
                text: '保存',
                hidden: me.authority.updateUser,
                formBind: true,
                disabled: true,
                handler: function () {
                    me.updatePerson();
                }
            }]

        });

        me.on('afterrender', me.loadUserData, me);

        me.callParent(arguments);
    },

    /* 加载用户数据 */
    loadUserData: function () {
        var form = this.getForm();
        form.reset();
        form.loadRecord(Ext.create('WB.model.sys.User', wb.user));
    },

    /* 提交表单信息 */
    updatePerson: function () {
        var me = this;
        var url = contextPath + '/person/updateUser';

        var form = me.getForm();
        if (form.isValid()) {
            form.submit({
                url: url,
                waitMsg: '系统处理中...',
                success: function (form, action) {
                    Ext.Msg.alert('成功', action.result.msg, function() {
                        window.location.href = contextPath + "/wb/main";
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