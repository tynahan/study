package me.tynahan.demoinflearnspringdata.repository;

import me.tynahan.demoinflearnspringdata.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamDataRepository extends JpaRepository<Team, Long> {
}
