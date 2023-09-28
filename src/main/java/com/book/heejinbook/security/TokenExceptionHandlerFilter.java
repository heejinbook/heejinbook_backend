package com.book.heejinbook.security;

import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.ErrorCode;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(UserErrorCode.CANT_ACCESS);
        } catch (AccessDeniedException e) {
            setErrorResponse(UserErrorCode.HANDLE_ACCESS_DENIED);
        } catch (JwtException e) {
            filterChain.doFilter(request, response);
        }
    }

    private void setErrorResponse(ErrorCode errorCode) {
        throw new CustomException(errorCode);
    }

}
