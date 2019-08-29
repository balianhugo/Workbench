package dev.gavin.wb.service;

import dev.gavin.wb.model.WbDataType;
import dev.gavin.wb.model.ext.WbTypeData;

import java.util.List;

/**
 * 平台数据类型操作接口
 * Created by Administrator on 2017/9/22.
 */
public interface WbTypeDataService {

    /**
     * 检查是否存在相同命名的数据类型
     * @param name 类型数据名
     * @return 存在 true, 不存在，
     */
    boolean checkTypeDataByName(String name);

    /**
     * 根据名字查询获取数据类型值
     * @param name 类型数据名
     * @return key:value 数据集合
     */
    List<WbTypeData> queryTypeDataByName(String name);

    /**
     * 根据名字查询获取数据类型为参数的值
     * @param name 类型数据名
     * @return value
     */
    String queryTypeDataOptionByName(String name);

    /**
     * 新增数据类型
     * @param data 类型数据对象
     * @return
     */
    int insertTypeData(WbDataType data);

    /**
     * 更新数据类型
     * @param data 类型数据对象
     * @return
     */
    int updateTypeData(WbDataType data);

    /**
     * 删除数据类型
     * @param names 类型数据名集合
     * @return
     */
    int deleteTypeData(List<String> names);

    /**
     * 查询当前可用的数据类型
     * @param name 查询参数名字
     * @param start 分页开始
     * @param limit 数据条数
     * @return
     */
    List<WbDataType> queryBySearrchParam(String name, Byte type, Integer start, Integer limit);

    /**
     * 查询当前可用的数据类型总数
     * @param name
     * @return
     */
    long countBySearrchParam(String name, Byte type);

}
