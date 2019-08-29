/**
 * 通用用户选择
 * Created by carrot on 2017/10/22.
 */
Ext.define("WB.view.util.user.UserSelectFormWin", {
    extend: 'Ext.window.Window',
    alias: 'widget.userselectform',
    requires: [
        'WB.view.util.user.UserList',
        'WB.view.util.user.RoleUserList',
        'WB.view.util.user.OrgUserList'
    ],

    // 用户选择模式： 1-单选、2-多选
    selectType: 1,

    // 提交请求处理 URL
    postURL: '',

    initComponent: function () {

        var me = this;

        me.selectUserGrid = Ext.create('Ext.grid.Panel',{
            xtype: 'grid',
            width: 900,
            height: 100,
            columns: [
                {text: '账号', dataIndex: 'loginName', flex: 1},
                {text: '姓名', dataIndex: 'nickName', flex: 1},
                {text: '手机号', dataIndex: 'mobile', flex: 1},
                {
                    text : '操作',
                    renderer : function(value) {
                        return "<span style='color:blue;cursor:pointer'>删除</span>";
                    },
                    width : 100
                }
            ],
            listeners: {
                cellclick: {
                    fn: function(o, td, cellIndex, record, tr, rowIndex, e, eOpts ){
                        if(cellIndex === 3){
                            me.selectUserGrid.getStore().remove(record);
                        }
                    }
                }
            },
            store: {
                model: 'WB.model.sys.User',
                autoLoad: false
            }
        });

        Ext.apply(me, {
            title: '用户选择',
            layout: 'fit',
            border: false,
            bodyPadding: 10,
            modal: true,
            items: [
                {
                    xtype: 'container',
                    items: [
                        {
                            xtype: 'fieldset',
                            title: '查找用户',
                            items: {
                                xtype: 'tabpanel',
                                plain: true,
                                width: 900,
                                height: 300,
                                activeTab: 0,
                                items: [
                                    {
                                        xtype:'utiluserlist',
                                        title: '按用户',
                                        wbCallback: function (record) {
                                            me.selectUser(record);
                                        }

                                    }, {
                                        xtype:'utilroleuserlist',
                                        title: '按角色',
                                        wbCallback: function (record) {
                                            me.selectUser(record);
                                        }
                                    }, {
                                        xtype:'utilorguserlist',
                                        title: '按组织',
                                        wbCallback: function (record) {
                                            me.selectUser(record);
                                        }
                                    }
                                ]
                            }
                        }, {
                            xtype: 'fieldset',
                            title: '已选用户',
                            items: me.selectUserGrid
                        }
                    ]
                }
            ],
            buttons: [{
                text: '重置',
                handler: function () {
                    me.selectUserGrid.getStore().removeAll();
                }
            },{
                text: '保存',
                handler: function () {
                    if(me.selectUserGrid.getStore().count() > 0){
                        var ids = [];
                        me.selectUserGrid.getStore().each(function (record) {
                            ids.push(record.get('userId'));
                        });
                        me.bindUser(ids);
                    } else {
                        Ext.Msg.alert('提示', "请至少选择一个用户");
                    }
                }
            }]

        });

        this.callParent(arguments);
    },


    // 选择用户
    selectUser: function (record) {
        var me = this;
        if(me.selectType === 1) {
            if(me.selectUserGrid.getStore().count() === 0){
                me.selectUserGrid.getStore().loadRawData(record.getData(), true);
            }
        }
        if(me.selectType === 2) {
            var flag = true;
            me.selectUserGrid.getStore().each(function (item) {
                if(item.get('loginName') === record.get('loginName')){
                    flag = false;
                }
            });

            if(flag){
                me.selectUserGrid.getStore().loadRawData(record.getData(), true);
            }
        }
    },

    // 用户选择提交
    bindUser: function (ids) {
        var me = this;
        var mask = Ext.MessageBox.wait("系统处理中...");
        Ext.Ajax.request({
            url: contextPath + me.postURL,
            params: {
                ids: ids
            },
            success: function (response, opts) {
                mask.close();
                var data = Ext.decode(response.responseText);
                Ext.Msg.alert('成功', data.msg, function () {
                    me.wbCallback();
                    me.close();
                });
            },
            failure: function (response, opts) {
                mask.close();
                if (response.status === 200) {
                    var data = Ext.decode(response.responseText);
                    Ext.Msg.alert('失败', data.msg);
                } else {
                    Ext.Msg.alert('失败', response.status);
                }
            }
        });

    },

    // 回调函数
    wbCallback: function () {

    }

});