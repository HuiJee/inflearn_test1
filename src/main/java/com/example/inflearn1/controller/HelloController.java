package com.example.inflearn1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }
    
    /*
        컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버(viewResolver)가 화면을 찾아 처리함
        resources:templates/{ViewName}.html
    * */

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
    
    
    /*
        @ResponseBody가 있는 경우 viewResolver 대신에 HttpMessageConverter가 동작
        
        기본 문자처리 : StringHttpMessageConverter
        기본 객체처리 : MappingJackson2HttpMessageConverter
        byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
    * */

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
        // 소스 보기 할 경우 해당 데이터만 나옴 (html 코드가 나오는 게 아님)
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    // json 방식으로 출력됨 (해당 페이지와 소스코드 모두)

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
