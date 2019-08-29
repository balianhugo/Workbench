/**
 * 角色菜单关联表单
 * Created by Administrator on 2016-12-19.
 */
Ext.define('WB.view.sys.RoleMenuForm', {
    extend: 'Ext.window.Window',
    alias: 'widget.rolemenuform',

    roleId: '', // 扩展属性用于加载数据时的查询参数

    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            layout: 'fit',
            width: 300,
            height: 500,
            modal: true,
            items:[
                {
                    xtype: 'treepanel',
                    border: false,
                    useArrows: true,
                    rootVisible: false,
                    listeners: {
                        checkchange: {
                            fn: function(node, checked, eOpts) {
                                me.updateNodeChecked(node, checked);
                            }
                        }
                    },
                    buttons: [{
                        text: '重置',
                        handler: function () {
                            me.loadBindData();
                        }
                    }, {
                        text: '保存',
                        handler: function () {
                            me.bindSave();
                        }
                    }]
                }
            ]
        });
        me.on('beforerender', me.loadBindData, me);

        me.callParent(arguments);
    },


    // 级联更新节点
    updateNodeChecked: function(node, checked){
        var me = this;
        // 这里处理节点选中的邏輯，选中后父节点相应选中，子节点全选，取消选中子节点全取消
        if(checked){
            var parentNode = node.parentNode;
            while(parentNode !== null && !parentNode.isRoot()){
                parentNode.set('checked', true);
                parentNode = parentNode.parentNode;
            }
            me.updateChildChecked(node, true);
        }else {
            me.updateChildChecked(node, false);
        }
    },

    // 级联更新子节点
    updateChildChecked: function(node, checked){
        var me = this;
        if(checked){
            if (node.hasChildNodes()) {
                node.eachChild(function(child) {
                    child.set('checked', true);
                    me.updateChildChecked(child, true);
                });
            }
        }else{
            if (node.hasChildNodes()) {
                node.eachChild(function(child) {
                    child.set('checked', false);
                    me.updateChildChecked(child, false);
                });
            }
        }
    },

    // 加载关联菜单数据
    loadBindData: function () {
        var me = this;
        Ext.Ajax.request({
            url: contextPath + '/role/menuList',
            params: {
                roleId: me.roleId
            },
            success: function (response, opts) {
                me.down('treepanel').setRootNode(Ext.decode(response.responseText));
            },
            failure: function (response, opts) {
                Ext.Msg.alert('失败', response.status);
            }
        });
    },

    // 保存关联信息
    bindSave: function () {
        var me = this;
        var models = me.down('treepanel').getChecked();
        var ids = [];
        Ext.Array.each(models, function (model, index, itSelf) {
            ids.push(model.raw.menuId);
        });
        var mask = Ext.MessageBox.wait("菜单绑定中...");
        Ext.Ajax.request({
            url: contextPath + '/role/bindMenu',
            params: {
                ids: ids,
                roleId: me.roleId
            },
            success: function (response, opts) {
                mask.close();
                var data = Ext.decode(response.responseText);
                Ext.Msg.alert('成功', data.msg, function () {
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
    }
});