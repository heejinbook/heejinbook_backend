package com.book.heejinbook.security;

import com.book.heejinbook.entity.User;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //헤더에 토큰을 담는 속성이 없으면 비로그인으로 판단
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //Bearer 로 시작하는 정상 토큰인지 유효성 검사
        if (!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        String token = authorizationHeader.split(" ")[1];

        //토큰 만료시간 검사
        if (TokenProvider.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = TokenProvider.getUserId(token);

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, List.of(new SimpleGrantedAuthority("USER"))
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);


    }

}
