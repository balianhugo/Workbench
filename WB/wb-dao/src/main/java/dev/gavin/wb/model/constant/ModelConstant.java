package dev.gavin.wb.model.constant;

/**
 * 定义表中各种类型状态常量
 * Created by Administrator on 2017/9/22.
 */
public class ModelConstant {

    /**
     * 平台类型数据表类型为字符类型
     */
    public static final byte WB_DATA_TYPE_TYPE_STRING = 0;

    /**
     * 平台类型数据表类型为SQL查询类型
     */
    public static final byte WB_DATA_TYPE_TYPE_SQL = 1;

    /**
     * 平台类型数据表类型参数类型
     */
    public static final byte WB_DATA_TYPE_TYPE_OPTION = 2;

    /**
     * 用户状态数据表类型为正常
     */
    public static final byte WB_USER_STATS_OK = 0;

    /**
     * 用户状态数据表类型为冻结
     */
    public static final byte WB_USER_STATS_FREEZE = 1;

    /**
     * 用户状态数据表类型为删除
     */
    public static final byte WB_USER_STATS_DEL = 2;

}
