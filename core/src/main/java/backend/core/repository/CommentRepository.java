package backend.core.repository;

import backend.core.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    public Optional<Comment> findById(Long id) {
        List<Comment> comments = em.createQuery(
                        "select c from Comment c" +
                                " join fetch c.member m" +
                                " join fetch c.post p" +
                                " where c.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();

        return comments.stream().findAny();
    }

    public Optional<List<Comment>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select c from Comment c" +
                                        " join fetch c.member m" +
                                        " join fetch c.post p", Comment.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }
}
