package dev.gavin.wb.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.gavin.wb.controller.BaseController;
import dev.gavin.wb.model.WbUserInf;
import dev.gavin.wb.model.constant.ModelConstant;
import dev.gavin.wb.service.WbUserInfService;
import dev.gavin.wb.tools.FormValid;
import dev.gavin.wb.util.AuthorityInfo;
import dev.gavin.wb.util.EncryptTool;
import dev.gavin.wb.vo.WbUserRoleBindVO;
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
 * 用户管理
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private WbUserInfService wbUserInfService;

    @Resource
    private AuthorityInfo authorityInfo;

    @RequestMapping("/page")
    public String page(Model model) throws JsonProcessingException {
        String authority = authorityInfo.getPageAuthority(getSession(), request.getServletPath());
        model.addAttribute("authority", authority);

        logger.debug("authority: {}", authority);

        return "/wb/sys/user";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> userList(@RequestParam(value = "loginName", required = false) String loginName,
                                        @RequestParam(value = "status", required = false) Byte status,
                                        @RequestParam(value = "start") Integer start,
                                        @RequestParam(value = "limit") Integer limit) {
        Map<String, Object> map = new HashMap<>();
        List<WbUserInf> list = wbUserInfService.queryBySearrchParam(loginName, status, start, limit);
        long total = wbUserInfService.countBySearrchParam(loginName, status);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);
        map.put(RESULT_TOTAL, total);
        return map;
    }

    @RequestMapping("/bindRoles")
    @ResponseBody
    public Map<String, Object> bindRolesList(@RequestParam(value = "userId") Integer userId) {
        Map<String, Object> map = new HashMap<>();

        List<WbUserRoleBindVO> list = wbUserInfService.queryUserBindRole(userId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, list);

        return map;
    }

    @RequestMapping("/bind")
    @ResponseBody
    public Map<String, Object> bindRole(@RequestParam(value = "roleId", required = false) List<Integer> roleId, @RequestParam(value = "userId") Integer userId) {
        Map<String, Object> map = new HashMap<>();

        wbUserInfService.updateUserRoleRel(roleId, userId);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "更新用户角色成功");

        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map<String, Object> updateUser(@ModelAttribute(value = "user") WbUserInf user) {
        Map<String, Object> map = new HashMap<>();

        WbUserInf oldUser = wbUserInfService.queryByLoginName(user.getLoginName());
        if(FormValid.validEmpty(user.getLoginPassword())){
            user.setLoginPassword(oldUser.getLoginPassword());
        }else{
            if(!user.getLoginPassword().equals(oldUser.getLoginPassword())){
                user.setLoginPassword(EncryptTool.md5Hex(user.getLoginPassword()));
            }
        }

        int n = wbUserInfService.update(user);
        if(n > 0){
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "更新用户成功");
        }else{
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "更新用户失败");
        }
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map<String, Object> addUser(@ModelAttribute(value = "user") WbUserInf user) {
        Map<String, Object> map = new HashMap<>();

        if(wbUserInfService.checkLoginName(user.getLoginName())){
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "账号已存在");
            return map;
        }

        if(FormValid.validEmpty(user.getLoginPassword())){
            user.setLoginPassword(EncryptTool.md5Hex(user.getLoginPassword()));
        }else{
            user.setLoginPassword(EncryptTool.md5Hex("123456"));
        }
        user.setStatus(ModelConstant.WB_USER_STATS_OK);

        int n = wbUserInfService.insert(user);
        if(n > 0){
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "新增用户成功");
        }else{
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "新增用户失败");
        }
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteUser(@RequestParam(value = "ids") List<Integer> ids) {
        Map<String, Object> map = new HashMap<>();

        int n = wbUserInfService.deleteByPrimaryKey(ids);
        if(n > 0){
            map.put(RESULT_SUCCESS, true);
            map.put(RESULT_MSG, "删除用户成功");
        }else{
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "删除用户失败");
        }
        return map;
    }

    @RequestMapping("/freezeUser")
    @ResponseBody
    public Map<String, Object> freezeUser(@RequestParam(value = "ids") List<Integer> ids) {
        Map<String, Object> map = new HashMap<>();
        wbUserInfService.updateUserFreeze(ids);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "冻结用户成功");

        return map;
    }



}
