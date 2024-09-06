package com.example.inflearn1.repository;

import com.example.inflearn1.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();

    /*
        findById나 findByName의 경우 null을 반환할 수 있음

        이를 처리하는 방법 중 하나가, 그대로 반환하는 것보다 Optional로 감싸서 반환하는 것!
    * */
}
