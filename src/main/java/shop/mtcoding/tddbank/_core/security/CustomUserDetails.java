package shop.mtcoding.tddbank._core.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.mtcoding.tddbank.user.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {//시큐리티는 앞으로 이놈을 통해서 인증&로그인 처리함.

    private final User user;

    // 시큐리티는 이 친구를 보고 어떤 권한이 있는지 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        // return "USER_ROLE";// ROLE 이라는 권한을 들고있는 놈이라고 리턴하면 시큐리티가 알아서 ROLE 권한이 있는데만 보내줌.
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // 비밀번호 5번 틀리면 false로 구현

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // 인증이 만료된 사람인지

    @Override
    public boolean isEnabled() {
        return true;
    } // 비활성화 된 유저인지
}
