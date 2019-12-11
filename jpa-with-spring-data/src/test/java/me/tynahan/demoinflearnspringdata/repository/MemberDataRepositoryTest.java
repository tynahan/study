package me.tynahan.demoinflearnspringdata.repository;

import me.tynahan.demoinflearnspringdata.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberDataRepositoryTest {

    @Autowired
    MemberDataRepository memberDataRepository;

    @Test
    public void testMember() {
        // Given
        Member member = new Member("newbie");

        // When
        Member saveMember = memberDataRepository.save(member);

        // Then
        Member findMember = memberDataRepository.findById(saveMember.getId()).get();

        Assertions.assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());

    }
}