package backend.core.post.repository;

import backend.core.member.domain.QMember;
import backend.core.post.domain.Post;
import backend.core.post.domain.PostStatus;
import backend.core.post.domain.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
                                " where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultList();

        return postList.stream().findAny();
    }

    public Optional<List<Post>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select p from Post p" +
                                        " join fetch p.member m", Post.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }

    public Optional<List<Post>> findAllByTitle(String title) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPost post = QPost.post;
        QMember member = QMember.member;

        List<Post> postList = query
                .select(post)
                .from(post)
                .join(post.member, member)
                .fetchJoin()
                .where(titleLike(title), statusEq(PostStatus.ACCESS))
                .limit(100)
                .fetch();
        return Optional.of(postList);
    }

    public Optional<List<Post>> findAllBySearch(PostSearch postSearch) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QPost post = QPost.post;
        QMember member = QMember.member;

        List<Post> postList = query
                .select(post)
                .from(post)
                .join(post.member, member)
                .fetchJoin()
                .where(statusEq(postSearch.getStatus()),
                        streetLike(postSearch.getStreetA()).or(
                                streetLike(postSearch.getStreetB())
                        ))
                .limit(100)
                .fetch();
        return Optional.of(postList);
    }

    private BooleanExpression streetLike(String street) {
        if (street == null) {
            return null;
        }
        return QPost.post.address.street.like(street);
    }

    private BooleanExpression titleLike(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }
        return QPost.post.title.like(title);
    }

    private BooleanExpression statusEq(PostStatus status) {
        if (status == null) {
            return null;
        }
        return QPost.post.postStatus.eq(status);
    }

    public Optional<List<Post>> findByStatus(PostStatus status) {
        return Optional.of(
                em.createQuery(
                                "select p from Post p" +
                                        " join fetch p.member m" +
                                        " where p.postStatus = :status", Post.class)
                        .setParameter("status", status)
                        .getResultList()
        );
    }
}
