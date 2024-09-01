package com.team.thundergather.global.auth.jwt.component;

import com.team.thundergather.global.exception.CustomException;
import com.team.thundergather.global.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
//@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        CustomException customException = (CustomException) request.getAttribute("exception");
        if (customException == null) {
            customException = new CustomException(ErrorCode.UNAUTHORIZED);
        }

        resolver.resolveException(request, response, null, customException);
    }
}
