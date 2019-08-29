/**
 * Created by Administrator on 2016-12-13.
 */
Ext.define('WB.model.sys.Menu', {
    extend: 'Ext.data.TreeModel',
    fields: [
        {name: 'menuId', type: 'int'},
        {name: 'name', type: 'string'},
        {name: 'iconcls', type: 'string'},
        {name: 'url', type: 'string'},
        {name: 'parentMenuId', type: 'int'},
        {name: 'sequence', type: 'string'},
        {name: 'level', type: 'int'}
    ]
});