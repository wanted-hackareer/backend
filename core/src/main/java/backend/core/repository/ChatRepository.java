package backend.core.repository;

import backend.core.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private final EntityManager em;

    public void save(Chat chat) {
        em.persist(chat);
    }

    public Optional<Chat> findById(Long id) {
        List<Chat> chatList = em.createQuery(
                        "select c from Chat c" +
                                " join fetch c.post p" +
                                " where c.id = :id", Chat.class)
                .getResultList();

        return chatList.stream().findAny();
    }

    public Optional<List<Chat>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select c from Chat c" +
                                        " join fetch c.post p", Chat.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }
}
