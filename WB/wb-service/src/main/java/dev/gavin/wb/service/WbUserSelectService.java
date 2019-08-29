package dev.gavin.wb.service;

import dev.gavin.wb.model.WbUserInf;

import java.util.List;

/**
 * 平台用户选择接口（按角色、登录名、组织）
 * Created by Administrator on 2017/10/27.
 */
public interface WbUserSelectService {

    /**
     * 按角色
     * @param roleId
     * @return
     */
    List<WbUserInf> queryUserByRole(Integer roleId);

    /**
     * 按组织
     * @param orgId
     * @return
     */
    List<WbUserInf> queryUserByOrg(Integer orgId);

    /**
     * 按登录名
     * @param loginId
     * @return
     */
    List<WbUserInf> queryUserByLoginId(String loginId);

}
