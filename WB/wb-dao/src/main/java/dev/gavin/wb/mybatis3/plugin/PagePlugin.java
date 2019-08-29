package dev.gavin.wb.mybatis3.plugin;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * <p>
 * 通用分页插件，主要是拦截原内存分页参数，拼接修改 sql 查询语句实现数据库物理分页
 * 核心代码是用反射方式获取 RowBounds 中 limit、offset 值并拼接到 sql 查询语句上
 * 注意 RowBounds 取值后要重置为系统默认值
 * </p>
 * Created by Administrator on 2016-10-26.
 */

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}))
public class PagePlugin implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(PagePlugin.class);
    private String dialectClassName;
    private Dialect dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        /**
         * 查看源码调试发现 invocation.getTarget() 获取拦截的对象是一个路由代理处理对象 RoutingStatementHandler
         * 所以取内变量 delegate 才是对应的 StatementHandler 真正实现类
         * */
        StatementHandler target = (StatementHandler) FieldUtils.readField(invocation.getTarget(), "delegate", true);
        RowBounds rowBounds = (RowBounds) FieldUtils.readField(target, "rowBounds", true);

        /**
         * 这里我们只针对用户设置的 rowBounds 进行分页处理
         */
        if (rowBounds == RowBounds.DEFAULT) {
            logger.debug("rowBounds is default ");
            return invocation.proceed();
        }

        /**
         * 实例化方言对象，调用 sql 分页拼接实现方法
         */
        dialect = (Dialect) Class.forName(dialectClassName).newInstance();
        String sql = target.getBoundSql().getSql();
        logger.debug("sql append before: {}", sql);
        sql = dialect.dialectSql(target.getBoundSql().getSql(), rowBounds.getOffset(), rowBounds.getLimit());
        logger.debug("sql append after: {}", sql);

        /**
         * 这里注意要将 rowBounds 中的 offset limit 重置为系统默认值，不然还是会进行内存分页的
         * 然后将拼接后的执行 sql 语句重设回去，因为接口没有有提供方法，只能用反射设置回去
         *
         * 知识点，主要是更改引用对象中的属性值，而不能新 new 一个对象出来覆盖。
         * 因为很可能该对象被其他的类所引用，这样就会造成系统中不同类引用的不是同一个对象
         */
        FieldUtils.writeField(rowBounds, "offset", RowBounds.NO_ROW_OFFSET, true);
        FieldUtils.writeField(rowBounds, "limit", RowBounds.NO_ROW_LIMIT, true);
        FieldUtils.writeField(target.getBoundSql(), "sql", sql, true);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

        logger.debug("properties: {}", properties);

        /**
         * 获取参数配置的方言实现类名
         */
        dialectClassName = String.valueOf(properties.get("dialect"));

    }

}
