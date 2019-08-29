/**
 * Created by Administrator on 2016-12-13.
 */
Ext.define('WB.model.sys.Role', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'roleId', type: 'int'},
        {name: 'roleName', type: 'string'},
        {name: 'description', type: 'string'}
    ]
});