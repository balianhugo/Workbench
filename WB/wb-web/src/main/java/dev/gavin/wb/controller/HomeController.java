package dev.gavin.wb.controller;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.gavin.wb.dao.WbOrganizationInfMapper;
import dev.gavin.wb.model.*;
import dev.gavin.wb.model.constant.ModelConstant;
import dev.gavin.wb.model.ext.WbTypeData;
import dev.gavin.wb.service.*;
import dev.gavin.wb.util.WebConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台主页面
 *
 * Created by Administrator on 2016-10-18.
 */

@Controller
@RequestMapping("/wb")
public class HomeController extends BaseController {

    @Resource
    private WbTypeDataService wbTypeDataService;

    @Resource
    private WbUserSelectService wbUserSelectService;

    @Resource
    private WbOrganizationService wbOrganizationService;

    @Resource
    private WbRoleService wbRoleService;

    @RequestMapping(value = {"/home"})
    public String home(Model model) {
        return "/wb/home";
    }

    @RequestMapping(value = {"/main", "/"})
    public String main(Model model) {

        logger.debug("当前 session user：{}", getSession().getAttribute(WebConstants.WB_USER_INFO));
        WbUserInf user = (WbUserInf)getSession().getAttribute(WebConstants.WB_USER_INFO_OBJ);
        logger.debug("当前 session user：{}", user.getLoginName());
        logger.debug("当前 session menu：{}", getSession().getAttribute(WebConstants.WB_USER_MENU));

        model.addAttribute("user", getSession().getAttribute(WebConstants.WB_USER_INFO));
        model.addAttribute("menus", getSession().getAttribute(WebConstants.WB_USER_MENU));

        return "/wb/main";
    }

    @Value("#{config[iconPath]}")
    private String iconPath;

    @RequestMapping("/icons")
    public void menusIcon(@RequestParam(value = "name") String name, HttpServletResponse response) throws IOException {
        if(iconPath.startsWith("/img")){
            String icon = request.getContextPath() + iconPath + name;
            response.sendRedirect(icon);
        } else {
            File file = new File(iconPath + File.separator + name);
            FileInputStream is = new FileInputStream(file);
            byte[] imgByte = new byte[(int)file.length()];
            is.read(imgByte);
            is.close();

            response.setContentType("image/*");
            OutputStream os = response.getOutputStream();
            os.write(imgByte);
            os.flush();
            os.close();
        }
    }

    @RequestMapping("/menus")
    public void menus(HttpServletResponse response) throws IOException {
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(getSession().getAttribute("test").toString());
        writer.flush();
        writer.close();
    }

    @Resource
    private WbOrganizationInfMapper wbOrganizationInfMapper;

    @RequestMapping("/typeData")
    @ResponseBody
    public Map<String, Object> typeData(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "type") Byte type){

        Map<String, Object> map = new HashMap<>();
        if(type == ModelConstant.WB_DATA_TYPE_TYPE_SQL || type == ModelConstant.WB_DATA_TYPE_TYPE_STRING){
            List<WbTypeData> list = wbTypeDataService.queryTypeDataByName(name);
            map.put(RESULT_DATA, list);
        }

        if(type == ModelConstant.WB_DATA_TYPE_TYPE_OPTION){
            String value = wbTypeDataService.queryTypeDataOptionByName(name);
            map.put(RESULT_DATA, value);
        }

        map.put(RESULT_SUCCESS, true);
        return map;

    }


    @RequestMapping("/selectUser")
    @ResponseBody
    public Map<String, Object> selectUser(
            @RequestParam(value = "qtype") int qtype,
            @RequestParam(value = "qval") String qval){

        Map<String, Object> map = new HashMap<>();
        List<WbUserInf> list = null;

        switch (qtype){
            case 0:
                list = wbUserSelectService.queryUserByLoginId(qval);
                break;
            case 1:
                list = wbUserSelectService.queryUserByRole(Integer.valueOf(qval));
                break;
            case 2:
                list = wbUserSelectService.queryUserByOrg(Integer.valueOf(qval));
                break;
        }

        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);
        return map;

    }

    @RequestMapping("/orgList")
    public void orgList(@RequestParam(value = "node") Integer node, HttpServletResponse response) throws IOException {
        String orgs = wbOrganizationService.queryOrganization(node);
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(orgs);
        writer.flush();
        writer.close();
    }

    @RequestMapping("/roleList")
    @ResponseBody
    public Map<String, Object> roleList() {
        Map<String, Object> map = new HashMap<>();
        List<WbUserRole> list = wbRoleService.queryBySearchParam(0, Integer.MAX_VALUE);
        long total = wbRoleService.countBySearchParam();

        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);
        map.put(RESULT_TOTAL, total);
        return map;
    }

}
