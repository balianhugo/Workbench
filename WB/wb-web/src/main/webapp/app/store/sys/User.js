/**
 * Created by Administrator on 2016-12-12.
 */
Ext.define('WB.store.sys.User', {
    extend: 'Ext.data.Store',
    alias: 'store.user',

    model: 'WB.model.sys.User',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: contextPath + '/user/list',
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