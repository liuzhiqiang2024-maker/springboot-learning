package com.example.springbootdemo.interceptor;
import com.example.springbootdemo.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler)throws Exception{
        // 1. 放行 OPTIONS 请求（CORS 预检）
        if("OPTIONS".equals(request.getMethod())){
            return true;
        }
        // 2. 从请求头取 Token
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            log.warn("未登录：{}",request.getRequestURI());
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录\",\"data\":null}");
            return false;
        }
        // 3. 去掉 "Bearer " 前缀
        String token = authHeader.substring(7);
        // 4. 校验 Token
        try{
            Claims claims=JwtUtil.parseToken(token);
            // 5. 把用户信息放进 request，后续接口可以用
            request.setAttribute("userId", claims.getSubject());
            request.setAttribute("username", claims.get("username",String.class));
            log.info("通过拦截器：userId={},uri={}",claims.getSubject(),request.getRequestURI());
            return  true;
        }catch (Exception e){
            log.warn("Token 无效：{}",e.getMessage());
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"Token 无效或已过期\",\"data\":null}");
            return false;
        }

    };
}
