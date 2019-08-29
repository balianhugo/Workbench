package dev.gavin.wb.service;

import dev.gavin.wb.model.WbUserRole;
import dev.gavin.wb.vo.WbMenuResourceVO;

import java.util.List;

/**
 * 用户角色管理业务操作接口定义
 * Created by Administrator on 2016-12-12.
 */
public interface WbRoleService {

    /**
     * 查询角色菜单信息
     *
     * @param roleId
     * @return
     */
    String queryRoleMenu(Integer roleId, String contextPath);

    /**
     * 查询菜单资源信息
     *
     * @param roleId
     * @param menuId
     * @return
     */
    List<WbMenuResourceVO> queryMenuResource(Integer roleId, Integer menuId);

    /**
     * 查询角色资源信息
     *
     * @param roleId
     * @return
     */
    String queryRoleResource(Integer roleId, String contextPath);

    /**
     * 更新角色菜单绑定信息
     *
     * @param resourceIds
     * @param menuId
     * @param roleId
     * @return
     */
    void updateRoleResource(List<Integer> resourceIds, Integer menuId, Integer roleId);

    /**
     * 更新角色菜单绑定信息
     *
     * @param menuIds
     * @param roleId
     * @return
     */
    int updateRoleMenu(List<Integer> menuIds, Integer roleId);

    /**
     * 分页查询返回信息
     *
     * @param start
     * @param limit
     * @return
     */
    List<WbUserRole> queryBySearchParam(Integer start, Integer limit);

    /**
     * 返回总数据条数
     *
     * @return
     */
    long countBySearchParam();

    /**
     * 检查角色是否已存在：true 存在，false 不存在
     *
     * @param roleName
     * @return
     */
    boolean checkRoleName(String roleName);

    /**
     * 新增角色
     *
     * @param record
     * @return
     */
    int insert(WbUserRole record);

    /**
     * 更新角色
     *
     * @param record
     * @return
     */
    int update(WbUserRole record);

    /**
     * 根据角色 ID 删除角色数据
     *
     * @param roleIds
     * @return
     */
    void deleteByPrimaryKey(Integer roleIds);

    /**
     * 判断角色 ID 是否绑定用户
     *
     * @param roleId
     * @return
     */
    boolean checkRoleBindUserByPrimaryKey(Integer roleId);
}
