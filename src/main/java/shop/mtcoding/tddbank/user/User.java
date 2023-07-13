package shop.mtcoding.tddbank.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// select를 하면 User 엔티티를 hibernate가 만들어서 영속화 시켜주는데 이때 default 생성자를 호출함.
@NoArgsConstructor // 애가 없으면 hibernate가 user 객체 못만듬, select 할때 default 생성자를 때리기 때문에
@Getter
@Table(name = "user_tb")
@Entity
public class User { // extends 시간설정 (상속)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 120) // 패스워드 인코딩(BCrypt)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;
    @Column(nullable = false, length = 20)
    private String fullName;

    private String roles; // USER, ADMIN -> 권한이 2개가 될수있다.

    private Boolean status; // true, false

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist //persist가 동작하기 직전에 동작 -> flush 되기 직전에 동작 , db에 insert 되면 동작
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate // 더치 체킹되서 update되기 직전?
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public User(Long id, String username, String password, String email, String fullName, String roles, Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}