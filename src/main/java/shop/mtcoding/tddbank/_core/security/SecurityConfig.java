package shop.mtcoding.tddbank._core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;

@Configuration // component 스캔이됨
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return new BCryptPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            //AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new CustomUsernamePasswordAuthenticationFilter());
            super.configure(builder);
        }
    }

    @Bean// 컴퍼넌트 스캔시에 @Bean이 붙은 메서드가 있으면 실행시키면서 리턴되는 값을 IoC에 등록하는 깃발
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // 기존의 시큐리티 필터 체인이 작동을 멈추고 이 메서드가 실행이된다(custom 가능해짐.)

        // 1. CSRF 해제
        http.csrf().disable();
        // -> postman 테스틀 하기위해 disable
        // x-www-form-urlencoded
//        http.formLogin()
//                .loginProcessingUrl("/login");// POST /login x-www-form-urlencoded // 필터에서 로그인하는 로직이 이미 만들어져있다.

        // json으로 로그인 하고싶으면 formLogin을 disable해야함.
        // disable 하면 UsernamePasswordAuthenticationFilter가 작동을 안하는것임.
        //UsernamePasswordAuthenticationFilter (Form POST x-www-form-urlencoded 작동) - 세션 작동
        http.formLogin().disable();

        //위에 UsernamePasswordAuthenticationFilter 가 작동안하면 HttpBasicAuthenticationFilter 작동
        // HttpBasicAuthenticationFilter 가 작동하지 않도록함.
        // HttpBasicAuthenticationFilter -> http 통신할때 헤더에다가 username, password를 달고가는 인증방식 -> 매번 헤더에 username, password를 달고가는 이 필터가 작동
        http.httpBasic().disable();

        // 필터들을 갈아끼우는 내부클래스
        http.apply(new CustomSecurityFilterManager());



        http.authorizeRequests(authorize -> authorize.antMatchers("/").authenticated().anyRequest().permitAll() );
        //위 코드의 의미 : /는 인증필요, 나머지는 인증 불필요

        return http.build();
    }
}
