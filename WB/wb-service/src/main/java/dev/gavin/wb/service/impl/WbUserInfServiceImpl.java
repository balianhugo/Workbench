package dev.gavin.wb.service.impl;

import dev.gavin.wb.dao.*;
import dev.gavin.wb.model.*;
import dev.gavin.wb.model.constant.ModelConstant;
import dev.gavin.wb.service.WbUserInfService;
import dev.gavin.wb.vo.WbUserRoleBindVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对用户表的业务操作接口实现类
 * Created by Administrator on 2016-11-17.
 */
@Service
public class WbUserInfServiceImpl implements WbUserInfService {

    @Resource
    private WbUserInfMapper wbUserInfMapper;

    @Resource
    private WbUserRoleRelMapper wbUserRoleRelMapper;

    @Resource
    private WbUserRoleMapper wbUserRoleMapper;

    @Resource
    private WbMenuResourceMapper wbMenuResourceMapper;

    @Resource
    private WbRoleMenuRelMapper wbRoleMenuRelMapper;

    @Resource
    private WbRoleMenuMapper wbRoleMenuMapper;

    @Resource
    private WbUserOrgRelMapper wbUserOrgRelMapper;

    @Resource
    private WbRoleResourceRelMapper wbRoleResourceRelMapper;

    @Override
    public int updateUserRoleRel(List<Integer> ids, Integer userId) {

        WbUserRoleRelExample re = new WbUserRoleRelExample();
        re.createCriteria().andUserIdEqualTo(userId);
        int n = wbUserRoleRelMapper.deleteByExample(re);
        if (null != ids && ids.size() > 0) {
            n = 0;
            for (Integer roleId : ids) {
                WbUserRoleRelKey key = new WbUserRoleRelKey();
                key.setRoleId(roleId);
                key.setUserId(userId);
                n = wbUserRoleRelMapper.insert(key) > 0 ? (n + 1) : n;
            }
        }
        return n;
    }

    @Override
    public boolean checkUserIsBindRole(String loginName, String roleName) {
        WbUserInfExample ex = new WbUserInfExample();
        WbUserInfExample.Criteria cri = ex.createCriteria();
        cri.andLoginNameEqualTo(loginName);
        List<WbUserInf> list = wbUserInfMapper.selectByExampleWithRowbounds(ex, new RowBounds(0, 1));
        if(list.isEmpty()) {
            return false;
        }

        WbUserRoleExample rex = new WbUserRoleExample();
        rex.createCriteria().andRoleNameEqualTo(roleName);
        List<WbUserRole> rlist = wbUserRoleMapper.selectByExampleWithRowbounds(rex, new RowBounds(0, 1));
        if(rlist.isEmpty()) {
            return false;
        }
        WbUserInf user = list.get(0);
        WbUserRole role = rlist.get(0);
        WbUserRoleRelExample re = new WbUserRoleRelExample();
        re.createCriteria().andUserIdEqualTo(user.getUserId()).andRoleIdEqualTo(role.getRoleId());
        List<WbUserRoleRelKey> keys = wbUserRoleRelMapper.selectByExample(re);
        return !keys.isEmpty();
    }

    @Override
    public boolean checkUserBindRole(String loginName) {

        WbUserInfExample ex = new WbUserInfExample();
        WbUserInfExample.Criteria cri = ex.createCriteria();
        cri.andLoginNameEqualTo(loginName);
        List<WbUserInf> list = wbUserInfMapper.selectByExampleWithRowbounds(ex, new RowBounds(0, 1));
        if(list.isEmpty()) {
            return false;
        }

        WbUserInf user = list.get(0);
        WbUserRoleRelExample re = new WbUserRoleRelExample();
        re.createCriteria().andUserIdEqualTo(user.getUserId());
        List<WbUserRoleRelKey> keys = wbUserRoleRelMapper.selectByExample(re);
        return !keys.isEmpty();
    }

    @Override
    public List<WbUserRoleBindVO> queryUserBindRole(Integer userId) {
        WbUserRoleExample ex = new WbUserRoleExample();
        List<WbUserRole> roles = wbUserRoleMapper.selectByExample(ex);

        WbUserRoleRelExample re = new WbUserRoleRelExample();
        re.createCriteria().andUserIdEqualTo(userId);
        List<WbUserRoleRelKey> keys = wbUserRoleRelMapper.selectByExample(re);

        List<WbUserRoleBindVO> list = new ArrayList<>();
        for (WbUserRole role : roles) {
            WbUserRoleBindVO vo = new WbUserRoleBindVO();
            vo.setDescription(role.getDescription());
            vo.setRoleId(role.getRoleId());
            vo.setRoleName(role.getRoleName());
            for (WbUserRoleRelKey key : keys) {
                if (key.getRoleId() == vo.getRoleId()) {
                    vo.setBind(true);
                }
            }
            list.add(vo);
        }

        return list;
    }

