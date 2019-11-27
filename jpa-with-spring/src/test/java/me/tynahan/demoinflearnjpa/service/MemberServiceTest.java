package me.tynahan.demoinflearnjpa.service;

import me.tynahan.demoinflearnjpa.domain.Member;
import me.tynahan.demoinflearnjpa.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void testJoinMember() {
        // Given
        String name = "newbie";
        System.out.println("memberRepository.findByName(name).size() = " + memberRepository.findByName(name).size());

        Member member = new Member();
        member.setName(name);

        // When
        Long savedId = memberService.join(member);

        // Then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    @Transactional
    public void testDuplicateJoinException() {
        // Given
        String newbie = "newbie";

        Member member1 = new Member();
        member1.setName(newbie);

        Member member2 = new Member();
        member2.setName(newbie);

        // When
        memberService.join(member1);
        memberService.join(member2);

        // Then
        fail("Occure duplication join Exception");
    }
}
