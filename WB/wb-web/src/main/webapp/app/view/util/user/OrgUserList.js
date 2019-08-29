/**
 * 组织用户查询
 */
Ext.define("WB.view.util.user.OrgUserList", {
    extend: 'Ext.container.Container',
    alias: 'widget.utilorguserlist',

    // 记录当前操作的对应组织 ID
    currentOrgId: 0,

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'border',
            items: [{
                xtype: 'treepanel',
                useArrows: true,
                rootVisible: false,
                store: Ext.create("store.organization"),
                columns: [
                    { xtype: 'treecolumn', header: '组织名称', dataIndex: 'name', flex: 1 }
                ],
                width: 400,
                region: 'west',
                listeners: {
                    itemclick: {
                        fn: function (view, record, item, index, e, eOpts) {
                            // 根据组织 orgId 加载右边的用户列表信息
                            me.currentOrgId = record.raw.id;
                            me.down('grid').getStore().load({
                                params: {
                                    qtype: 2,
                                    qval: me.currentOrgId
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
                }
            }]
        });

        this.callParent(arguments);
    },

    // 界面回调函数
    wbCallback: function (record) {

    }
});