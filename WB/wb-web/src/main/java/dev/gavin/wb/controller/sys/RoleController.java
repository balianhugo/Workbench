package dev.gavin.wb.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.gavin.wb.controller.BaseController;
import dev.gavin.wb.model.WbUserRole;
import dev.gavin.wb.service.WbRoleService;
import dev.gavin.wb.util.AuthorityInfo;
import dev.gavin.wb.vo.WbMenuResourceVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Resource
    private WbRoleService wbRoleService;

    @Resource
    private AuthorityInfo authorityInfo;

    @RequestMapping("/page")
    public String page(Model model) throws JsonProcessingException {
        String authority = authorityInfo.getPageAuthority(getSession(), request.getServletPath());
        model.addAttribute("authority", authority);

        logger.debug("authority: {}", authority);
        return "/wb/sys/role";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> roleList(@RequestParam(value = "start") Integer start,
                                        @RequestParam(value = "limit") Integer limit) {
        Map<String, Object> map = new HashMap<>();

        List<WbUserRole> list = wbRoleService.queryBySearchParam(start, limit);
        long total = wbRoleService.countBySearchParam();

        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);
        map.put(RESULT_TOTAL, total);
        return map;
    }

    @RequestMapping("/menuList")
    public void menuList(@RequestParam(value = "roleId") Integer roleId,
                         HttpServletResponse response) throws IOException {

        String menus = wbRoleService.queryRoleMenu(roleId, request.getContextPath());
        logger.debug("roleMenus : {}", menus);

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(menus);
        writer.flush();
        writer.close();
    }

    @RequestMapping("/menuResource")
    @ResponseBody
    public Map<String, Object> menuResource(
            @RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "menuId") Integer menuId) {
        Map<String, Object> map = new HashMap<>();
        List<WbMenuResourceVO> list  = wbRoleService.queryMenuResource(roleId, menuId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);
        return map;
    }

    @RequestMapping("/resourceList")
    public void resourceList(@RequestParam(value = "roleId") Integer roleId,
                         HttpServletResponse response) throws IOException {

        String menus = wbRoleService.queryRoleResource(roleId, request.getContextPath());
        logger.debug("roleMenus : {}", menus);

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(menus);
        writer.flush();
        writer.close();
    }

    @RequestMapping("/updateResource")
    @ResponseBody
    public Map<String, Object> updateResource(
            @RequestParam(value = "ids", required = false) List<Integer> ids,
            @RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "menuId") Integer menuId) {
        Map<String, Object> map = new HashMap<>();

        wbRoleService.updateRoleResource(ids, menuId, roleId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "角色资源更新成功");

        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> updateRole(@ModelAttribute(value = "role") WbUserRole role) {
        Map<String, Object> map = new HashMap<>();

        int n = wbRoleService.update(role);
        if (n > 0) {
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "更新角色成功");
        } else {
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "更新角色失败");
        }
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map<String, Object> addRole(@ModelAttribute(value = "user") WbUserRole role) {
        Map<String, Object> map = new HashMap<>();

        if (wbRoleService.checkRoleName(role.getRoleName())) {
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "角色已存在");
            return map;
        }

        int n = wbRoleService.insert(role);
        if (n > 0) {
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "新增角色成功");
        } else {
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "新增角色失败");
        }
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteRole(@RequestParam(value = "roleId") Integer roleId) {
        Map<String, Object> map = new HashMap<>();


        if (wbRoleService.checkRoleBindUserByPrimaryKey(roleId)) {
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "当前角色已绑定了用户，不能删除");
            return map;
        }

        wbRoleService.deleteByPrimaryKey(roleId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "删除角色成功");
        return map;
    }

    @RequestMapping("/bindMenu")
    @ResponseBody
    public Map<String, Object> bindMenu(@RequestParam(value = "ids", required = false) List<Integer> ids,
                                        @RequestParam(value = "roleId") Integer roleId) {
        Map<String, Object> map = new HashMap<>();

        wbRoleService.updateRoleMenu(ids, roleId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "菜单关联更新成功");

        return map;
    }
}
