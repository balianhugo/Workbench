package dev.gavin.wb.mybatis3.plugin;

/**
 * 用户扩展不同数据库物理分页接口，实现 dialectSql 方法完成 sql 分页语句的拼接返回
 *
 * Created by Administrator on 2016-11-1.
 */
public interface Dialect {

    /**
     * 用户需要实现的 sql 分页语句拼接方法
     *
     * @param sql 源查询 sql 语句
     * @param offset 定位，从哪一行后开始
     * @param limit 偏移量，返回数据行数
     * @return sql 返回拼接后的语句
     */
    String dialectSql(String sql, int offset, int limit);

}
