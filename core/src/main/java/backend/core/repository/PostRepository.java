package backend.core.repository;

import backend.core.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public List<Post> findById(Long id) {
        return em.createQuery(
                        "select p from Post p" +
                                " join fetch p.chat c" +
                                " where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Post> findAll(int offset, int limit) {
        return em.createQuery(
                        "select p from Post p" +
                                " join fetch p.chat c", Post.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
