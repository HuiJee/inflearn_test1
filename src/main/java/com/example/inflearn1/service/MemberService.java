package com.example.inflearn1.service;

import com.example.inflearn1.domain.Member;
import com.example.inflearn1.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /** 회원가입 */
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        /*
        하지만 이와 같이 optional을 권장하지는 않음!(위의 코드처럼 작성)
        그리고 findByName 이후에 로직이 쭉 나오는 경우 별도의 메서드 분리하는 것이 좋음
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> { // 존재한 경우(null이 아닌 경우)
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /** 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
