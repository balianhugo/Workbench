/**
 * Created by Administrator on 2017/9/23.
 */
Ext.define('WB.store.sys.TypeData', {
    extend: 'Ext.data.Store',
    alias: 'store.typedata',

    model: 'WB.model.sys.TypeData',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: contextPath + '/typeData/list',
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