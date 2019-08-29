package dev.gavin.wb.service;

import dev.gavin.wb.model.WbMenuResource;
import dev.gavin.wb.model.WbRoleMenu;
import dev.gavin.wb.model.WbUserInf;
import dev.gavin.wb.vo.WbUserRoleBindVO;

import java.util.List;
import java.util.Map;

/**
 * 对用户表的业务操作接口定义
 * Created by Administrator on 2016-11-17.
 */
public interface WbUserInfService {

    /**
     * 根据用户 ID 查询用户角色的绑定信息
     *
     * @param userId
     * @return
     */
    List<WbUserRoleBindVO> queryUserBindRole(Integer userId);

    /**
     * 根据用户 ID 查询用户是否有角色绑定
     *
     * @param loginName
     * @return
     */
    boolean checkUserBindRole(String loginName);


    /**
     * 根据用户 ID 及角色名查询用户角色是否绑定
     *
     * @param loginName
     * @return
     */
    boolean checkUserIsBindRole(String loginName, String roleName);


    /**
     * 批量更新用户角色信息
     *
     * @param ids
     * @param userId
     * @return
     */
    int updateUserRoleRel(List<Integer> ids, Integer userId);

    /**
     * 检查账号是否已存在：true 存在，false 不存在
     *
     * @param loginName
     * @return
     */
    boolean checkLoginName(String loginName);

    /**
     * 根据用户 ID 删除用户数据
     *
     * @param userIds
     * @return
     */
    int deleteByPrimaryKey(List<Integer> userIds);

    /**
     * 根据用户 ID 查询用户数据
     *
     * @param userId
     * @return
     */
    WbUserInf queryByPrimaryKey(Integer userId);

    /**
     * 根据账号查询用户数据
     *
     * @param loginName
     * @return
     */
    WbUserInf queryByLoginName(String loginName);

    /**
     * 根据 loginName，status 查询参数查询并以分页方式返回用户数据
     *
     * @param loginName
     * @param status
     * @param start
     * @param limit
     * @return
     */
    List<WbUserInf> queryBySearrchParam(String loginName, Byte status, Integer start, Integer limit);

    /**
     * 根据 loginName，status 查询参数查询返回用户记录数
     *
     * @param loginName
     * @param status
     * @return
     */
    long countBySearrchParam(String loginName, Byte status);

    /**
     * 新增用户
     *
     * @param record
     * @return
     */
    int insert(WbUserInf record);

    /**
     * 更新用户
     *
     * @param record
     * @return
     */
    int update(WbUserInf record);

    /**
     * 根据用户 ID 查询用户角色菜单
     *
     * @param userId
     * @return
     */
    List<WbRoleMenu> queryUserRoleMenuByUserId(Integer userId);

    /**
     * 根据用户 ID 查询用户界面访问资源
     *
     * @param userId
     * @return
     */
    Map<String, Map<String, String>> queryUserPageResource(Integer userId);

    /**
     * 根据用户 ID 查询用户所有操作资源权限
     *
     * @param userId
     * @return
     */
    List<WbMenuResource> queryUserMenuResourceByUserId(Integer userId);

    /**
     * 根据用户 ID 更改用户为冻结状态
     *
     * @param ids
     */
    void updateUserFreeze(List<Integer> ids);

}

