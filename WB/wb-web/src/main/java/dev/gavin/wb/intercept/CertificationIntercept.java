package dev.gavin.wb.intercept;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gavin.wb.util.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户访问认证拦截器
 */
public class CertificationIntercept extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (null == request.getSession().getAttribute(WebConstants.WB_USER_INFO)) {

            // ajax 异步请求 session 超时处理
            if (request.getHeader("x-requested-with") != null &&
                    request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                response.setHeader("sessionstatus", "timeout");
            } else {
                response.sendRedirect(request.getContextPath() + "/public/login");
            }
            return false;
        }

        List<String> uris = (List<String>) request.getSession().getAttribute(WebConstants.WB_USER_AUTHORITY_URL);
        String uri = request.getServletPath();

        // 权限访问控制
        if (uri.startsWith("/public") || uri.startsWith("/wb")) {
            return true;
        } else {
            if (uris.contains(uri)) {
                return true;
            } else {

                // ajax 异步请求权限处理
                if (request.getHeader("x-requested-with") != null &&
                        request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                    response.setHeader("sessionstatus", "403");
                    if (uri.endsWith(request.getContextPath() + "/page")){
                        response.sendRedirect(request.getContextPath() + "/public/error/403");
                    }else {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json; charset=utf-8");
                        PrintWriter out = null;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("success", false);
                        map.put("msg", "您没有足够的权限!");

                        out = response.getWriter();
                        ObjectMapper mapper = new ObjectMapper();
                        out.append(mapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/public/error/403");
                }
                return false;
            }
        }
    }
}
