package backend.core.repository;

import backend.core.domain.Member;
import backend.core.global.error.exception.CustomException;
import backend.core.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        List<Member> member = em.createQuery(
                        "select m from Member m" +
                                " where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
        return member.stream().findAny();
    }

    public Optional<List<Member>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select m from Member m", Member.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }

    public Optional<Member> findByEmail(String email) {
        List<Member> members = em.createQuery(
                        "select m from Member m" +
                                " where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();

        return members.stream().findAny();
    }
}
