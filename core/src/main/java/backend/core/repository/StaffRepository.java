package backend.core.repository;

import backend.core.domain.Staff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StaffRepository {

    private final EntityManager em;

    public void save(Staff staff) {
        em.persist(staff);
    }

    public Staff findOne(Long id) {
        return em.find(Staff.class, id);
    }

    public List<Staff> findById(Long id) {
        return em.createQuery(
                        "select s from Staff s" +
                                " join fetch s.member m" +
                                " join fetch s.post p" +
                                " where s.id = :id", Staff.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Staff> findAll(int offset, int limit) {
        return em.createQuery(
                        "select s from Staff s" +
                                " join fetch s.member m" +
                                " join fetch s.post p", Staff.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
