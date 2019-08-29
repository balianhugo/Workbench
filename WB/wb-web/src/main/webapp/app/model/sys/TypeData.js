/**
 * Created by Administrator on 2017/9/23.
 */
Ext.define('WB.model.sys.TypeData', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'name', type: 'string'},
        {name: 'type', type: 'int'},
        {name: 'value', type: 'string'},
        {name: 'description', type: 'string'}
    ]
});