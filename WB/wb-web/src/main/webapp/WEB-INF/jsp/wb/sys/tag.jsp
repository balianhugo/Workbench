
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    Ext.require('WB.view.sys.Tag');
    Ext.onReady(function () {
        Ext.getCmp('nav-main-center').getActiveTab().add({
            xtype: 'tagpage',
            deptadmin: ${deptadmin}
            <%--authority: ${authority}--%>
        });
    });
</script>
