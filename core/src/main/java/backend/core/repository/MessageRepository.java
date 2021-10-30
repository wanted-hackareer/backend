package backend.core.repository;

import backend.core.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

    private final EntityManager em;

    public void save(Message message) {
        em.persist(message);
    }

    public List<Message> findById(Long id) {
        return em.createQuery(
                        "select m from Message m" +
                                " join fetch m.member m2" +
                                " join fetch m.chat c" +
                                " where m.id = :id", Message.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Message> findAll(int offset, int limit) {
        return em.createQuery(
                        "select m from Message m" +
                                " join fetch m.member m2" +
                                " join fetch m.chat c", Message.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

}
