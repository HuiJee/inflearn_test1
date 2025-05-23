package com.example.inflearn1.repository;

import com.example.inflearn1.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

//    @Autowired // 생성자가 하나면 @Autowired 생략 가능
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    /*
     < JdbcTemplate 대신 DataSource를 주입받는 이유>
     
     1. 유연성
        - DataSource를 주입받으면 필요에 따라 다양한 방식으로 JdbcTemplate을 구성할 수 있음
        - 예를 들어, 특정 설정이 필요한 경우 JdbcTemplate 생성 시 추가 옵션을 설정할 수 있음
     2. 테스트 용이성
        - DataSource를 주입받으면 테스트 시 실제 DataSource 대신 mock DataSource를 쉽게 주입할 수 있음
        - 단위테스트를 더 쉽게 작성할 수 있게 해줌
     3. 재사용성
        - 동일한 DataSource를 사용하여 여러 개의 JdbcTemplate 인스턴스를 생성할 수 있음
        - 각 인스턴스는 다른 설정을 가질 수 있음

     https://www.inflearn.com/community/questions/1320853
    * */

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // insert 쿼리 없이 아래 설정으로 처리 가능
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }

}
