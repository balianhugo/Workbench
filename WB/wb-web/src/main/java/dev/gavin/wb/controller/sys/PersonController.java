package dev.gavin.wb.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gavin.wb.controller.BaseController;
import dev.gavin.wb.model.WbUserInf;
import dev.gavin.wb.service.WbUserInfService;
import dev.gavin.wb.util.AuthorityInfo;
import dev.gavin.wb.util.EncryptTool;
import dev.gavin.wb.util.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息
 * <p>
 */

@Controller
@RequestMapping("/person")
public class PersonController extends BaseController {

    @Resource
    private WbUserInfService wbUserInfService;

    @Resource
    private AuthorityInfo authorityInfo;

    @RequestMapping("/personPage")
    public String personPage(Model model) throws JsonProcessingException {
        String authority = authorityInfo.getPageAuthority(getSession(), request.getServletPath());
        model.addAttribute("authority", authority);

        logger.debug("authority: {}", authority);
        return "/wb/sys/person";
    }

    @RequestMapping("/passwordPage")
    public String passwordPage(Model model) throws JsonProcessingException {
        String authority = authorityInfo.getPageAuthority(getSession(), request.getServletPath());
        model.addAttribute("authority", authority);

        logger.debug("authority: {}", authority);
        return "/wb/sys/password";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateUser(@ModelAttribute(value="user") WbUserInf user)throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        wbUserInfService.update(user);
        ObjectMapper mapper = new ObjectMapper();
        getSession().setAttribute(WebConstants.WB_USER_INFO, mapper.writeValueAsString(user));
        getSession().setAttribute(WebConstants.WB_USER_INFO_OBJ, user);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "信息更新成功");
        return map;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateUser(@RequestParam(value="userId") Integer userId,
                                          @RequestParam(value="oldPassword") String oldPassword,
                                          @RequestParam(value="newPassword") String newPassword){
        Map<String, Object> map = new HashMap<>();
        WbUserInf user = wbUserInfService.queryByPrimaryKey(userId);

        if(!user.getLoginPassword().equals(EncryptTool.md5Hex(oldPassword))){
            map.put(RESULT_SUCCESS, false);
            map.put(RESULT_MSG, "旧密码输入错误");
            return map;
        }

        user.setLoginPassword(EncryptTool.md5Hex(newPassword));
        wbUserInfService.update(user);
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "密码更新成功, 系统将退出重新登入验证");
        return map;
    }

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> handleFileUpload(@RequestParam(value="filename") String[] filename,
                                                @RequestParam(value="filepath") MultipartFile[] filepath) throws IOException {
        Map<String, Object> map = new HashMap<>();
        fileUpload(filename, filepath, "d://");
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_MSG, "文件上传成功");
        return map;
    }

    @RequestMapping(value = "/progress")
    @ResponseBody
    public Map<String, Object> progress() {
        return fileProgress();
    }


}
