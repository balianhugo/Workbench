/**
 * WB 主页面
 * Created by Administrator on 2016-11-9.
 */
Ext.define('WB.view.Viewport', {
    extend: 'Ext.container.Viewport',
    requires: [
        'WB.view.main.MainLeft',
        'WB.view.main.MainTop',
        'WB.view.main.MainCenter'
    ],

    initComponent: function () {

        Ext.apply(this, {
            layout: {
                type: 'border'
            },
            items: [{
                xtype: 'maintop',
                region: 'north'
            }, {
                xtype: 'mainleft',
                region: 'west',
                split: true,
                collapsible: true
            }, {
                xtype: 'maincenter',
                region: 'center'
            }]
        });

        this.callParent(arguments);
    }
});