package shop.mtcoding.tddbank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor // 생성자 주입 (DI )
@RestController //data를 응답할때 스프링에서 사용하는 어노테이션
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO joinDTO){ //json
        // 1. 유효성 검사

        // 2. 회원가입(서비스 요청) - 시큐리티는 패스워드 인코딩이 무조건 되어야함. 인코딩 안하면 회원가입은 되나 나중에 로그인할때 터짐.
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));// password 인코딩해서 다시 password에 집어넣음.
        userRepository.save(joinDTO.toEntity());

        // 3. 응답
        return ResponseEntity.ok().body("ok");
    }
}
