/**
 * Created by Administrator on 2017/10/12.
 */
Ext.define('WB.store.sys.Organization', {
    extend: 'Ext.data.TreeStore',
    alias: 'store.organization',

    model: 'WB.model.sys.Organization',
    root: {
        name: '机构组织',
        id: 0,
        expanded: true
    }
});