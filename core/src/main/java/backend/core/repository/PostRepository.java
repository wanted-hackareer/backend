package backend.core.repository;

import backend.core.domain.Post;
import backend.core.domain.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Optional<Post> findById(Long id) {
        List<Post> postList = em.createQuery(
                        "select p from Post p" +
                                " join fetch p.member m" +
                                " join fetch p.chat c" +
                                " where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultList();

        return postList.stream().findAny();
    }

    public Optional<List<Post>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select p from Post p" +
                                        " join fetch p.member m" +
                                        " join fetch p.chat c", Post.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }

    public Optional<List<Post>> findByStatus(PostStatus status) {
        return Optional.of(
                em.createQuery(
                                "select p from Post p" +
                                        " join fetch p.member m" +
                                        " join fetch p.chat c" +
                                        " where p.postStatus = :status", Post.class)
                        .setParameter("status", status)
                        .getResultList()
        );
    }
}
