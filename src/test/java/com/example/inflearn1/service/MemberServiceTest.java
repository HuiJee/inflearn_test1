package com.example.inflearn1.service;

import com.example.inflearn1.domain.Member;
import com.example.inflearn1.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    //MemberService memberService = new MemberService();
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // MemberService에 있는 MemoryMemberRepository랑 위의 MemoryMemberRepository가 서로 다름
    // 해당 클래스의 store가 static이기에 다행이지만, 그게 아니면 문제 생김
    // 따라서 같은 instance를 쓰게 만들어 줌! (MemoryService에서 생성자 이용하고, 여기는 @BeforeEach)

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
        // 해당 방식으로 사용하면 같은 memoryMemberRepository 사용하게 됨
        // 직접 NEW가 아닌 외부에서 넣도록! => 이런게 Dependency Injection 즉, DI(의존성주입)이라고 함
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }
    // 전체 테스트 할 때 돌 때마다 메모리 clear 된다.

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