package backend.core.repository;

import backend.core.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

    private final EntityManager em;

    public void save(Message message) {
        em.persist(message);
    }

    public Optional<Message> findById(Long id) {
        List<Message> messages = em.createQuery(
                        "select m from Message m" +
                                " join fetch m.member m2" +
                                " join fetch m.chat c" +
                                " where m.id = :id", Message.class)
                .setParameter("id", id)
                .getResultList();

        return messages.stream().findAny();
    }

    public Optional<List<Message>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select m from Message m" +
                                        " join fetch m.member m2" +
                                        " join fetch m.chat c", Message.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }

}
