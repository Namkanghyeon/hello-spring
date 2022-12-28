package hello.hellospring.controller;

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
        return "hello"; // 컨트롤러에서 리턴 값으로 문자열을 반환하면 viewResolver가 resources/templates에 있는 hello.html을 찾아서 처리함
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model) {
        // cmd + p 누르면 옵션 볼 수 있음
        // required = false로 설정 시 파라리터 안 넣어도 에러없이 null 들어감
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    // @ResponseBody: http 응답의 body에 메서드가 반환하는 값을 직접 넣어주겠다는 의미
    // viewResolver 대신 HttpMessageConverter가 동작
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        // cmd + shift + enter: 문장 자동 완성
        hello.setName(name);
        return hello;
        // @ResponseBody가 붙어있을때, 객체를 내리면 HttpMessageConverter가 디폴트로 json으로 변환해서 응답이 감
    }

    static class Hello {
        private String name;
        //cmd + n: constructor나 getter, setter 등 자동으로 생성

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
