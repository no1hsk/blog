package org.anyframe.cloud.gateway.config;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter is used in production, to serve static resources generated by "grunt build".
 * <p/>
 * <p>
 * It is configured to serve resources from the "dist" directory, which is the Grunt
 * destination directory.
 * </p>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CredentialsCORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to initialize
    }

    @Override
    public void destroy() {
        // Nothing to destroy
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)  throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String referer = request.getHeader("referer");

        if(referer == null){
            referer = request.getHeader("origin");
            if(referer == null){
                referer = "";
            }
        }
        System.out.println("############################################### " + referer);

        if(referer.contains("blog.ssc.com")){
            response.setHeader("Access-Control-Allow-Origin", "https://blog.ssc.com");
            response.setHeader("Access-Control-Allow-Credentials", "true");

        }else if(referer.contains("forum.ssc.com")) {
            response.setHeader("Access-Control-Allow-Origin", " https://forum.ssc.com");
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }else {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, X-XSRF-TOKEN");
        response.setHeader("Access-Control-Max-Age", "3600");

        if(!"OPTIONS".equals(request.getMethod())) {
            chain.doFilter(req, res);
        }
    }

}
