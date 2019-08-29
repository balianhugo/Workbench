/**
 * 组织管理
 * Created by Administrator on 2017/10/12.
 */
Ext.define('WB.view.sys.Organization', {
    extend: 'Ext.container.Container',
    alias: 'widget.organization',
    requires: [
        "WB.store.sys.Organization",
        "WB.store.sys.OrgUser",
        'WB.view.sys.OrganizationForm',
        'WB.view.util.user.UserSelectFormWin'
    ],

    // 自定义界面权限配置，对应界面功能操作
    authority: {
        add: false,
        update: false,
        remove: false,
        bind: false,
        unbind: false
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
                   // useArrows: true,
                    rootVisible: true,
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
                                        orgId: me.currentOrgId
                                    }
                                });
                            }
                        }
                    },
                    tbar: [{
                        text: '新增',
                        hidden: me.authority.add,
                        handler: function () {
                            me.addOrganization()
                        }
                    }, {
                        text: '修改',
                        hidden: me.authority.update,
                        handler: function () {
                            me.updateOrganization();
                        }
                    }, {
                        text: '删除',
                        hidden: me.authority.remove,
                        handler: function () {
                            me.delOrganization();
                        }
                    }]
                },
                {
                    xtype: 'grid',
                    region: 'center',
                    store: {
                        type: 'orguser'
                    },
                    columns: [
                        {text: '账号', dataIndex: 'loginName', flex: 1},
                        {text: '姓名', dataIndex: 'nickName', flex: 1},
                        {text: '手机号', dataIndex: 'mobile', flex: 1}
                    ],
                    viewConfig: {
                        loadingText: '读取中...'
                    },
                    // selModel: {
                    //     mode: 'MULTI'
                    // },
                    // selType: 'checkboxmodel',
                    tbar: [{
                        text: '关联用户',
                        // hidden: true,
                        handler: function () {
                            me.bindUser();
                        }
                    }]
                }
            ]
        });

        me.callParent(arguments);
    },

    // 新增
    addOrganization: function () {
        var me = this;
        var treePanel = me.down('treepanel');
        Ext.createWidget('organizationform', {
            title: '新增',
            isUpdate: false,
            wbCallBack: function (values) {
                values.name = values.orgName;
                values.leaf = false;
                values.parentId = me.currentOrgId;
                var node = Ext.create('WB.model.sys.Organization', values);
                node.save({
                    callback: function (r, o, s) {
                        if(s){
                            var store = treePanel.getStore();

                            if(me.currentOrgId !== 0){
                                store.load({node:store.getNodeById(me.currentOrgId)});
                            }else{
                                store.load();
                            }

                            //treePanel.expandNode(store.getNodeById(me.currentOrgId));
                        }
                    }
                });
            }
        }).show();
    },



    // 修改
    updateOrganization: function () {
        var me = this;
        var treePanel = me.down('treepanel');
        var sm = treePanel.getSelectionModel();
        var dataRecord = {};
        if (sm.hasSelection()) {
            var record = sm.getSelection()[0];
            console.log(record);
            dataRecord = {
                orgId: record.get('id'),
                orgName: record.get('orgName'),
                orgCode: record.get('orgCode')
            };

            Ext.createWidget('organizationform', {
                title: '修改',
                isUpdate: true,
                dataRecord: dataRecord,
                wbCallBack: function (values) {
                    console.log(values);
                    var store = treePanel.getStore();
                    store.getNodeById(me.currentOrgId).set('name', values.orgName);
                    store.getNodeById(me.currentOrgId).set('orgName', values.orgName);
                    store.getNodeById(me.currentOrgId).set('orgCode', values.orgCode);
                    store.sync();
                }
            }).show();

        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    },

    // 删除
    delOrganization: function () {
        var me = this;
        var treePanel = me.down('treepanel');
        var sm = treePanel.getSelectionModel();
        if (sm.hasSelection()) {

            if(me.down('grid').getStore().getCount() > 0){
                Ext.Msg.alert('提示', '当前组织已分配用户，不能删除！');
                return;
            }

            var store = treePanel.getStore();
            var node = store.getNodeById(me.currentOrgId);
            if(node.hasChildNodes()){
                Ext.Msg.alert('提示', '当前组织有下属机构，请先删除下属机构！');
                return;
            }
            node.remove();
            store.sync();
           // store.load();
            me.currentOrgId = 0;
        } else {
            Ext.Msg.alert('提示', '请选择一条数据进行操作！');
        }
    },

    // 绑定用户
    bindUser: function () {
        var me = this;
        if(me.currentOrgId !== 0) {
            Ext.createWidget('userselectform', {
                selectType: 2,
                postURL: '/organization/bindUser?orgId=' + me.currentOrgId,
                wbCallback: function () {
                    me.down('grid').getStore().load({
                        params: {
                            orgId: me.currentOrgId
                        }
                    });
                }
            }).show();
        }else{
            Ext.Msg.alert('提示', '请选择一个组织进行操作！');
        }
    }
});