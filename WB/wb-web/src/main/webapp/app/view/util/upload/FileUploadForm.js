/**
 * 文件上传表单
 * Created by Administrator on 2017/4/10.
 */
Ext.define("WB.view.util.FileUploadForm", {
    extend: 'Ext.window.Window',
    alias: 'widget.fileuploadform',

    // 上传URL
    uploadURL: '',

    // 上传进度URL
    progressURL: '',

    initComponent: function () {

        var me = this;
        Ext.apply(me, {
            title: '选择文件',
            layout: 'fit',
            modal: true,
            items: [{
                xtype: 'form',
                width: 500,
                bodyPadding: 10,
                border: false,
                defaults: {
                    anchor: '100%',
                    allowBlank: false,
                    labelAlign: 'right',
                    msgTarget: 'side',
                    labelWidth: 50
                },

                items: [{
                    xtype: 'textfield',
                    fieldLabel: '命  名',
                    name: 'filename'
                }, {
                    xtype: 'filefield',
                    emptyText: '选择文件',
                    fieldLabel: '文  件',
                    name: 'filepath',
                    buttonText: '选择'
                }],

                buttons: [{
                    text: '批量',
                    handler: function () {
                        me.batchFileupload();
                    }
                },{
                    text: '重置',
                    handler: function () {
                        this.up('form').getForm().reset();
                    }
                },{
                    text: '保存',
                    formBind: true,
                    disabled: true,
                    handler: function () {
                        me.uploadFile();
                    }
                }]
            }]
        });

        this.callParent(arguments);
    },

    // 动态新增上传表单项
    batchFileupload: function () {
        var formPanel = this.down('form');
        formPanel.add({
            xtype: 'textfield',
            fieldLabel: '命  名',
            name: 'filename'
        }, {
            xtype: 'filefield',
            emptyText: '选择文件',
            fieldLabel: '文  件',
            name: 'filepath',
            buttonText: '选择'
        });
    },

    // 保存上传文件
    uploadFile: function () {
        var me = this;
        var form = me.down('form').getForm();
        if (form.isValid()) {

            var progress = Ext.MessageBox.show({
                title: '请等待',
                msg: '文件上传中...',
                progressText: '初始化...',
                width:300,
                progress:true,
                closable:false,
                animateTarget: form
            });

            Ext.TaskManager.start({
                run: function(){
                    me.progressUpdate(progress);
                },
                interval: 2000
            });

            form.submit({
                url: contextPath + me.uploadURL,
                success: function (f, action) {
                    progress.updateProgress( 1, '100%', false);
                    Ext.TaskManager.destroy();
                    progress.close();
                    Ext.Msg.alert('成功', action.result.msg, function(){
                        me.close();
                    });
                }
            });

        }
    },

    // 更新上传进度
    progressUpdate: function(p) {
        var me = this;

        Ext.Ajax.request({
            url: contextPath + me.progressURL,
            success: function (response, opts) {
                var json = Ext.decode(response.responseText);
                if(json.data !== null && json.data.contentLength > 0){
                    var v = json.data.bytesRead/json.data.contentLength;
                    var t = Math.round((json.data.bytesRead/json.data.contentLength)*100) + '%';
                    p.updateProgress( v, t, false);
                }
            },
            failure: function (response, opts) {
                Ext.Msg.alert('失败', "进度信息获取失败!");
            }
        });

    }

});