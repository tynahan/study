package me.tynahan.demoinsttinfomanage.repository;

import lombok.RequiredArgsConstructor;
import me.tynahan.demoinsttinfomanage.domain.InsttInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InsttInfoRepository {
    private final EntityManager em;
    public void save(InsttInfo insttInfo) {
        em.persist(insttInfo);
    }

    public InsttInfo findOne(Long id) {
        return em.find(InsttInfo.class, id);
    }

    public List<InsttInfo> findAll() {
        return em.createQuery("select i from InsttInfo i", InsttInfo.class).getResultList();
    }
}
