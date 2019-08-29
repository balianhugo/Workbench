package dev.gavin.wb.service.impl;

import dev.gavin.wb.dao.WbUserInfMapper;
import dev.gavin.wb.dao.WbUserRoleRelMapper;
import dev.gavin.wb.model.*;
import dev.gavin.wb.service.WbOrganizationService;
import dev.gavin.wb.service.WbUserSelectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台用户选择接口实现
 * Created by Administrator on 2017/10/27.
 */
@Service
public class WbUserSelectServiceImpl implements WbUserSelectService {

    @Resource
    private WbUserRoleRelMapper wbUserRoleRelMapper;

    @Resource
    private WbUserInfMapper wbUserInfMapper;

    @Resource
    private WbOrganizationService wbOrganizationService;

    @Override
    public List<WbUserInf> queryUserByRole(Integer roleId) {

        WbUserRoleRelExample ex = new WbUserRoleRelExample();
        ex.createCriteria().andRoleIdEqualTo(roleId);
        List<WbUserRoleRelKey> list = wbUserRoleRelMapper.selectByExample(ex);
        List<Integer> keys = new ArrayList<>();
        if(list.size() > 0){
            for(WbUserRoleRelKey key: list){
                keys.add(key.getUserId());
            }

            WbUserInfExample uex = new WbUserInfExample();
            uex.createCriteria().andUserIdIn(keys);
            return wbUserInfMapper.selectByExample(uex);
        }

        return null;
    }

    @Override
    public List<WbUserInf> queryUserByOrg(Integer orgId) {

        return wbOrganizationService.queryOrgUserList(orgId);
    }

    @Override
    public List<WbUserInf> queryUserByLoginId(String loginId) {

        WbUserInfExample ex = new WbUserInfExample();
        ex.createCriteria().andLoginNameLike("%" + loginId + "%");

        return wbUserInfMapper.selectByExample(ex);
    }
}
