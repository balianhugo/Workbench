package dev.gavin.wb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 基类提供一些通用的属性与方法，便于子类调用
 * Created by Administrator on 2016-11-16.
 */
public class BaseController {

    // 日志调用
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // json 数据通用参数
    protected static final String RESULT_SUCCESS = "success";
    protected static final String RESULT_MSG = "msg";
    protected static final String RESULT_DATA = "data";
    protected static final String RESULT_TOTAL = "total";

    @Resource
    protected HttpServletRequest request;

    /**
     * 通用获取当前 HttpSession
     *
     * @return
     */
    protected HttpSession getSession() {
        return request.getSession();
    }

    /**
     *
     * @Title: processFileName
     *
     * @Description: ie,chrom,firfox下处理文件名显示乱码
     */
    protected String processFileName(String fileName) {
        String codedfilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie

                String name = java.net.URLEncoder.encode(fileName, "UTF8");

                codedfilename = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等

                codedfilename = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }

    /**
     * 输出json格式响应数据，内部默认的日期格式化方式是使用时间戳
     *
     * @param obj
     * @param response
     * @throws IOException
     */
    protected void writeJsonResponse(Object obj, HttpServletResponse response)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        mapper.writeValue(writer, obj);
        writer.flush();
    }

    /**
     * 输出json格式响应，使用特定的日期格式
     *
     * @param obj
     * @param datePattern
     * @param response
     * @throws IOException
     */
    protected void writeJsonResponse(Object obj, String datePattern, HttpServletResponse response)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        mapper.setDateFormat(new SimpleDateFormat(datePattern));
        mapper.writeValue(writer, obj);
        writer.flush();
    }

    /**
     * 通用文件上传操作
     *
     * @param filename 上传文件名集合
     * @param filepath 文件集合
     * @param pathStore 文件存储路径
     * @return
     * @throws IOException
     */
    protected void fileUpload(String[] filename, MultipartFile[] filepath, String pathStore) throws IOException {

        logger.info("//********* start upload files ***********//");


        if (filepath.length > 0) {
            int index = 0;
            for (MultipartFile file : filepath) {
                String nameSuffix = "";
                String newFileName = "";

                if(null != filename){
                    if (filename[index].lastIndexOf(".") != -1) {
                        nameSuffix = filename[index].substring(filename[index].lastIndexOf("."));
                        newFileName = filename[index].substring(0, filename[index].lastIndexOf("."));
                    } else {
                        nameSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                        newFileName = filename[index];
                    }
                }else {
                    nameSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                    newFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
                }

                newFileName = newFileName +  "-" + System.currentTimeMillis() + nameSuffix;
                logger.info("file-newName：{}, file-aldName：{}, file-size: {}", newFileName, file.getOriginalFilename(), file.getSize());

                File temp = new File(pathStore, newFileName);
                file.transferTo(temp);
                index++;
            }

            getSession().removeAttribute("progressStatus");
            logger.info("//********* upload files success ***********//");
        }
    }

    /**
     * 获取上传进度信息
     *
     * @return
     */
    protected Map<String, Object> fileProgress() {
        Map<String, Object> map = new HashMap<>();
        map.put(RESULT_SUCCESS, true);
        map.put(RESULT_DATA, getSession().getAttribute("progressStatus"));
        return map;
    }

}
