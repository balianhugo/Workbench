/**
 * Created by Administrator on 2017/10/26.
 */
Ext.define('WB.store.sys.OrgUser', {
    extend: 'Ext.data.Store',
    alias: 'store.orguser',

    model: 'WB.model.sys.User',
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: contextPath + '/organization/orgUserList',
        actionMethods : {
            read : 'POST'
        },
        reader: {
            type: 'json',
            root: 'data',
            totalProperty: 'total',
            successProperty: 'success'
        }
    }
});