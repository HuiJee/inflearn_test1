package com.example.inflearn1;

import com.example.inflearn1.controller.MemberController;
import com.example.inflearn1.repository.MemberRepository;
import com.example.inflearn1.repository.MemoryMemberRepository;
import com.example.inflearn1.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    
    // 스프링 빈을 수동으로 직접 등록하는 경우

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

//    @Bean
//    public MemberController memberController() {
//        return new MemberController(memberService());
//    }

}
