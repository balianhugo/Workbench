package dev.gavin.wb.service.impl;

import dev.gavin.wb.dao.WbOrganizationInfMapper;
import dev.gavin.wb.dao.WbUserInfMapper;
import dev.gavin.wb.dao.WbUserOrgRelMapper;
import dev.gavin.wb.model.*;
import dev.gavin.wb.service.WbOrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构管理业务操作接口实现
 * Created by Administrator on 2017/10/12.
 */
@Service
public class WbOrganizationServiceImpl implements WbOrganizationService {

    @Resource
    private WbOrganizationInfMapper wbOrganizationInfMapper;

    @Resource
    private WbUserInfMapper wbUserInfMapper;

    @Resource
    private WbUserOrgRelMapper wbUserOrgRelMapper;

    @Override
    public String queryOrganization(Integer parentId) {

        WbOrganizationInfExample ex = new WbOrganizationInfExample();
        ex.createCriteria().andParentIdEqualTo(parentId);

        List<WbOrganizationInf> list = wbOrganizationInfMapper.selectByExample(ex);

        StringBuilder sb = new StringBuilder();
        sb.append("{\"success\":true,\"children\":[");
        for(int x=0,len=list.size(); x < len; x++) {
            sb.append("{\"leaf\": false, \"id\":").append(list.get(x).getOrgId())
                    .append(",\"name\":\"").append(list.get(x).getOrgName())
                    .append("\",\"orgId\":").append(list.get(x).getOrgId())
                    .append(",\"orgName\":\"").append(list.get(x).getOrgName())
                    .append("\",\"orgCode\":\"").append(list.get(x).getOrgCode())
                    .append("\",\"pid\":").append(list.get(x).getParentId())
                    .append("}");
            if (x < len - 1) sb.append(",");
        }
        sb.append("]}");

        return sb.toString();
    }

    @Override
    public int insert(WbOrganizationInf record) {
        wbOrganizationInfMapper.insertSelective(record);
        return record.getOrgId();
    }

    @Override
    public int update(WbOrganizationInf record) {
        return wbOrganizationInfMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer orgId) {
        return wbOrganizationInfMapper.deleteByPrimaryKey(orgId);
    }

    @Override
    public List<WbUserInf> queryOrgUserList(Integer orgId) {

        WbUserOrgRelExample ex = new WbUserOrgRelExample();
        ex.createCriteria().andOrgIdEqualTo(orgId);
        List<WbUserOrgRelKey> keys = wbUserOrgRelMapper.selectByExample(ex);
        List<Integer> ids = new ArrayList<>();
        List<WbUserInf> list = null;
        if(keys.size() > 0) {
            for(WbUserOrgRelKey key: keys) {
                ids.add(key.getUserId());
            }

            WbUserInfExample uex = new WbUserInfExample();
            uex.createCriteria().andUserIdIn(ids);
            list = wbUserInfMapper.selectByExample(uex);
        }

        return list;
    }

    @Override
    public void insertBindUser(List<Integer> ids, Integer orgId) {

        WbUserOrgRelExample ex = new WbUserOrgRelExample();
        ex.createCriteria().andUserIdIn(ids);
        wbUserOrgRelMapper.deleteByExample(ex);

        WbOrganizationInf org = wbOrganizationInfMapper.selectByPrimaryKey(orgId);

        WbUserOrgRelKey key = new WbUserOrgRelKey();
        key.setOrgId(orgId);
        for (Integer id: ids) {
            key.setUserId(id);
            wbUserOrgRelMapper.insertSelective(key);
            WbUserInf user = wbUserInfMapper.selectByPrimaryKey(id);
            user.setOrgId(orgId);
            user.setOrgName(org.getOrgName());
            wbUserInfMapper.updateByPrimaryKey(user);
        }
    }

}
