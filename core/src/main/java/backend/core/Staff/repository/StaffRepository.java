package backend.core.Staff.repository;

import backend.core.Staff.domain.Staff;
import backend.core.Staff.domain.StaffStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StaffRepository {

    private final EntityManager em;

    public void save(Staff staff) {
        em.persist(staff);
    }

    public Optional<Staff> findById(Long id) {
        List<Staff> staffList = em.createQuery(
                        "select s from Staff s" +
                                " join fetch s.member m" +
                                " join fetch s.post p" +
                                " where s.id = :id", Staff.class)
                .setParameter("id", id)
                .getResultList();

        return staffList.stream().findAny();
    }

    public Optional<List<Staff>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select s from Staff s" +
                                        " join fetch s.member m" +
                                        " join fetch s.post p", Staff.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }

    public Optional<List<Staff>> findByStatus(StaffStatus status) {
        return Optional.of(
                em.createQuery(
                                "select s from Staff s" +
                                        " join fetch s.member m" +
                                        " join fetch s.post p" +
                                        " where s.staffStatus = :status", Staff.class)
                        .setParameter("status", status)
                        .getResultList());
    }
}
