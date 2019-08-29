package dev.gavin.wb.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * WB 系统异常统一处理
 *
 * Created by Administrator on 2017/4/5.
 */
public class WbExceptionHandler implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(WbExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        String trace = getTrace(e);

        logger.error("系统异常: {}", trace);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("success", false);
        model.put("msg", trace);

        // ajax 异步请求
        if (httpServletRequest.getHeader("x-requested-with") != null &&
                httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");

            PrintWriter out = null;
            try {
                out = httpServletResponse.getWriter();
                ObjectMapper mapper = new ObjectMapper();
                out.append(mapper.writeValueAsString(model));
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return null; // ajax 请求不返回页面

        } else {

            // 根据不同错误转向不同页面
            if(e instanceof WbException) {
                model.put("errorCode", ((WbException) e).getErrorCode());
                model.put("message", e.getMessage());
                return new ModelAndView("/error/exception", model);
            }

            if(e instanceof MaxUploadSizeExceededException) {

                logger.debug("catch the MaxUploadSizeExceededException");

                return new ModelAndView("/error/500", model);
            }

            return new ModelAndView("/error/500", model);
        }
    }


    /**
     * 获取异常栈信息
     * @param t
     * @return
     */
    public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
}
