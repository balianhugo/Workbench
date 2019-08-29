package dev.gavin.wb.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.gavin.wb.controller.BaseController;
import dev.gavin.wb.model.WbRoleMenu;
import dev.gavin.wb.service.WbMenuService;
import dev.gavin.wb.util.AuthorityInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController{

    @Resource
    private WbMenuService wbMenuService;

    @Resource
    private AuthorityInfo authorityInfo;

    @Value("#{config[iconPath]}")
    private String iconPath;

    @RequestMapping("/icons")
    @ResponseBody
    public Map<String, Object> icons(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if(iconPath.startsWith("/img")){
            iconPath = request.getServletContext().getRealPath("/") + iconPath;
        }
        File file = new File(iconPath);
        if (file.exists()) {
            List<Map<String, Object>> list = new ArrayList<>();
            String[] icons = file.list();
            for (int i = 0; i < icons.length; i++) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("icon", "/wb/icons?name=/" + icons[i]);
                list.add(obj);
            }
            map.put("icons", list);
        }
        return map;
    }

    @RequestMapping("/page")
    public String page(Model model) throws JsonProcessingException {
        String authority = authorityInfo.getPageAuthority(getSession(), request.getServletPath());
        model.addAttribute("authority", authority);

        logger.debug("authority: {}", authority);
        return "/wb/sys/menu";
    }

    @RequestMapping("/list")
    public void menuList(HttpServletResponse response) throws IOException {

        String menus = wbMenuService.queryMenu(request.getContextPath());
        logger.debug("menus : {}", menus);

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(menus);
        writer.flush();
        writer.close();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> updateMenu(@ModelAttribute(value = "menu") WbRoleMenu menu) {
        Map<String, Object> map = new HashMap<>();

        int n = wbMenuService.update(menu);
        if(n > 0){
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "更新菜单成功");
        }else{
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "更新菜单失败");
        }
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map<String, Object> addMenu(@ModelAttribute(value = "menu") WbRoleMenu menu) {
        Map<String, Object> map = new HashMap<>();

        int n = wbMenuService.insert(menu);
        if(n > 0){
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "新增菜单成功");
        }else{
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "新增菜单失败");
        }
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteMenu(@RequestParam(value = "ids") List<Integer> ids) {
        Map<String, Object> map = new HashMap<>();

        int n = wbMenuService.deleteByPrimaryKey(ids);
        if(n > 0){
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "删除菜单成功");
        }else{
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "删除菜单失败");
        }
        return map;
    }

}
