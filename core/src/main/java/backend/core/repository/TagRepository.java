package backend.core.repository;

import backend.core.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    private final EntityManager em;

    public void save(Tag tag) {
        em.persist(tag);
    }

    public void delete(Tag tag) {
        em.remove(tag);
    }

    public Optional<Tag> findById(Long id) {
        List<Tag> tags = em.createQuery(
                        "select t from Tag t" +
                                " where t.id = :id", Tag.class)
                .setParameter("id", id)
                .getResultList();

        return tags.stream().findAny();
    }

    public Optional<List<Tag>> findAll(int offset, int limit) {
        List<Tag> tags = em.createQuery(
                        "select t from Tag t", Tag.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return Optional.of(tags);
    }
}
