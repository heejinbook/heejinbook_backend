package com.book.heejinbook.security;

import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        try{
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Auth authAnnotation = handlerMethod.getMethodAnnotation(Auth.class);

                if (authAnnotation != null) {
                    boolean includeUserIdx = authAnnotation.includeUserIdx();
                    if (includeUserIdx) {

                        if (!request.getHeader("Authorization").startsWith("Bearer ")) {
                            throw new CustomException(UserErrorCode.HANDLE_ACCESS_DENIED);
                        }
                        String token = request.getHeader("Authorization").split(" ")[1];

                        Long userId = TokenProvider.getUserId(token);
                        request.setAttribute("userId", userId);
                        AuthHolder.setUserId(userId);
                    }
                    return true;
                }
            }

            return HandlerInterceptor.super.preHandle(request, response, handler);
        } catch (NullPointerException e) {
            throw new CustomException(UserErrorCode.HANDLE_ACCESS_DENIED);
        }

    }

    // JWT 토큰 검증 로직을 여기에 구현

}
