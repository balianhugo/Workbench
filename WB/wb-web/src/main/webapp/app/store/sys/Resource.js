/**
 * Created by Administrator on 2016-11-9.
 */
Ext.define('WB.store.sys.Resource', {
    extend: 'Ext.data.Store',
    alias: 'store.resource',

    model: 'WB.model.sys.Resource',
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: contextPath + '/resource/list',
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