package shop.mtcoding.tddbank._core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    // login POST 요청일 때 동작
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
        ObjectMapper om = new ObjectMapper(); //이걸로 json을 받는다.

        try {// ctrl alt T 단축키
            // request로 들어오는 json을  java object로 parsing 하는것이다.
            UserRequest.LoginDTO loginDTO = om.readValue(request.getInputStream(), UserRequest.LoginDTO.class);
            UsernamePasswordAuthenticationToken token =
                    UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(),
                            loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);// UserDetailsService -> loadUsername() 실행됨.
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // UserDetailsService가 UserDetails를 잘 리턴하면 실행됨.
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 인증서비스가 실행되기 전에 익셉션이 터지면 실행됨.
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
