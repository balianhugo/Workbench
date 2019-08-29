/**
 * 用户新增更新表单
 * Created by Administrator on 2016-11-29.
 */
Ext.define('WB.view.sys.UserForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.userform',
    requires: ['WB.store.sys.User', "WB.view.sys.OrgChooseWin"],

    // 自定义属性，用于区别是新增还是更新
    userUpdate: false,
    // 当 userUpdate 为 true 时加载到 form 表单数据对象
    userRecord: {},

    initComponent: function () {
        var me = this;

        // var deptStore = Ext.create("Ext.data.Store", {
        //     fields: [
        //         {name: 'name', type: 'string'},
        //         {name: 'value', type: 'int'}
        //     ],
        //     autoLoad: true,
        //     proxy: {
        //         type: 'ajax',
        //         url: contextPath + '/wb/typeData?name=doc_dept_select2&type=1',
        //         reader: {
        //             type: 'json',
        //             root: 'data',
        //             successProperty: 'success'
        //         }
        //     },
        //     listeners: {
        //         load: {
        //             fn: function(thisE, newValue, oldValue, eOpts){
        //                 var orgcombo = me.down('form').getForm().findField('orgId');
        //                 orgcombo.queryMode='local';
        //             }
        //         }
        //     }
        // });

        Ext.apply(me, {
            layout: 'fit',
            modal: true,
            items: [{
                xtype: 'form',
                bodyPadding: 10,
                border: false,
                width: 400,
                layout: 'anchor',
                defaults: {
                    anchor: '100%',
                    labelAlign: 'right',
                    // msgTarget: 'side',
                    labelWidth: 50
                },
                defaultType: 'textfield',
                items: [{
                    inputType: 'hidden',
                    name: 'userId'
                },{
                    fieldLabel: '账  号<span style="color:red;">*</span>',
                    readOnly: me.userUpdate,
                    name: 'loginName',
                    maxLength: 20,
                    allowBlank: false
                }, {
                    fieldLabel: '密  码',
                    inputType: 'password',
                    name: 'loginPassword',
                    maxLength: 15,
                    emptyText: me.userUpdate ? '' : '空默认初始化为：123456',
                    allowBlank: true
                }, {
                    fieldLabel: '姓  名<span style="color:red;">*</span>',
                    name: 'nickName',
                    maxLength: 15,
                    allowBlank: false
                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '性  别<span style="color:red;">*</span>',
                    layout: 'hbox',
                    defaultType: 'radiofield',
                    defaults: {
                        flex: 1
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
                }, {
                    xtype: 'fieldcontainer',
                    fieldLabel: '部  门<span style="color:red;">*</span>',
                    layout: 'hbox',
                    items: [
                        {
                            xtype: 'textfield',
                            inputType: 'hidden',
                            name: 'orgId',
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            name: 'orgName',
                            readOnly: true,
                            allowBlank: false,
                            flex: 1
                        }, {
                            xtype: 'button',
                            text: '选择',
                            handler: function () {
                                me.selectOrg();
                            }
                        }
                    ]
                },
                //     {
                //     xtype: 'combo',
                //     fieldLabel: '部  门<span style="color:red;">*</span>',
                //     name: 'orgId',
                //     editable: false,
                //     listeners: {
                //         change: {
                //             fn: function(thisE, newValue, oldValue, eOpts){
                //                  me.down('form').getForm().findField("orgName").setValue(thisE.getRawValue());
                //             }
                //         }
                //     },
                //     store: deptStore,
                //     queryMode: 'remote',
                //     triggerAction : 'all',
                //     displayField: 'name',
                //     valueField: 'value'
                // },{
                //     fieldLabel: '部  门',
                //     hidden: true,
                //     name: 'orgName',
                //     allowBlank: true
                // },
                    {
                    fieldLabel: '职  位',
                    name: 'position',
                    maxLength: 40,
                    allowBlank: true
                },{
                    fieldLabel: '手机号<span style="color:red;">*</span>',
                    name: 'mobile',
                    xtype: 'numberfield',
                    minLength: 11,
                    maxLength: 11,
                    regex: /^1\d{10}$/,
                    regexText: '手机号码不合法',
                    hideTrigger: true,
                    keyNavEnabled: false,
                    mouseWheelEnabled: false,
                    allowBlank: false
                },{
                    fieldLabel: '邮  箱<span style="color:red;">*</span>',
                    name: 'email',
                    maxLength: 60,
                    vtype: 'email',
                    allowBlank: false
                }, {
                    xtype: 'combo',
                    fieldLabel: '状  态<span style="color:red;">*</span>',
                    allowBlank: false,
                    name: 'status',
                    editable: false,
                    value: 0,
                    store: {
                        fields: [
                            {name: 'name', type: 'string'},
                            {name: 'value', type: 'int'}
                        ],
                        autoLoad: true,
                        listeners: {
                            load: {
                                fn: function(thisE, newValue, oldValue, eOpts){
                                    var orgcombo = me.down('form').getForm().findField('status');
                                    orgcombo.queryMode='local';
                                }
                            }
                        },
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
                    triggerAction : 'all',
                    queryMode: 'remote',
                    displayField: 'name',
                    valueField: 'value'
                }],
                buttons: [{
                    text: '重置',
                    handler: function () {
                        if(me.userUpdate){
                            me.loadUserData();
                        }else{
                            this.up('form').getForm().reset();
                        }
                    }
                }, {
                    text: '保存',
                    // formBind: true,
                    // disabled: true,
                    handler: function () {
                        me.userSave();
                    }
                }]
            }]
        });

        if(me.userUpdate){
            me.on('afterrender', me.loadUserData, me);
        }
        me.callParent(arguments);
    },

    /* 加载用户数据 */
    loadUserData: function () {
        var me = this;
        var form = me.down('form').getForm();
        form.loadRecord(me.userRecord);
        form.findField('loginPassword').reset();
    },

    /* 提交表单 */
    userSave:function () {
        var me = this;
        var url = '';
        if(me.userUpdate){
            url = contextPath + '/user/update';
        }else{
            url = contextPath + '/user/add';
        }
        var form = me.down('form').getForm();
        if (form.isValid()) {
            form.submit({
                url: url,
                waitMsg: '系统处理中...',
                success: function (form, action) {
                    Ext.Msg.alert('成功', action.result.msg, function(){
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

    //部门选择
    selectOrg: function () {
        var me = this;
        var form = me.down('form').getForm();
        Ext.createWidget('orgchoose', {
            wbCallBack: function (orgId, orgName) {
                form.findField('orgId').setValue(orgId);
                form.findField('orgName').setValue(orgName);
            }
        }).show();
    },

    // 回调函数
    wbCallBack: function () {

    }
});