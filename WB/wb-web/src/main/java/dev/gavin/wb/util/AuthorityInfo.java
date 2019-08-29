package dev.gavin.wb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gavin.wb.model.WbMenuResource;
import dev.gavin.wb.model.WbRoleMenu;
import dev.gavin.wb.service.WbUserInfService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据用户 ID 获取用户菜单及资源信息
 * Created by Administrator on 2016-11-23.
 */
@Component
public class AuthorityInfo {

    @Resource
    private WbUserInfService wbUserInfService;


    /**
     * 返回页面权限展现控制 JSON 对象
     * @param session
     * @param uri
     * @return
     * @throws JsonProcessingException
     */
    public String getPageAuthority(HttpSession session, String uri) throws JsonProcessingException {
        Map<String, Map<String, String>> pageURI = (Map<String, Map<String,String>>) session.getAttribute(WebConstants.WB_USER_PAGE_URL);
        List<String> uris = (List<String>) session.getAttribute(WebConstants.WB_USER_AUTHORITY_URL);
        Map<String, String> map = pageURI.get(uri);
        Map<String, Boolean> authority = new HashMap<>();

        for(String key : map.keySet()){
            if(uris.contains(key)){
                authority.put(map.get(key), false);
            }else{
                authority.put(map.get(key), true);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(authority);
    }


    /**
     * 根据用户 ID 查询用户可访问的 URL 集合
     *
     * @param userId
     * @return
     */
    public List<String> getUserAccessURL(Integer userId) {
        List<String> urls = new ArrayList<>();
        List<WbMenuResource> list = wbUserInfService.queryUserMenuResourceByUserId(userId);
        for(WbMenuResource resource : list){
            urls.add(resource.getHref());
        }
        return urls;
    }

    /**
     * 根据用户 ID 查询用户可访问的界面功能使用权限集合
     *
     * @param userId
     * @return
     */
    public Map<String, Map<String, String>> getUserPageResource(Integer userId) {
        return wbUserInfService.queryUserPageResource(userId);
    }

    /**
     * 根据用户 ID 查询菜单数据并处理返回 ExtJS 相应的 JSON 格式数据
     *
     * @param userId
     * @return
     */
    public String getUserMenu(Integer userId, String contextPath) {
        List<WbRoleMenu> list = wbUserInfService.queryUserRoleMenuByUserId(userId);

        List<WbRoleMenu> menu1 = new ArrayList<>();
        List<WbRoleMenu> menu2 = new ArrayList<>();
        List<WbRoleMenu> menu3 = new ArrayList<>();

        for (WbRoleMenu menu : list) {
            if (menu.getLevel() == 1) {
                menu1.add(menu);
            }
            if (menu.getLevel() == 2) {
                menu2.add(menu);
            }
            if (menu.getLevel() == 3) {
                menu3.add(menu);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"menus\":[");
        for (int i1 = 0, len1 = menu1.size(); i1 < len1; i1++) {
            sb.append("{\"text\":\"").append(menu1.get(i1).getName()).append("\",\"icon\":\"").append(contextPath)
                    .append(menu1.get(i1).getIconcls()).append("\",\"id\":").append(menu1.get(i1).getMenuId()).append(",\"data\":[");

            for (int i2 = 0, len2 = menu2.size(); i2 < len2; i2++) {
                if (menu1.get(i1).getMenuId() == menu2.get(i2).getParentMenuId()) {
                    sb.append("{\"title\":\"").append(menu2.get(i2).getName()).append("\",\"icon\":\"").append(contextPath)
                            .append(menu2.get(i2).getIconcls()).append("\",\"id\":").append(menu2.get(i2).getMenuId()).append(",\"root\":{\"expanded\":true,\"children\":[");

                    for (int i3 = 0, len3 = menu3.size(); i3 < len3; i3++) {
                        if (menu2.get(i2).getMenuId() == menu3.get(i3).getParentMenuId()) {
                            sb.append("{\"leaf\":true,\"text\":\"").append(menu3.get(i3).getName()).append("\",\"icon\":\"").append(contextPath)
                                    .append(menu3.get(i3).getIconcls()).append("\",\"id\":").append(menu3.get(i3).getMenuId()).append(",\"url\":\"").append(menu3.get(i3).getUrl()).append("\"}");
                            if (i3 < len3 - 1) sb.append(",");
                        }
                    }

                    sb.append("]}}");
                    if (i2 < len2 - 1) sb.append(",");
                }
            }

            sb.append("]}");
            if (i1 < len1 - 1) sb.append(",");
        }
        sb.append("]}");

        return sb.toString();
    }

}
