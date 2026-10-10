package com.example.springbootdemo.entity;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public class User {
    private  Integer id;
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度必须是 2-20 个字符")
    private String name;

    @Min(value = 0, message = "年龄不能小于 0")
    @Max(value = 150, message = "年龄不能大于 150")
    private Integer age;
    private String password;
    public User() {
    }
    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public Integer getAge(){return age;}
    public void setAge(Integer age){this.age = age;}
    @Override
    public String toString() {
        return "User{id=" + id + ", name=" + name + ", age=" + age + "}";
    }
    private BigDecimal money;
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public BigDecimal getMoney() { return money; }
    public void setMoney(BigDecimal money) { this.money = money; }
}
