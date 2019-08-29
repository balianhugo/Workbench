/**
 * Created by Administrator on 2016-01-03.
 */
Ext.define('WB.model.sys.Resource', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'resourceId', type: 'int'},
        {name: 'menuId', type: 'int'},
        {name: 'name', type: 'string'},
        {name: 'href', type: 'string'},
        {name: 'description', type: 'string'}
    ]
});