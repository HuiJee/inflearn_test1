package com.example.inflearn1.service;

import com.example.inflearn1.domain.Member;
import com.example.inflearn1.repository.MemberRepository;
import com.example.inflearn1.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 통합 테스트 영상 부분
@SpringBootTest
@Transactional
// 테스트는 테스트일 뿐! 테스트 쪽에 @Transactional이 있으면 자동 Rollback 처리
class MemberServiceIntegrationTest {

    // 기존과 달리 Container에게 repository 받기
    // 그냥 test니까 @Autowired로 간단하게!
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

//    @AfterEach
//    public void afterEach() {
//        memberRepository.clearStore();
//    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
/*
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
*/
/*
        아래처럼 작성할 수 있지만, 단순한 구문이기에 위에 코드로 대체할 수 있다.
        해당 작업을 할 때 해당 Exception이 터지면 통과!
        try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}