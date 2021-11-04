package backend.core.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Staff extends BaseTimeEntity {

    @Id
    @Generated
    private Long id;

    @Enumerated(EnumType.STRING)
    private StaffStatus staffStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getStaffList().remove(this);
        }
        this.member = member;
        member.getStaffList().add(this);
    }

    public void setPost(Post post) {
        if (this.post != null) {
            this.post.getStaffList().remove(this);
        }
        this.post = post;
        post.getStaffList().add(this);
    }

    //== 비즈니스 로직 ==//
    @Builder
    public Staff (Member member, Post post) {
        this.staffStatus = StaffStatus.WAIT;
        setMember(member);
        setPost(post);
    }
}
