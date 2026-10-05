package com.example.springbootdemo.entity;
import jakarta.validation.constraints.*;

public class User {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须是 2-20 个字符")
    private String name;

    @Min(value = 0, message = "年龄不能小于 0")
    @Max(value = 150, message = "年龄不能大于 150")
    private int age;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
