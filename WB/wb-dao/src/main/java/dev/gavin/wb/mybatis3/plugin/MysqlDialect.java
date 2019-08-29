package dev.gavin.wb.mybatis3.plugin;

/**
 * Created by Administrator on 2016-11-1.
 */
public class MysqlDialect implements Dialect {

    @Override
    public String dialectSql(String sql, int offset, int limit) {

        StringBuffer buffer = new StringBuffer();
        buffer.append(sql).append(" LIMIT ").append(offset).append(",").append(limit);
        sql = buffer.toString();

        return sql;
    }
}
