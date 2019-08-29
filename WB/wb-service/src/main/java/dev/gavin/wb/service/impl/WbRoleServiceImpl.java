package dev.gavin.wb.service.impl;

import dev.gavin.wb.dao.*;
import dev.gavin.wb.model.*;
import dev.gavin.wb.service.WbRoleService;
import dev.gavin.wb.vo.WbMenuResourceVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理业务操作接口实现
 * Created by Administrator on 2016-12-12.
 */
@Service
public class WbRoleServiceImpl implements WbRoleService {

    @Resource
    private WbUserRoleMapper wbUserRoleMapper;

    @Resource
    private WbRoleMenuRelMapper wbRoleMenuRelMapper;

    @Resource
    private WbRoleMenuMapper wbRoleMenuMapper;

    @Resource
    private WbMenuResourceMapper wbMenuResourceMapper;

    @Resource
    private WbRoleResourceRelMapper wbRoleResourceRelMapper;

    @Resource
    private WbUserRoleRelMapper wbUserRoleRelMapper;

    @Override
    public int updateRoleMenu(List<Integer> menuIds, Integer roleId) {
        WbRoleMenuRelExample re = new WbRoleMenuRelExample();
        re.createCriteria().andRoleIdEqualTo(roleId);
        int n = wbRoleMenuRelMapper.deleteByExample(re);

        if (null != menuIds && menuIds.size() > 0) {
            n = 0;
            for (Integer menuId : menuIds) {
                WbRoleMenuRelKey key = new WbRoleMenuRelKey();
                key.setRoleId(roleId);
                key.setMenuId(menuId);
                n = wbRoleMenuRelMapper.insert(key) > 0 ? n + 1 : n;
            }
        }
        return n;
    }

    @Override
    public void updateRoleResource(List<Integer> resourceIds, Integer menuId, Integer roleId) {

        WbMenuResourceExample ex = new WbMenuResourceExample();
        ex.createCriteria().andMenuIdEqualTo(menuId);
        List<WbMenuResource> list = wbMenuResourceMapper.selectByExample(ex);

        List<Integer> ids = new ArrayList<>();
        for (WbMenuResource resource: list){
            ids.add(resource.getResourceId());
        }
        WbRoleResourceRelExample re = new WbRoleResourceRelExample();
        re.createCriteria().andRoleIdEqualTo(roleId).andResourceIdIn(ids);
        wbRoleResourceRelMapper.deleteByExample(re);

        if(null != resourceIds && resourceIds.size() > 0){
            for (Integer resourceId: resourceIds){
                WbRoleResourceRelKey key = new WbRoleResourceRelKey();
                key.setResourceId(resourceId);
                key.setRoleId(roleId);
                wbRoleResourceRelMapper.insert(key);
            }
        }
    }

    @Override
    public List<WbMenuResourceVO> queryMenuResource(Integer roleId, Integer menuId) {

        List<WbMenuResourceVO> volist = new ArrayList<>();

        WbMenuResourceExample ex = new WbMenuResourceExample();
        ex.createCriteria().andMenuIdEqualTo(menuId);
        List<WbMenuResource> list = wbMenuResourceMapper.selectByExample(ex);

        WbRoleResourceRelExample re = new WbRoleResourceRelExample();
        re.createCriteria().andRoleIdEqualTo(roleId);
        List<WbRoleResourceRelKey> relList = wbRoleResourceRelMapper.selectByExample(re);

        if(list.size() > 0){
            for (WbMenuResource resource: list){
                WbMenuResourceVO vo = new WbMenuResourceVO();
                vo.setMenuId(resource.getMenuId());
                vo.setDescription(resource.getDescription());
                vo.setHref(resource.getHref());
                vo.setName(resource.getName());
                vo.setResourceId(resource.getResourceId());

                WbRoleResourceRelKey temp = null;
                for (WbRoleResourceRelKey key: relList){
                    if(key.getResourceId() == vo.getResourceId()){
                        vo.setBind(true);
                        temp = key;
                        break;
                    }
                }
                relList.remove(temp);
                volist.add(vo);
            }
            return volist;
        }else {
            return null;
        }
    }

