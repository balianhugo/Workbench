/**
 * 角色用户查询
 */
Ext.define("WB.view.util.user.RoleUserList", {
    extend: 'Ext.container.Container',
    alias: 'widget.utilroleuserlist',

    roleStore: '',
    userStore: '',

    initComponent: function () {
        var me = this;
        me.roleStore = Ext.create("Ext.data.Store", {
            fields: [
                {name: 'roleName', type: 'string'},
                {name: 'description',  type: 'string'},
                {name: 'roleId',       type: 'int'}
            ],
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: contextPath + '/wb/roleList',
                reader: {
                    type: 'json',
                    root: 'data',
                    totalProperty: 'total',
                    successProperty: 'success'
                }
            }
        });

        me.userStore = Ext.create("Ext.data.Store", {
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
        });

        Ext.apply(me, {
            layout: 'border',
            items: [{
                xtype: 'grid',
                region: 'west',
                store: me.roleStore,
                width: 400,
                columns: [
                    {text: '角色', dataIndex: 'roleName', flex: 1},
                    {text: '描述', dataIndex: 'description', flex: 2}
                ],
                viewConfig: {
                    loadingText: '读取中...'
                },
                listeners: {
                    cellclick: {
                        fn: function(o, td, cellIndex, record, tr, rowIndex, e, eOpts ){
                            me.userStore.load({
                                params: {
                                    qtype: 1,
                                    qval: record.get('roleId')
                                }
                            });
                        }
                    }
                }
            }, {
                xtype: 'grid',
                region: 'center',
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
                store: me.userStore
            }]
        });

        this.callParent(arguments);
    },

    // 界面回调函数
    wbCallback: function (record) {

    }
});