package backend.core.repository;

import backend.core.domain.Chat;
import backend.core.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private final EntityManager em;

    public void save(Chat chat) {
        em.persist(chat);
    }

    public List<Chat> findById(Long id) {
        return em.createQuery(
                        "select c from Chat c" +
                                " join fetch c.post p" +
                                " where c.id = :id", Chat.class)
                .getResultList();
    }

    public List<Chat> findAll(int offset, int limit) {
        return em.createQuery(
                        "select c from Chat c" +
                                " join fetch c.post p", Chat.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
