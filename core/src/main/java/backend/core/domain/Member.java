package backend.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String profile;
    private String nickName;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Staff> staffList = new ArrayList<>();

/*    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Basket basket;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Rating rating;*/

    //== 비즈니스 로직 ==//
    @Builder
    public Member (String email, String profile, String nickName, Address address) {
        this.email = email;
        this.profile = profile;
        this.nickName = nickName;
        this.address = address;
        this.role = Role.USER;
    }

    public void update(String profile, String nickName, Address address) {
        this.profile = profile;
        this.nickName = nickName;
        this.address = address;
    }
}
