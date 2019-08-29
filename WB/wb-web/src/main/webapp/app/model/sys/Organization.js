/**
 * Created by Administrator on 2017/10/12.
 */
Ext.define('WB.model.sys.Organization', {
    extend: 'Ext.data.Model',
    fields: [
        { name: 'id', type: 'int' },
        { name: 'name', type: 'string' },
        { name: 'orgId', type: 'int' },
        { name: 'orgName', type: 'string' },
        { name: 'orgCode', type: 'string' }
    ],
    proxy: {
        type: 'ajax',
        api: {
            create: contextPath +'/organization/orgAdd',
            read: contextPath +'/organization/orgList',
            update: contextPath +'/organization/orgUpdate',
            destroy: contextPath +'/organization/orgDel'
        }
    }

});