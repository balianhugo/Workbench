package dev.gavin.wb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gavin.wb.model.WbUserInf;
import dev.gavin.wb.service.WbUserInfService;
import dev.gavin.wb.tools.FormValid;
import dev.gavin.wb.util.AuthorityInfo;
import dev.gavin.wb.util.EncryptTool;
import dev.gavin.wb.util.WebConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户登入页面
 * Created by Administrator on 2016-11-17.
 */

@Controller
public class LoginController extends BaseController {

    @Resource
    private WbUserInfService wbUserInfService;

    @Resource
    private AuthorityInfo authorityInfo;

    @RequestMapping(value = {"/public/login", "/"})
    public String page() {
        return "/public/login";
    }

    @RequestMapping("/public/doLogin")
    @ResponseBody
    public Map<String, Object> doLogin(@RequestParam(value = "loginName") String loginName,
                                       @RequestParam(value = "loginPassword") String loginPassword,
                                       HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();

        switch(loginValid(loginName, loginPassword, request.getContextPath())){
            case 1:
                map.put(RESULT_SUCCESS, false);
                map.put(RESULT_MSG, "账号或密码错误");
                break;
            case 2:
                map.put(RESULT_SUCCESS, false);
                map.put(RESULT_MSG, "账号或密码错误");
                break;
            case 3:
                map.put(RESULT_SUCCESS, false);
                map.put(RESULT_MSG, "账号或密码错误");
                break;
            case 4:
                map.put(RESULT_SUCCESS, false);
                map.put(RESULT_MSG, "账号或密码错误");
                break;
            case 5:
                map.put(RESULT_SUCCESS, false);
                map.put(RESULT_MSG, "账号没有访问权限，请联系管理员");
                break;
            case 6:
                map.put(RESULT_SUCCESS, false);
                map.put(RESULT_MSG, "账号已被冻结，请联系管理员");
                break;
            case 7:
                map.put(RESULT_SUCCESS, true);
                map.put(RESULT_MSG, "账号认证成功，页面跳转中");
                map.put(RESULT_DATA, "/wb/main");

                break;
        }
        return map;
    }


    /***
     * 验证用户登入返回类型 ： 1、2.参数错误 3.账号不存在 4.密码错误 5.权限错误 6.用户被冻结 7.正常认证通过
     * @param loginName
     * @param loginPassword
     * @return
     * @throws JsonProcessingException
     */
    private Integer loginValid(String loginName, String loginPassword, String contextPath) throws JsonProcessingException {

        if (FormValid.validEmpty(loginName) || FormValid.validEmpty(loginPassword)) return 1;
        if (FormValid.validLength(loginName, 1, 20) || FormValid.validLength(loginPassword, 1, 15)) return 2;

        WbUserInf user = wbUserInfService.queryByLoginName(loginName);
        if (null == user) return 3;

        if (!user.getLoginPassword().equals(EncryptTool.md5Hex(loginPassword))) return 4;

        if(!wbUserInfService.checkUserBindRole(loginName)) return 5;

        if(user.getStatus() == 1) return 6;

        final ObjectMapper mapper = new ObjectMapper();
        getSession().setAttribute(WebConstants.WB_USER_INFO, mapper.writeValueAsString(user));
        getSession().setAttribute(WebConstants.WB_USER_INFO_OBJ, user);
        getSession().setAttribute(WebConstants.WB_USER_MENU, authorityInfo.getUserMenu(user.getUserId(), contextPath));
        getSession().setAttribute(WebConstants.WB_USER_AUTHORITY_URL, authorityInfo.getUserAccessURL(user.getUserId()));
        getSession().setAttribute(WebConstants.WB_USER_PAGE_URL, authorityInfo.getUserPageResource(user.getUserId()));

        return 7;
    }

    @RequestMapping("/public/doLogout")
    public String doLogout() {
        getSession().invalidate();
        return "redirect:/public/login";
    }

    @RequestMapping("/public/error/{code}")
    public String error(@PathVariable(name = "code") String code, Model model) {
        model.addAttribute("code", code);
        return "/public/error";
    }

}
