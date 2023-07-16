package shop.mtcoding.tddbank._core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

@RequiredArgsConstructor
@Component
public class DBInit {

    private final PasswordEncoder passwordEncoder;

    @Bean
      // 서버 시작시에 데이터 초기화 -> 로그인 하는걸 빠르게 테스트할 수 있게된다.
    CommandLineRunner initDB(UserRepository userRepository){
        return args -> {
            User ssar = User.builder()
                    .username("ssar")
                    .password(passwordEncoder.encode("1234"))
                    .email("ssar@nate.com")
                    .fullName("쌀")
                    .status(true)
                    .roles("ROLE_USER")
                    .build();

        };
    }
}
