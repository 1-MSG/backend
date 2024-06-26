package spharos.msg.domain.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import spharos.msg.global.entity.BaseEntity;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Users extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(min = 5, max = 20)
    @Column(name = "login_id")
    private String loginId;

    @Column(name = "uuid")
    private String uuid;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank()
    @Pattern(regexp = "^\\d{10,11}$")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "user_name")
    private String userName;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private UserStatus status;

    /**
     * 기본 배송지 Index (default 0)
     */
    @Column(columnDefinition = "bigint default 0")
    private Long baseAddressId;

    @Builder
    public Users(Long id, String loginId, String uuid, String password, String phoneNumber, String email,
            String userName, String address, UserStatus status) {
        this.id = id;
        this.loginId = loginId;
        this.uuid = uuid;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userName = userName;
        this.address = address;
        this.baseAddressId = 0L;
        this.status = status;
    }


    /**
     * Entity의 userName을 반환합니다.
     *
     * @return userName;
     */
    public String readUserName() {
        return this.userName;
    }

    public Users(String uuid) {
        this.uuid = uuid;
    }

    public void passwordToHash(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * @return uuid를 반환함
     */
    @Override
    public String getUsername() {
        return this.uuid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
