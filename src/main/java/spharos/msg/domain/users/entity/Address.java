package spharos.msg.domain.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String addressNickname;

    @NotBlank
    private String recipient;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String recipientPhoneNumber;

    @NotBlank
    private String addressDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @Builder
    public Address(String addressNickname, String recipient, String phoneNumber,
            String recipientPhoneNumber, String addressDetail, Users users) {
        this.addressNickname = addressNickname;
        this.recipient = recipient;
        this.phoneNumber = phoneNumber;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.addressDetail = addressDetail;
        this.users = users;
    }
}
