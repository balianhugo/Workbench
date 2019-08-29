Ext.define('WB.model.sys.Log', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'logId', type: 'int'},
        {name: 'loginName', type: 'string'},
        {name: 'mobile', type: 'string'},
        {name: 'doDate', type: 'int'},
        {name: 'ip', type: 'string'},
        {name: 'uri', type: 'string'},
        {name: 'doType', type: 'string'},
        {name: 'userTime', type: 'int'},
        {name: 'doResult', type: 'string'}
    ]
});

