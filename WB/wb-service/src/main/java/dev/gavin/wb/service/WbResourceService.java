package dev.gavin.wb.service;


import dev.gavin.wb.model.WbMenuResource;

import java.util.List;

/**
 * 菜单对应资源管理接口定义
 * Created by Administrator on 2017-1-3.
 */
public interface WbResourceService {

    /**
     * 根据菜单 ID 查询资源列表
     *
     * @param menuId
     * @return
     */
    List<WbMenuResource> queryResourceListByMenuId(Integer menuId);

    /**
     * 新增资源
     *
     * @param record
     * @return
     */
    int insert(WbMenuResource record);

    /**
     * 更新资源
     *
     * @param record
     * @return
     */
    int update(WbMenuResource record);

    /**
     * 根据资源 ID 删除数据
     *
     * @param Ids
     * @return
     */
    int deleteByPrimaryKey(List<Integer> Ids);

}
