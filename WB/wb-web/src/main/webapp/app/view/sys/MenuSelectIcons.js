Ext.define('WB.view.sys.MenuSelectIcons', {
    extend: 'Ext.window.Window',
    alias: 'widget.menuicons',

    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            title: '图标选择',
            border: false,
            constrain: true,
            layout: 'fit',
            modal: true,
            width: 800,
            height: 500,
            items: [
                {
                    xtype: 'dataview',
                    autoScroll: true,
                    id: 'select-icon',
                    loadingText: '读取中...',
                    store: {
                        fields: ['icon'],
                        proxy: {
                            type: 'ajax',
                            url: contextPath + '/menu/icons',
                            reader: {
                                type: 'json',
                                root: 'icons'
                            }
                        },
                        autoLoad: true
                    },
                    tpl: new Ext.XTemplate(
                        '<tpl for=".">',
                        '<div class="icon-wrap">',
                        '<img src="'+ contextPath +'{icon}" />',
                        '</div>',
                        '</tpl>'
                    ),
                    itemSelector: 'div.icon-wrap',
                    listeners: {
                        itemclick: {
                            fn: function (view, record, item, index, e, eOpts) {
                                Ext.Msg.show({
                                    title:'提示',
                                    msg: '您选择的图标是：<img src="'+ contextPath + record.get('icon') +'" />',
                                    buttons: Ext.Msg.YESNO,
                                    icon: Ext.Msg.QUESTION,
                                    fn: function(btn) {
                                        if (btn === 'yes') {
                                            me.wbCallBack(record.get('icon'));
                                            me.close();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            ]
        });

        me.callParent(arguments);
    },

    wbCallBack: function (iconpath) {

    }

});