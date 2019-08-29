/**
 * Created by Administrator on 2016-11-9.
 */
Ext.define('WB.store.sys.Menu', {
    extend: 'Ext.data.TreeStore',
    alias: 'store.menu',

    model: 'WB.model.sys.Menu',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: contextPath + '/menu/list',
        actionMethods : {
            read : 'POST'
        },
        reader: {
            type: 'json'
        }
    },
    folderSort: true
});