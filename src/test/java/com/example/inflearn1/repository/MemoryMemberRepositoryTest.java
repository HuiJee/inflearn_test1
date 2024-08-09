package com.example.inflearn1.repository;

import com.example.inflearn1.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    /*
        test를 먼저 만들고 구현 클래스를 만들게 되면 테스트 주도 개발 즉, TDD라고 함
        강의에서는 구현 클래스 만들고 잘 동작하는지 테스트를 만들었으므로 해당하지는 않음
    * */

    MemoryMemberRepository repository = new MemoryMemberRepository();

    /*
        전체 테스트 실행 시 실행 순서는 보장 못함
        동일한 객체에 대해 테스트할 경우 오류 발생할 수 있으므로 clear 기능이 필요!
    * */
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        
        Member result = repository.findById(member.getId()).get();

        /*
            // org.junit.jupiter.api
            Assertions.assertEquals(member, result);
            // 두 객체가 같은지 확인(기대하는 대상, 결과)
            // 일지하면 초록불, 다르면 빨간불
        */

        // org.assertj.core.api
        // Assertions.assertThat(member).isEqualTo(result);
        // Assertions는 static 이므로 import 하고 아래와 같이 쓸 수 있음
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);  // 초록불
        // assertThat(result).isEqualTo(member2);  // 빨간불
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

}
