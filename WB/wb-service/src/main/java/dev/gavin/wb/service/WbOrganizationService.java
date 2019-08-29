package dev.gavin.wb.service;

import dev.gavin.wb.model.WbOrganizationInf;
import dev.gavin.wb.model.WbUserInf;

import java.util.List;

/**
 * 组织架构管理业务操作接口定义
 * Created by Administrator on 2017/10/12.
 */
public interface WbOrganizationService {

    /**
     * 查询返回 JSON 字符串信息
     *
     * @return
     */
    String queryOrganization(Integer parentId);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insert(WbOrganizationInf record);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int update(WbOrganizationInf record);

    /**
     * 根据菜单 ID 删除数据
     *
     * @param orgId
     * @return
     */
    int deleteByPrimaryKey(Integer orgId);

    /**
     * 按组织机构ID 查询绑定用户
     * @param orgId
     * @return
     */
    List<WbUserInf> queryOrgUserList(Integer orgId);

    /**
     * 按组织机构ID 绑定相应用户
     * @param ids
     * @param orgId
     */
    void insertBindUser(List<Integer> ids, Integer orgId);

}
