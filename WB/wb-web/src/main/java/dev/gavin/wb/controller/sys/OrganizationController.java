package dev.gavin.wb.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.gavin.wb.controller.BaseController;
import dev.gavin.wb.model.WbOrganizationInf;
import dev.gavin.wb.model.WbUserInf;
import dev.gavin.wb.service.WbOrganizationService;
import dev.gavin.wb.util.AuthorityInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织架构管理
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

    @Resource
    private AuthorityInfo authorityInfo;

    @Resource
    private WbOrganizationService wbOrganizationService;

    @RequestMapping("/page")
    public String page(Model model) throws JsonProcessingException {
        String authority = authorityInfo.getPageAuthority(getSession(), request.getServletPath());
        model.addAttribute("authority", authority);

        logger.debug("authority: {}", authority);
        return "/wb/sys/organization";
    }

    @RequestMapping("/orgList")
    public void orgList(@RequestParam(value = "node") Integer node, HttpServletResponse response) throws IOException {

        String orgs = wbOrganizationService.queryOrganization(node);
        logger.debug("orgs : {}", orgs);

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(orgs);
        writer.flush();
        writer.close();
    }

    @RequestMapping("/orgAdd")
    @ResponseBody
    public Map<String, Object> orgAdd(@RequestBody WbOrganizationInf org) {
        Map<String, Object> map = new HashMap<>();
        int key = wbOrganizationService.insert(org);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "组织新增成功");
        map.put(RESULT_DATA, key);
        return map;
    }

    @RequestMapping("/orgUpdate")
    @ResponseBody
    public Map<String, Object> orgUpdate(@RequestBody WbOrganizationInf org) {
        Map<String, Object> map = new HashMap<>();
        wbOrganizationService.update(org);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "组织更新成功");
        return map;
    }

    @RequestMapping("/orgDel")
    @ResponseBody
    public Map<String, Object> orgDel(@RequestBody WbOrganizationInf org) {
        Map<String, Object> map = new HashMap<>();
        wbOrganizationService.deleteByPrimaryKey(org.getOrgId());
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "组织删除成功");
        return map;
    }


    @RequestMapping("/orgUserList")
    @ResponseBody
    public Map<String, Object> orgUserList(@RequestParam(value = "orgId") Integer orgId) {
        Map<String, Object> map = new HashMap<>();
        List<WbUserInf> list = wbOrganizationService.queryOrgUserList(orgId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);
        return map;
    }

    @RequestMapping("/bindUser")
    @ResponseBody
    public Map<String, Object> bindUser(@RequestParam(value = "ids") List<Integer> ids,
                                        @RequestParam(value = "orgId") Integer orgId) {
        Map<String, Object> map = new HashMap<>();

        wbOrganizationService.insertBindUser(ids, orgId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "组织用户关联成功");

        return map;
    }

}
