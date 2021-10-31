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
    private String picture;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Staff> staffList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Basket basket;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Rating rating;

    //== 비즈니스 로직 ==//
    @Builder
    public Member (String email, String picture, Address address) {
        this.email = email;
        this.picture = picture;
        this.address = address;
        this.role = Role.USER;
    }
}
