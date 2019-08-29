/**
 * 文件上传进度状态显示
 * Created by Administrator on 2017/4/10.
 */
Ext.define('WB.view.util.FileUploadProgress', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.fileuploadprogress',
    requires: ['WB.view.util.FileUploadForm'],

    initComponent: function () {

        var me = this;
        Ext.apply(me, {
            title: '文件上传',
            width: 350,
            tbar: [{
                text: '新增',
                handler: function () {
                    me.addFile();
                }
            }],
            items: []

        });

        this.callParent(arguments);
    },

    // 新增文件所行函数
    addFile: function () {
        Ext.widget('fileuploadform', {
            uploadURL: '/person/fileupload',
            progressURL: '/person/progress'
        }).show();
    }

});