    @Override
    public boolean checkLoginName(String loginName) {
        WbUserInfExample ex = new WbUserInfExample();
        WbUserInfExample.Criteria cri = ex.createCriteria();
        cri.andLoginNameEqualTo(loginName);
        List<WbUserInf> list = wbUserInfMapper.selectByExampleWithRowbounds(ex, new RowBounds(0, 1));
        return list.size() == 1;
    }

    @Override
    public long countBySearrchParam(String loginName, Byte status) {
        WbUserInfExample ex = new WbUserInfExample();
        WbUserInfExample.Criteria cri = ex.createCriteria();
        if (null != loginName && !loginName.trim().equals("")) {
            cri.andLoginNameEqualTo(loginName);
        }
        if (null != status) {
            cri.andStatusEqualTo(status);
        }
        return wbUserInfMapper.countByExample(ex);
    }

    @Override
    public List<WbUserInf> queryBySearrchParam(String loginName, Byte status, Integer start, Integer limit) {
        WbUserInfExample ex = new WbUserInfExample();
        ex.setOrderByClause("user_id desc");
        WbUserInfExample.Criteria cri = ex.createCriteria();
        if (null != loginName && !loginName.trim().equals("")) {
            cri.andLoginNameEqualTo(loginName);
        }
        if (null != status) {
            cri.andStatusEqualTo(status);
        }
        return wbUserInfMapper.selectByExampleWithRowbounds(ex, new RowBounds(start, limit));
    }

    @Override
    public WbUserInf queryByPrimaryKey(Integer userId) {
        return wbUserInfMapper.selectByPrimaryKey(userId);
    }

