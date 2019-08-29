/**
 * 标签页面
 */
Ext.define('WB.view.sys.Tag', {
    extend: 'Ext.container.Container',
    alias: 'widget.tagpage',
    requires: [
        "WB.store.sys.Organization"
    ],

    // 是否部门管理员
    deptadmin: false,

    // 自定义界面权限配置，对应界面功能操作
    authority: {

    },

    // 记录当前操作的对应组织 ID
    currentOrgId: 0,

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'border',
            items: [
                {
                    xtype: 'treepanel',
                    hidden: me.deptadmin,
                    width: 300,
                   // useArrows: true,
                    rootVisible: true,
                    store: me.deptadmin ? null : Ext.create("store.organization"),
                    columns: [
                        { xtype: 'treecolumn', header: '组织名称', dataIndex: 'name', flex: 1 }
                    ],
                    region: 'west',
                    listeners: {
                        itemclick: {
                            fn: function (view, record, item, index, e, eOpts) {
                                // 根据组织 orgId 加载右边的用户列表信息
                                me.currentOrgId = record.raw.id;
                                if(record.raw.name === "机构组织"){
                                    me.down('fieldset').setTitle('默认标签');
                                }else{
                                    me.down('fieldset').setTitle(record.raw.orgName);
                                }
                                me.loadFormData(me.currentOrgId);
                            }
                        }
                    },
                    dockedItems: [{
                        xtype: 'toolbar',
                        dock: 'bottom',
                        items:[{
                            xtype: 'button',
                            tooltip : '刷新',
                            iconCls: 'x-tbar-loading',
                            handler: function() {
                                me.down('treepanel').getStore().load();
                            }
                        }]
                    }]
                },
                {
                    xtype: 'form',
                    region: 'center',
                    border: false,
                    bodyPadding: 10,
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
                        title: '默认标签',
                        defaultType: 'textfield',
                        padding: '5 15 10 15',
                        layout: 'anchor',
                        defaults: {
                            anchor: '100%',
                            allowBlank: false
                        },
                        items: [{
                            inputType: 'hidden',
                            name: 'orgId',
                            value: 0
                        },{
                            xtype     : 'textareafield',
                            grow      : true,
                            name      : 'tags',
                            fieldLabel: '文档标签<span style="color:red;">*</span>',
                            height: 300,
                            anchor    : '100%'
                        }]
                    }, {
                        xtype: 'container',
                        html: '<span style="color:green">提示：</span>文档标签格式以英文“,”号分隔，如：标签1,标签2,标签3'
                    }],
                    buttonAlign : 'center',
                    buttons: [{
                        text: '保存',
                        formBind: true,
                        disabled: true,
                        handler: function () {
                            me.tagSave();
                        }
                    }],
                    listeners: {
                        afterrender: {
                            fn: function(){
                                if(me.deptadmin){
                                    me.currentOrgId = wb.user.orgId;
                                    me.down('fieldset').setTitle(wb.user.orgName);
                                }
                                me.loadFormData(me.currentOrgId);
                            }
                        }
                    }
                }
            ]
        });

        this.callParent(arguments);
    },

    loadFormData: function (orgId){
        var me = this;
        var form = me.down('form').getForm();
        form.reset();
        form.findField('orgId').setValue(orgId);
        Ext.Ajax.request({
            url: contextPath + '/tag/get',
            params: {
                orgId: orgId
            },
            success: function (response, opts) {
                var json = Ext.decode(response.responseText);
                if(json.success && json.data != null){
                    form.findField('tags').setValue(json.data.tags);
                }
            },
            failure: function (response, opts) {
                if (response.status === 200) {
                    var data = Ext.decode(response.responseText);
                    Ext.Msg.alert('提示', "标签信息获取失败");
                } else {
                    Ext.Msg.alert('提示', "服务器访问失败" + response.status);
                }
            }
        });
    },

    tagSave: function (){
        var me = this;
        var url = contextPath + '/tag/save';

        var form = me.down('form').getForm();
        if (form.isValid()) {

            form.submit({
                url: url,
                waitMsg: '系统处理中...',
                success: function (form, action) {
                    Ext.Msg.alert('提示', action.result.msg);
                },
                failure: function (form, action) {
                    switch (action.failureType) {
                        case Ext.form.action.Action.CLIENT_INVALID:
                            Ext.Msg.alert('提示', '表单提交了不合法的数据！');
                            break;
                        case Ext.form.action.Action.CONNECT_FAILURE:
                            Ext.Msg.alert('提示', '服务器连接超时！');
                            break;
                        case Ext.form.action.Action.SERVER_INVALID:
                            Ext.Msg.alert('提示', action.result.msg);
                    }
                }
            });
        }

    }


});