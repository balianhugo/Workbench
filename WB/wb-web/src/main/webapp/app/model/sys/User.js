/**
 * Created by Administrator on 2016-11-9.
 */
Ext.define('WB.model.sys.User', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'userId', type: 'int'},
        {name: 'loginName', type: 'string'},
        {name: 'loginPassword', type: 'string'},
        {name: 'nickName', type: 'string'},
        {name: 'gender', type: 'string'},
        {name: 'mobile', type: 'string'},
        {name: 'orgId', type: 'int'},
        {name: 'orgName', type: 'string'},
        {name: 'position', type: 'string'},
        {name: 'email', type: 'string'},
        {name: 'status', type: 'int'}
    ]
});