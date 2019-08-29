package dev.gavin.wb.service;

import dev.gavin.wb.model.WbRoleMenu;

import java.util.List;

/**
 * 菜单管理业务操作接口定义
 * Created by Administrator on 2016-12-12.
 */
public interface WbMenuService {

    /**
     * 查询菜单返回 JSON 字符串信息
     *
     * @return
     */
    String queryMenu(String contextPath);

    /**
     * 返回总数据条数
     *
     * @return
     */
    long countBySearrchParam();

    /**
     * 新增菜单
     *
     * @param record
     * @return
     */
    int insert(WbRoleMenu record);

    /**
     * 更新菜单
     *
     * @param record
     * @return
     */
    int update(WbRoleMenu record);

    /**
     * 根据菜单 ID 删除数据
     *
     * @param ids
     * @return
     */
    int deleteByPrimaryKey(List<Integer> ids);
}
