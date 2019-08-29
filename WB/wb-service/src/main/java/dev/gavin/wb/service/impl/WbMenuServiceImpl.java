package dev.gavin.wb.service.impl;

import dev.gavin.wb.dao.WbRoleMenuMapper;
import dev.gavin.wb.model.WbRoleMenu;
import dev.gavin.wb.model.WbRoleMenuExample;
import dev.gavin.wb.service.WbMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理操作接口实现
 * Created by Administrator on 2016-12-14.
 */
@Service
public class WbMenuServiceImpl implements WbMenuService {

    @Resource
    private WbRoleMenuMapper wbRoleMenuMapper;

    @Override
    public String queryMenu(String contextPath) {

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
            sb.append("{\"expanded\":true,\"text\":\"").append(one.get(i1).getName())
                   // .append("\",\"icon\":\"").append(contextPath).append(one.get(i1).getIconcls())
                    .append("\",\"iconcls\":\"").append(one.get(i1).getIconcls())
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
                            .append("\",\"iconcls\":\"").append(two.get(i2).getIconcls())
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
                                    .append("\",\"iconcls\":\"").append(three.get(i3).getIconcls())
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
    public long countBySearrchParam() {
        WbRoleMenuExample ex = new WbRoleMenuExample();
        return wbRoleMenuMapper.countByExample(ex);
    }

    @Override
    public int insert(WbRoleMenu record) {
        return wbRoleMenuMapper.insert(record);
    }

    @Override
    public int update(WbRoleMenu record) {
        return wbRoleMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(List<Integer> roleIds) {
        WbRoleMenuExample ex = new WbRoleMenuExample();
        ex.createCriteria().andMenuIdIn(roleIds);
        return wbRoleMenuMapper.deleteByExample(ex);
    }

}
