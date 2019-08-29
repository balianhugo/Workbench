Ext.define('WB.view.sys.OrgChooseWin', {
    extend: 'Ext.window.Window',
    alias: 'widget.orgchoose',
    requires: [ "WB.store.sys.Organization"],

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            title: '部门选择',
            height: 500,
            layout: 'fit',
            constrain: true,
            modal: true,
            items: [{
                xtype: 'treepanel',
                width: 300,
                // useArrows: true,
                rootVisible: false,
                store: Ext.create("store.organization"),
                columns: [
                    { xtype: 'treecolumn', header: '部门名称', dataIndex: 'name', flex: 1 }
                ],
                region: 'west',
                listeners: {
                    itemclick: {
                        fn: function (view, record, item, index, e, eOpts) {
                            me.wbCallBack(record.raw.id, record.raw.name);
                            me.close();
                        }
                    }
                }
            }]
        });

        me.callParent(arguments);
    },

    // 回调函数
    wbCallBack: function (orgId, orgName) {

    }
});