package backend.core.repository;

import backend.core.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public List<Comment> findById(Long id) {
        return em.createQuery(
                        "select c from Comment c" +
                                " join fetch c.member m" +
                                " join fetch c.post p" +
                                " where c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Comment> findAll(int offset, int limit) {
        return em.createQuery(
                        "select c from Comment c" +
                                " join fetch c.member m" +
                                " join fetch c.post p", Comment.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
