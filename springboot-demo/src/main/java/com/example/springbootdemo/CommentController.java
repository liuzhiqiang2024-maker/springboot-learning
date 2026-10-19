package com.example.springbootdemo;

import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.Comment;
import com.example.springbootdemo.service.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Result<Comment> add(@Valid @RequestBody Comment comment) {
        log.info("发表评论：articleId={}", comment.getArticleId());
        return Result.success(commentService.add(comment));
    }

    @GetMapping("/article/{articleId}")
    public Result<List<Comment>> getTree(@PathVariable Integer articleId) {
        log.info("获取文章评论树：articleId={}", articleId);
        return Result.success(commentService.getCommentTree(articleId));
    }
}