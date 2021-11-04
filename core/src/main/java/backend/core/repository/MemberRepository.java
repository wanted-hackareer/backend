package backend.core.repository;

import backend.core.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
        return Optional.of(em.createQuery(
                        "select m from Member m" +
                                " where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult());
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
        return Optional.of(em.createQuery(
                                "select m from Member m" +
                                        " where m.email = :email", Member.class)
                        .setParameter("email", email)
                        .getSingleResult());
    }
}
