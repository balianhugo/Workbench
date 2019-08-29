Ext.define('WB.store.sys.Log', {
    extend: 'Ext.data.Store',
    alias: 'store.log',

    model: 'WB.model.sys.Log',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: contextPath + '/log/list',
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