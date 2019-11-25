package me.tynahan.demoinsttinfomanage.repository;

import me.tynahan.demoinsttinfomanage.domain.InsttInfo;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InsttInfoRepositoryTest {

    @Autowired
    InsttInfoRepository insttInfoRepository;

    @Test
    public void testSaveInsttInfo() {
        // Given
        InsttInfo insttInfo = new InsttInfo();
        insttInfoRepository.save(insttInfo);

        // When
        InsttInfo findInsttInfo = insttInfoRepository.findOne(insttInfo.getId());

        // Then
        Assertions.assertThat(findInsttInfo).isEqualTo(insttInfo);
    }

    @Test
    public void testFindAll() {
        // Given
        int orgSize = insttInfoRepository.findAll().size();
        InsttInfo insttInfo = new InsttInfo();
        insttInfoRepository.save(insttInfo);

        // When
        int findSize = insttInfoRepository.findAll().size();

        // Then
        Assertions.assertThat(findSize).isEqualTo(orgSize + 1);
    }
}