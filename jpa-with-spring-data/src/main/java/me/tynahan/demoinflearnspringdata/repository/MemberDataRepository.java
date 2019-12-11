package me.tynahan.demoinflearnspringdata.repository;

import me.tynahan.demoinflearnspringdata.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDataRepository extends JpaRepository<Member, Long> {
}
