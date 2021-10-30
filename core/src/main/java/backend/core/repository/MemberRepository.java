package backend.core.repository;

import backend.core.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findAll(int offset, int limit) {
        return em.createQuery(
                        "select m from Member m" +
                                " join fetch m.rating r" +
                                " join fetch m.basket b", Member.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Member> findById(Long id) {
        return em.createQuery(
                        "select m from Member m" +
                                " join fetch m.rating r" +
                                " join fetch m.basket b" +
                                " where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Member> findByIdWithRating(Long id) {
        return em.createQuery(
                        "select m from Member m" +
                                " join fetch m.rating r" +
                                " where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Member> findByIdWithBasket(int id) {
        return em.createQuery(
                        "select m from Member m" +
                                " join fetch m.basket b" +
                                " where m.id = :id", Member.class)
                .setParameter("id", id)
                .getResultList();
    }
}
