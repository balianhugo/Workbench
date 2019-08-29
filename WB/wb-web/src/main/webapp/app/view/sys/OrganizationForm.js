/**
 * 组织新增修改表单
 * Created by Administrator on 2017/10/13.
 */
Ext.define('WB.view.sys.OrganizationForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.organizationform',

    // 自定义属性，用于区别是新增还是更新
    isUpdate: false,
    // 当 isUpdate 为 true 时加载到 form 表单数据对象
    dataRecord: {},

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
                    labelWidth: 40
                },
                defaultType: 'textfield',
                items: [{
                    fieldLabel: '名称',
                    name: 'orgName',
                    maxLength: 20,
                    allowBlank: false
                }, {
                    xtype: 'textfield',
                    fieldLabel: '编号',
                    name: 'orgCode',
                    maxLength: 20,
                    allowBlank: false
                }],
                buttons: [{
                    text: '重置',
                    handler: function () {
                        if (me.isUpdate) {
                            me.loadFormData();
                        }else{
                            this.up('form').getForm().reset();
                        }
                    }
                }, {
                    text: '保存',
                    formBind: true,
                    disabled: true,
                    handler: function () {
                        me.dataSave();
                    }
                }]
            }]
        });

        if (me.isUpdate) {
            me.on('afterrender', me.loadFormData, me);
        }

        me.callParent(arguments);
    },

    /* 加载数据 */
    loadFormData: function () {
        var me = this;
        var form = me.down('form').getForm();
        form.findField('orgName').setValue(me.dataRecord.orgName);
        form.findField('orgCode').setValue(me.dataRecord.orgCode);
    },

    /* 提交表单 */
    dataSave: function () {
        var me = this;
        var form = me.down('form').getForm();
        me.wbCallBack(form.getValues());
        me.close();
    },

    // 回调函数
    wbCallBack: function (values) {

    }
});