    @Override
    public WbUserInf queryByLoginName(String loginName) {
        WbUserInfExample ex = new WbUserInfExample();
        ex.createCriteria().andLoginNameEqualTo(loginName);
        List<WbUserInf> list = wbUserInfMapper.selectByExample(ex);

        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int deleteByPrimaryKey(List<Integer> userIds) {
        WbUserInfExample ex = new WbUserInfExample();
        ex.createCriteria().andUserIdIn(userIds);
        return wbUserInfMapper.deleteByExample(ex);
    }

    @Override
    public int insert(WbUserInf record) {

        record.setStatus((byte) 0);
        int n = wbUserInfMapper.insert(record);

        WbUserOrgRelExample ex = new WbUserOrgRelExample();
        ex.createCriteria().andUserIdEqualTo(record.getUserId());
        wbUserOrgRelMapper.deleteByExample(ex);
        WbUserOrgRelKey key = new WbUserOrgRelKey();
        key.setUserId(record.getUserId());
        key.setOrgId(record.getOrgId());
        wbUserOrgRelMapper.insertSelective(key);

        return n;
    }

    @Override
    public int update(WbUserInf record) {

        WbUserOrgRelExample ex = new WbUserOrgRelExample();
        ex.createCriteria().andUserIdEqualTo(record.getUserId());
        wbUserOrgRelMapper.deleteByExample(ex);
        WbUserOrgRelKey key = new WbUserOrgRelKey();
        key.setUserId(record.getUserId());
        key.setOrgId(record.getOrgId());
        wbUserOrgRelMapper.insertSelective(key);

        return wbUserInfMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<WbRoleMenu> queryUserRoleMenuByUserId(Integer userId) {

        // 1.查出用户所有角色
        WbUserRoleRelExample ex = new WbUserRoleRelExample();
        ex.createCriteria().andUserIdEqualTo(userId);
        List<WbUserRoleRelKey> urr = wbUserRoleRelMapper.selectByExample(ex);
        List<Integer> tempList = new ArrayList<>();
        for (WbUserRoleRelKey key : urr) {
            tempList.add(key.getRoleId());
        }

        // 2.根据角色 ID 集合获取所有角色菜单并过滤掉重复菜单
        WbRoleMenuRelExample example = new WbRoleMenuRelExample();
        example.createCriteria().andRoleIdIn(tempList);
        List<WbRoleMenuRelKey> rmr = wbRoleMenuRelMapper.selectByExample(example);
        tempList.clear();
        for (WbRoleMenuRelKey key : rmr) {
            if (!tempList.contains(key.getMenuId())) tempList.add(key.getMenuId());
        }

        // 3.根据菜单 ID 集合查出所有菜单返回
        WbRoleMenuExample menuEx = new WbRoleMenuExample();
        menuEx.setOrderByClause("sequence ASC");
        menuEx.createCriteria().andMenuIdIn(tempList);
        return wbRoleMenuMapper.selectByExample(menuEx);
    }

    @Override
    public  Map<String, Map<String, String>> queryUserPageResource(Integer userId) {

        // 1.查出用户所有角色
        WbUserRoleRelExample ex = new WbUserRoleRelExample();
        ex.createCriteria().andUserIdEqualTo(userId);
        List<WbUserRoleRelKey> urr = wbUserRoleRelMapper.selectByExample(ex);
        List<Integer> tempList = new ArrayList<>();
        for (WbUserRoleRelKey key : urr) {
            tempList.add(key.getRoleId());
        }

        // 2.根据角色 ID 集合获取所有角色菜单并过滤掉重复菜单
        WbRoleMenuRelExample example = new WbRoleMenuRelExample();
        example.createCriteria().andRoleIdIn(tempList);
        List<WbRoleMenuRelKey> rmr = wbRoleMenuRelMapper.selectByExample(example);
        tempList.clear();
        for (WbRoleMenuRelKey key : rmr) {
            if (!tempList.contains(key.getMenuId())) tempList.add(key.getMenuId());
        }

        // 3.根据菜单 ID 集合查出所有菜单返回
        WbMenuResourceExample resourceEx = new WbMenuResourceExample();
        resourceEx.createCriteria().andMenuIdIn(tempList);
        List<WbMenuResource> resourceList = wbMenuResourceMapper.selectByExample(resourceEx);
        Map<Integer, Map<String, String>> map = new HashMap<>();
        for(WbMenuResource resource : resourceList){
            if(map.containsKey(resource.getMenuId())){
                map.get(resource.getMenuId()).put(resource.getHref(), resource.getName());
            }else {
                Map<String, String> urls = new HashMap<>();
                urls.put(resource.getHref(), resource.getName());
                map.put(resource.getMenuId(),urls);
            }
        }

        Map<String, Map<String, String>> pageResource = new HashMap<>();
        for(Integer menuId : map.keySet()){
            Map<String, String> resource = map.get(menuId);
            for(String url : resource.keySet()){
                if(url.endsWith("page") || url.endsWith("Page")){
                    pageResource.put(url, resource);
                    break;
                }
            }
        }

        return pageResource;
    }

    @Override
    public List<WbMenuResource> queryUserMenuResourceByUserId(Integer userId) {

        // 1.查出用户所有角色
        WbUserRoleRelExample ex = new WbUserRoleRelExample();
        ex.createCriteria().andUserIdEqualTo(userId);
        List<WbUserRoleRelKey> urr = wbUserRoleRelMapper.selectByExample(ex);
        List<Integer> roleList = new ArrayList<>();
        List<Integer> resourceList = new ArrayList<>();
        for (WbUserRoleRelKey key : urr) {
            roleList.add(key.getRoleId());
        }

        // 2.根据角色 ID 查询出关联的资源 ID集合
        WbRoleResourceRelExample rrr = new WbRoleResourceRelExample();
        rrr.createCriteria().andRoleIdIn(roleList);
        List<WbRoleResourceRelKey> rrrk = wbRoleResourceRelMapper.selectByExample(rrr);
        for (WbRoleResourceRelKey key : rrrk) {
            if (!resourceList.contains(key.getResourceId())) resourceList.add(key.getResourceId());
        }

        // 3.根据菜单 ID 集合查出所有菜单资源
        WbMenuResourceExample resourceEx = new WbMenuResourceExample();
        resourceEx.createCriteria().andResourceIdIn(resourceList);
        return wbMenuResourceMapper.selectByExample(resourceEx);
    }

    @Override
    public void updateUserFreeze(List<Integer> ids) {
        if(ids != null && !ids.isEmpty()){
            WbUserInfExample ex = new WbUserInfExample();
            ex.createCriteria().andUserIdIn(ids);
            List<WbUserInf> users = wbUserInfMapper.selectByExample(ex);
            for(WbUserInf inf: users) {
                inf.setStatus(ModelConstant.WB_USER_STATS_FREEZE);
                wbUserInfMapper.updateByPrimaryKey(inf);
            }
        }
    }
}
