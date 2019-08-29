package dev.gavin.wb.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.gavin.wb.controller.BaseController;
import dev.gavin.wb.model.WbDataType;
import dev.gavin.wb.service.WbTypeDataService;
import dev.gavin.wb.util.AuthorityInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据类型管理
 */
@Controller
@RequestMapping("/typeData")
public class TypeDataController extends BaseController {

    @Resource
    private WbTypeDataService wbTypeDataService;

    @Resource
    private AuthorityInfo authorityInfo;

    @RequestMapping("/page")
    public String page(Model model) throws JsonProcessingException {
        String authority = authorityInfo.getPageAuthority(getSession(), request.getServletPath());
        model.addAttribute("authority", authority);

        logger.debug("authority: {}", authority);
        return "/wb/sys/typeData";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> typeDataList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) Byte type,
            @RequestParam(value = "start") Integer start,
            @RequestParam(value = "limit") Integer limit) {
        Map<String, Object> map = new HashMap<>();

        List<WbDataType> list = wbTypeDataService.queryBySearrchParam(name, type, start, limit);
        long total = wbTypeDataService.countBySearrchParam(name, type);

        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);
        map.put(RESULT_TOTAL, total);
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map<String, Object> typeDataAdd(WbDataType wbDataType) {
        Map<String, Object> map = new HashMap<>();

        if(wbTypeDataService.checkTypeDataByName(wbDataType.getName())){
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "新增失败，存在相同命名的数据类型");
            return map;
        }

        wbTypeDataService.insertTypeData(wbDataType);

        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "新增成功");
        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> typeDataUpdate(WbDataType wbDataType) {
        Map<String, Object> map = new HashMap<>();

        wbTypeDataService.updateTypeData(wbDataType);

        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "更新成功");
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> typeDataDelete(@RequestParam(value = "ids") List<String> ids) {
        Map<String, Object> map = new HashMap<>();

        int n = wbTypeDataService.deleteTypeData(ids);
        if(n > 0){
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "删除成功");
        }else{
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "删除失败");
        }
        return map;
    }

}