    @Override
    public String queryRoleMenu(Integer roleId, String contextPath) {

        WbRoleMenuRelExample re = new WbRoleMenuRelExample();
        re.createCriteria().andRoleIdEqualTo(roleId);
        List<WbRoleMenuRelKey> rolemenu = wbRoleMenuRelMapper.selectByExample(re);
        List<Integer> menuIds = new ArrayList<>();
        for (WbRoleMenuRelKey key : rolemenu) {
            menuIds.add(key.getMenuId());
        }

        WbRoleMenuExample ex = new WbRoleMenuExample();
        ex.setOrderByClause("sequence ASC");
        List<WbRoleMenu> list = wbRoleMenuMapper.selectByExample(ex);

        List<WbRoleMenu> one = new ArrayList<>();
        List<WbRoleMenu> two = new ArrayList<>();
        List<WbRoleMenu> three = new ArrayList<>();

        for (WbRoleMenu menu : list) {
            if (menu.getLevel() == 1) {
                one.add(menu);
            }
            if (menu.getLevel() == 2) {
                two.add(menu);
            }
            if (menu.getLevel() == 3) {
                three.add(menu);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"expanded\":true,\"children\":[");
        for (int i1 = 0, len1 = one.size(); i1 < len1; i1++) {
            sb.append("{\"expanded\":true,\"text\":\"").append(one.get(i1).getName());
            // .append("\",\"icon\":\"").append(contextPath).append(one.get(i1).getIconcls())
            if (menuIds.contains(one.get(i1).getMenuId())) {
                sb.append("\",\"checked\":true");
            } else {
                sb.append("\",\"checked\":false");
            }
            sb.append(",\"iconcls\":\"").append(contextPath).append(one.get(i1).getIconcls())
                    .append("\",\"menuId\":").append(one.get(i1).getMenuId())
                    .append(",\"name\":\"").append(one.get(i1).getName())
                    .append("\",\"url\":\"").append(one.get(i1).getUrl())
                    .append("\",\"parentMenuId\":").append(one.get(i1).getParentMenuId())
                    .append(",\"sequence\":\"").append(one.get(i1).getSequence())
                    .append("\",\"level\":").append(one.get(i1).getLevel()).append(",\"children\":[");

            for (int i2 = 0, len2 = two.size(); i2 < len2; i2++) {
                if (one.get(i1).getMenuId() == two.get(i2).getParentMenuId()) {
                    sb.append("{\"expanded\":true,\"text\":\"").append(two.get(i2).getName())
                            .append("\",\"icon\":\"").append(contextPath).append(two.get(i2).getIconcls());
                    if (menuIds.contains(two.get(i2).getMenuId())) {
                        sb.append("\",\"checked\":true");
                    } else {
                        sb.append("\",\"checked\":false");
                    }
                    sb.append(",\"iconcls\":\"").append(contextPath).append(two.get(i2).getIconcls())
                            .append("\",\"menuId\":").append(two.get(i2).getMenuId())
                            .append(",\"name\":\"").append(two.get(i2).getName())
                            .append("\",\"url\":\"").append(two.get(i2).getUrl())
                            .append("\",\"parentMenuId\":").append(two.get(i2).getParentMenuId())
                            .append(",\"sequence\":\"").append(two.get(i2).getSequence())
                            .append("\",\"level\":").append(two.get(i2).getLevel()).append(",\"children\":[");

                    for (int i3 = 0, len3 = three.size(); i3 < len3; i3++) {
                        if (two.get(i2).getMenuId() == three.get(i3).getParentMenuId()) {
                            sb.append("{\"leaf\":true,\"text\":\"").append(three.get(i3).getName())
                                    .append("\",\"icon\":\"").append(contextPath).append(three.get(i3).getIconcls());
                            if (menuIds.contains(three.get(i3).getMenuId())) {
                                sb.append("\",\"checked\":true");
                            } else {
                                sb.append("\",\"checked\":false");
                            }
                            sb.append(",\"iconcls\":\"").append(contextPath).append(three.get(i3).getIconcls())
                                    .append("\",\"menuId\":").append(three.get(i3).getMenuId())
                                    .append(",\"name\":\"").append(three.get(i3).getName())
                                    .append("\",\"url\":\"").append(three.get(i3).getUrl())
                                    .append("\",\"parentMenuId\":").append(three.get(i3).getParentMenuId())
                                    .append(",\"sequence\":\"").append(three.get(i3).getSequence())
                                    .append("\",\"level\":").append(three.get(i3).getLevel()).append("}");

                            if (i3 < len3 - 1) sb.append(",");
                        }
                    }

                    sb.append("]}");
                    if (i2 < len2 - 1) sb.append(",");
                }
            }

            sb.append("]}");
            if (i1 < len1 - 1) sb.append(",");
        }
        sb.append("]}");

        return sb.toString();
    }

    @Override
    public String queryRoleResource(Integer roleId, String contextPath) {

        WbRoleMenuRelExample re = new WbRoleMenuRelExample();
        re.createCriteria().andRoleIdEqualTo(roleId);
        List<WbRoleMenuRelKey> rolemenu = wbRoleMenuRelMapper.selectByExample(re);
        List<Integer> menuIds = new ArrayList<>();
        for (WbRoleMenuRelKey key : rolemenu) {
            menuIds.add(key.getMenuId());
        }

        if(menuIds.size() == 0){
            return "";
        }

        WbRoleMenuExample ex = new WbRoleMenuExample();
        ex.setOrderByClause("sequence ASC");
        ex.createCriteria().andMenuIdIn(menuIds);
        List<WbRoleMenu> list = wbRoleMenuMapper.selectByExample(ex);

        List<WbRoleMenu> one = new ArrayList<>();
        List<WbRoleMenu> two = new ArrayList<>();
        List<WbRoleMenu> three = new ArrayList<>();

        for (WbRoleMenu menu : list) {
            if (menu.getLevel() == 1) {
                one.add(menu);
            }
            if (menu.getLevel() == 2) {
                two.add(menu);
            }
            if (menu.getLevel() == 3) {
                three.add(menu);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"expanded\":true,\"children\":[");
        for (int i1 = 0, len1 = one.size(); i1 < len1; i1++) {
            sb.append("{\"expanded\":true,\"text\":\"").append(one.get(i1).getName())
                    // .append("\",\"icon\":\"").append(contextPath).append(one.get(i1).getIconcls())
                    .append("\",\"iconcls\":\"").append(contextPath).append(one.get(i1).getIconcls())
                    .append("\",\"menuId\":").append(one.get(i1).getMenuId())
                    .append(",\"name\":\"").append(one.get(i1).getName())
                    .append("\",\"url\":\"").append(one.get(i1).getUrl())
                    .append("\",\"parentMenuId\":").append(one.get(i1).getParentMenuId())
                    .append(",\"sequence\":\"").append(one.get(i1).getSequence())
                    .append("\",\"level\":").append(one.get(i1).getLevel()).append(",\"children\":[");

            for (int i2 = 0, len2 = two.size(); i2 < len2; i2++) {
                if (one.get(i1).getMenuId() == two.get(i2).getParentMenuId()) {
                    sb.append("{\"expanded\":true,\"text\":\"").append(two.get(i2).getName())
                            .append("\",\"icon\":\"").append(contextPath).append(two.get(i2).getIconcls())
                            .append("\",\"iconcls\":\"").append(contextPath).append(two.get(i2).getIconcls())
                            .append("\",\"menuId\":").append(two.get(i2).getMenuId())
                            .append(",\"name\":\"").append(two.get(i2).getName())
                            .append("\",\"url\":\"").append(two.get(i2).getUrl())
                            .append("\",\"parentMenuId\":").append(two.get(i2).getParentMenuId())
                            .append(",\"sequence\":\"").append(two.get(i2).getSequence())
                            .append("\",\"level\":").append(two.get(i2).getLevel()).append(",\"children\":[");

                    for (int i3 = 0, len3 = three.size(); i3 < len3; i3++) {
                        if (two.get(i2).getMenuId() == three.get(i3).getParentMenuId()) {
                            sb.append("{\"leaf\":true,\"text\":\"").append(three.get(i3).getName())
                                    .append("\",\"icon\":\"").append(contextPath).append(three.get(i3).getIconcls())
                                    .append("\",\"iconcls\":\"").append(contextPath).append(three.get(i3).getIconcls())
                                    .append("\",\"menuId\":").append(three.get(i3).getMenuId())
                                    .append(",\"name\":\"").append(three.get(i3).getName())
                                    .append("\",\"url\":\"").append(three.get(i3).getUrl())
                                    .append("\",\"parentMenuId\":").append(three.get(i3).getParentMenuId())
                                    .append(",\"sequence\":\"").append(three.get(i3).getSequence())
                                    .append("\",\"level\":").append(three.get(i3).getLevel()).append("}");

                            if (i3 < len3 - 1) sb.append(",");
                        }
                    }

                    sb.append("]}");
                    if (i2 < len2 - 1) sb.append(",");
                }
            }

            sb.append("]}");
            if (i1 < len1 - 1) sb.append(",");
        }
        sb.append("]}");

        return sb.toString();
    }

    @Override
    public List<WbUserRole> queryBySearchParam(Integer start, Integer limit) {
        WbUserRoleExample ex = new WbUserRoleExample();
        return wbUserRoleMapper.selectByExampleWithRowbounds(ex, new RowBounds(start, limit));
    }

    @Override
    public long countBySearchParam() {
        WbUserRoleExample ex = new WbUserRoleExample();
        return wbUserRoleMapper.countByExample(ex);
    }

    @Override
    public boolean checkRoleName(String roleName) {
        WbUserRoleExample ex = new WbUserRoleExample();
        ex.createCriteria().andRoleNameEqualTo(roleName);
        List<WbUserRole> list = wbUserRoleMapper.selectByExampleWithRowbounds(ex, new RowBounds(0, 1));
        if (list.size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public int insert(WbUserRole record) {
        return wbUserRoleMapper.insert(record);
    }

    @Override
    public int update(WbUserRole record) {
        return wbUserRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void deleteByPrimaryKey(Integer roleId) {

        WbRoleMenuRelExample ex1 = new WbRoleMenuRelExample();
        ex1.createCriteria().andRoleIdEqualTo(roleId);
        wbRoleMenuRelMapper.deleteByExample(ex1);

        WbRoleResourceRelExample ex2 = new WbRoleResourceRelExample();
        ex2.createCriteria().andRoleIdEqualTo(roleId);
        wbRoleResourceRelMapper.deleteByExample(ex2);

        WbUserRoleExample ex = new WbUserRoleExample();
        ex.createCriteria().andRoleIdEqualTo(roleId);
        wbUserRoleMapper.deleteByExample(ex);
    }

    @Override
    public boolean checkRoleBindUserByPrimaryKey(Integer roleId) {

        WbUserRoleRelExample ex = new WbUserRoleRelExample();
        ex.createCriteria().andRoleIdEqualTo(roleId);

        return wbUserRoleRelMapper.countByExample(ex) > 0;
    }
}
