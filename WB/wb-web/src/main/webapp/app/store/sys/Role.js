/**
 * Created by Administrator on 2016-11-9.
 */
Ext.define('WB.store.sys.Role', {
    extend: 'Ext.data.Store',
    alias: 'store.role',

    model: 'WB.model.sys.Role',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: contextPath + '/role/list',
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