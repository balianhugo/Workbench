/**
 * 数据类型新增更新表单
 * Created by Administrator on 2016-11-29.
 */
Ext.define('WB.view.sys.TypeDataForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.typedataform',

    // 自定义属性，用于区别是新增还是更新
    isUpdate: false,

    // 当 isUpdate 为 true 时加载到 form 表单数据对象
    dataRecord: {},

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'fit',
            constrain: true,
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
                    msgTarget: 'side',
                    labelWidth: 40
                },
                defaultType: 'textfield',
                items: [{
                    fieldLabel: '名称',
                    readOnly: me.isUpdate,
                    name: 'name',
                    maxLength: 40,
                    allowBlank: false
                }, {
                    xtype: 'combobox',
                    fieldLabel: '类型',
                    editable: false,
                    name: 'type',
                    store: {
                        fields: ['name', 'value'],
                        data : [
                            {"name":"字符串", "value": 0},
                            {"name":"SQL语句", "value": 1},
                            {"name":"参数值", "value": 2}
                        ]
                    },
                    queryMode: 'local',
                    displayField: 'name',
                    valueField: 'value',
                    allowBlank: false
                }, {
                    xtype: 'textarea',
                    fieldLabel: '数值',
                    grow: true,
                    name: 'value',
                    maxLength: 300,
                    emptyText: '例：字符串格式( a:b,c:d,e:f) SQL语句格式(select xx as name, yy as value from zz) 参数值(value)',
                    allowBlank: false
                }, {
                    xtype: 'textarea',
                    fieldLabel: '描述',
                    grow: true,
                    name: 'description',
                    maxLength: 50,
                    allowBlank: false
                }],
                buttons: [{
                    text: '重置',
                    handler: function () {
                        if(me.isUpdate){
                            me.loadUserData();
                        }else{
                            this.up('form').getForm().reset();
                        }
                    }
                }, {
                    text: '保存',
                    formBind: true,
                    disabled: true,
                    handler: function () {
                        me.mySave();
                    }
                }]
            }]
        });

        if(me.isUpdate){
            me.on('afterrender', me.loadData, me);
        }
        me.callParent(arguments);
    },

    /* 加载用户数据 */
    loadData: function () {
        var form = this.down('form').getForm();
        form.loadRecord(this.dataRecord);
    },

    /* 提交表单 */
    mySave:function () {
        var me = this;
        var url = '';
        if(me.isUpdate){
            url = contextPath + '/typeData/update';
        }else{
            url = contextPath + '/typeData/add';
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

    // 回调函数
    wbCallBack: function () {

    }
});