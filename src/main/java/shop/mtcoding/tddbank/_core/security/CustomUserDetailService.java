package shop.mtcoding.tddbank._core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.mtcoding.tddbank.user.User;
import shop.mtcoding.tddbank.user.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service // component scan됨. 스캔시에 new CustomUserDetailsService() -> IoC 던져짐.
public class CustomUserDetailService implements UserDetailsService {// 내가 동일한 타입으로 만들어서 IoC에 등록하면 덮어씌어짐
    // 시큐리티의 로그인을 커스텀 하는법
    // 1. UserDetailsService를 implements 해서
    // 2. Service로 등록 --> 커스텀 완료
    private final UserRepository userRepository;
    //login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println();
        Optional<User> userOP = userRepository.findByUsername(username);

        if(userOP.isPresent()){
            return new CustomUserDetails(userOP.get());
        }else{
            return null;
        }
    }
}
