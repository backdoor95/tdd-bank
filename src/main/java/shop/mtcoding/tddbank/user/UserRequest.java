package shop.mtcoding.tddbank.user;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    @Getter @Setter
    public static class JoinDTO{ // 강사님은 보통 내부클래스로 만든다고함.
        private String username;
        private String password;
        private String email;
        private String fullName;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .fullName(fullName)
                    .status(true)
                    .roles("ROLE_USER")
                    .build();
        }
    }
}
