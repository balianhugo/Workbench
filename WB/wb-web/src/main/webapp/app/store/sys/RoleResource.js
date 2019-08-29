/**
 * Created by Administrator on 2017-3-2.
 */
Ext.define('WB.store.sys.RoleResource', {
    extend: 'Ext.data.Store',
    alias: 'store.roleresource',

    model: 'WB.model.sys.Resource',
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: contextPath + '/role/menuResource',
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