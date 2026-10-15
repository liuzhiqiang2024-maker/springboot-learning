package com.example.springbootdemo;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.Article;
import com.example.springbootdemo.service.ArticleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public Result<Article> add(@Valid @RequestBody Article article) {
        log.info("新增文章：title={}", article.getTitle());
        return Result.success(articleService.add(article));
    }
    @PutMapping("/{id}")
    public Result<Article> update(@PathVariable Integer id, @Valid @RequestBody Article article) {
        log.info("修改文章：id={}",id);
        article.setId(id);
        return Result.success(articleService.update(article));
    }
    @DeleteMapping("/{id}")
    public Result<Article> delete(@PathVariable Integer id) {
        log.info("删除文章：id={}",id);
        articleService.delete(id);
        return Result.success();
    }
    @GetMapping("/list")
    public Result<List<Article>>findAll() {
        log.info("查询所有文章");
        return Result.success(articleService.findAll());
    }
    @PostMapping("/{id}/view")
    public Result<Void>increaseView(@PathVariable Integer id) {
        log.info("浏览器+1：id={}",id);
        articleService.increaseViewCount(id);
        return Result.success();
    }
}


