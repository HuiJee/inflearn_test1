package com.example.inflearn1.repository;

import com.example.inflearn1.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    // 동시성 문제가 있을 수 있으므로 공유되는 변수일 경우 ConcurrentHashMap을 써야 함
    private static Map<Long, Member> store = new HashMap<>();
    // 이 또한 동시성 문제를 위해 Atomic Long을 사용해야 함
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        // member 객체를 돌면서 해당 name과 동일한 걸 하나 찾으면 바로 반환
        // 끝까지 돌렸을 때 존재하지 않으면 Optional에 null이 담겨 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
