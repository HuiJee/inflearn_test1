package com.example.inflearn1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Inflearn1Application {

    public static void main(String[] args) {
        SpringApplication.run(Inflearn1Application.class, args);
        
        /*
            gradle을 통해서 실행하게 되면 느리기 때문에
            settings -> gradle 검색해서 
            build and run using / test using을 모두 intelliJ로 바꿔줌
            => intelliJ에서 java를 바로 돌리기에 더 빠름
        * */
    }

}
