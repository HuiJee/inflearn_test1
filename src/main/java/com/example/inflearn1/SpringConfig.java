package com.example.inflearn1;

import com.example.inflearn1.controller.MemberController;
import com.example.inflearn1.repository.JdbcMemberRepository;
import com.example.inflearn1.repository.MemberRepository;
import com.example.inflearn1.repository.MemoryMemberRepository;
import com.example.inflearn1.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    /*
    Jdbc 연결 후에는 MemmoryMemberRepository가 아니라
    JdbcMemberRepository로 바꿔준다.
    파라미터로 넣을 dataSource는 아래처럼 바로 @Autowired를 달아도 되고

    그 밑에 생성자?처럼 해도 된다.
    * */
//    @Autowired  DataSource dataSource;

    // 스프링이 DataSource 빈을 만들고
    // 아래처럼 주입해주면 된다.
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    
    // 스프링 빈을 수동으로 직접 등록하는 경우

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
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
