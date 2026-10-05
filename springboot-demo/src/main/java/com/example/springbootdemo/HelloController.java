package com.example.springbootdemo;
import com.example.springbootdemo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {
    @Autowired
    private HelloService helloService1;

    @Autowired
    private HelloService helloService2;

    @GetMapping("/test-singleton")
    public String testSingleton() {
        return "同一个对象吗？" + (helloService1 == helloService2);
    }
    @Autowired
    private HelloService helloService;
    @GetMapping("/hello")
    public String hello(){
        return helloService.sayHello("world");
    }
}
