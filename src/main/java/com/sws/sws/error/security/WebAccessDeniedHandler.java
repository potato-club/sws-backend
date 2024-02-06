package com.sws.sws.error.security;


import com.sws.sws.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        ErrorCode errorCode = ErrorCode.FORBIDDEN_EXCEPTION;

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject json = new JSONObject();

        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());
        response.getWriter().print(json);
    }

}
