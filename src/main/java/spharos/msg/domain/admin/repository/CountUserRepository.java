package spharos.msg.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spharos.msg.domain.admin.entity.CountUser;

@Repository
public interface CountUserRepository extends JpaRepository<CountUser, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE CountUser cu SET cu.outCount = cu.outCount + 1 WHERE cu.id = :index")
    void incrementOutCount(Long index);
    //todo : low를 여러개 가져갈건지, ex)월별로 등  생각 다시좀 해볼것.
}
