package dev.gavin.wb.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.gavin.wb.controller.BaseController;
import dev.gavin.wb.model.WbMenuResource;
import dev.gavin.wb.service.WbResourceService;
import dev.gavin.wb.util.AuthorityInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源管理
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Resource
    private WbResourceService wbResourceService;

    @Resource
    private AuthorityInfo authorityInfo;

    @RequestMapping("/page")
    public String page(Model model) throws JsonProcessingException {
        String authority = authorityInfo.getPageAuthority(getSession(), request.getServletPath());
        model.addAttribute("authority", authority);

        logger.debug("authority: {}", authority);
        return "/wb/sys/resource";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> resourceList(@RequestParam(value = "menuId") Integer menuId) {
        Map<String, Object> map = new HashMap<>();
        List<WbMenuResource> list = wbResourceService.queryResourceListByMenuId(menuId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> updateResource(@ModelAttribute(value = "resource") WbMenuResource resource) {
        Map<String, Object> map = new HashMap<>();
        int n = wbResourceService.update(resource);
        if (n > 0) {
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "更新资源成功");
        } else {
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "更新资源失败");
        }
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map<String, Object> addResource(@ModelAttribute(value = "resource") WbMenuResource resource) {
        Map<String, Object> map = new HashMap<>();
        int n = wbResourceService.insert(resource);
        if (n > 0) {
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "新增资源成功");
        } else {
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "新增资源失败");
        }
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteResource(@RequestParam(value = "ids") List<Integer> ids) {
        Map<String, Object> map = new HashMap<>();
        int n = wbResourceService.deleteByPrimaryKey(ids);
        if (n > 0) {
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "删除资源成功");
        } else {
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "删除资源失败");
        }
        return map;
    }
}
