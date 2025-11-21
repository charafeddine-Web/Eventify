package com.sec.eventify.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sec.eventify.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class BasicAccessDeniedHandler implements AccessDeniedHandler {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 403, "Forbidden",
                "Access denied", request.getRequestURI()
        );
        response.setStatus(403);
        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}
