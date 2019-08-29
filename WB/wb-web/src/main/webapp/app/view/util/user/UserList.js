/**
 * 用户选择页面用户列表
 * Created by Administrator on 2016-11-25.
 */
Ext.define('WB.view.util.user.UserList', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.utiluserlist',

    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            columns: [
                {text: '账号', dataIndex: 'loginName', flex: 1},
                {text: '姓名', dataIndex: 'nickName', flex: 1},
                {text: '手机号', dataIndex: 'mobile', flex: 1},
                {
                    text : '操作',
                    renderer : function(value) {
                        return "<span style='color:blue;cursor:pointer'>选择</span>";
                    },
                    width : 100
                }
            ],
            viewConfig: {
                loadingText: '读取中...'
            },
            // selModel: {
            //     mode: 'MULTI'
            // },
            // selType: 'checkboxmodel'
            listeners: {
                cellclick: {
                    fn: function(o, td, cellIndex, record, tr, rowIndex, e, eOpts ){
                        if(cellIndex === 3){
                            me.wbCallback(record);
                        }
                    }
                }
            },
            store: {
                model: 'WB.model.sys.User',
                autoLoad: false,
                proxy: {
                    type: 'ajax',
                    url: contextPath + '/wb/selectUser',
                    reader: {
                        type: 'json',
                        root: 'data',
                        totalProperty: 'total',
                        successProperty: 'success'
                    }
                }
            },
            tbar: [
                {
                    xtype: 'textfield',
                    fieldLabel: '账号',
                    name: 'loginName',
                    labelAlign: 'right',
                    labelWidth: 50,
                    maxLength: 20
                },{
                    text: '查询',
                    handler: function () {
                        me.searchByUser();
                    }
                }
            ]

        });

        this.callParent(arguments);
    },

    // 查询
    searchByUser: function () {
        var me = this;
        var loginName = me.down('textfield[name=loginName]').getValue();
        me.getStore().load({
            params: {
                qtype: 0,
                qval: loginName
            }
        });
    },

    // 界面回调函数
    wbCallback: function (record) {

    }

});