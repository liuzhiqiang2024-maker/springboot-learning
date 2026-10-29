package com.example.springbootdemo;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.Article;
import com.example.springbootdemo.exception.BusinessException;
import com.example.springbootdemo.mapper.ArticleMapper;
import com.example.springbootdemo.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    @PostMapping
    public Result<Article> add(@Valid @RequestBody Article article) {
        log.info("新增文章：title={}", article.getTitle());
        return Result.success(articleService.add(article));
    }
    // 注入 RedisTemplate
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 1. 点赞接口
    @PostMapping("/{id}/like")
    public Result<Void> like(@PathVariable Integer id, HttpServletRequest request) {
        // 从拦截器里拿出当前登录用户的 ID（之前拦截器放进去的）
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            throw new BusinessException(401, "请先登录");
        }

        String redisKey = "article:like:" + id;

        // 把 userId 放进 Set 里（Redis 的 SADD 命令）
        Long added = stringRedisTemplate.opsForSet().add(redisKey, userId);

        if (added != null && added > 0) {
            log.info("用户 {} 给文章 {} 点赞成功", userId, id);
            // 真实项目中，这里通常会发送一个消息到 RabbitMQ 或异步去 MySQL 里 +1
            // 今天我们简化：直接顺便把 MySQL 的 like_count 也更新一下
            articleMapper.increaseLikeCount(id);
            return Result.success();
        } else {
            throw new BusinessException(400, "你已经点过赞了");
        }
    }

    // 2. 查询是否已点赞
    @GetMapping("/{id}/like/status")
    public Result<Boolean> likeStatus(@PathVariable Integer id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            return Result.success(false);
        }
        String redisKey = "article:like:" + id;

        // 判断 userId 是否在这个 Set 里
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(redisKey, userId);
        return Result.success(isMember != null && isMember);
    }

    @GetMapping("/list")
    public Result<List<Article>>findAll() {
        log.info("查询所有文章");
        return Result.success(articleService.findAll());
    }

    @GetMapping("/page")
    public Result<List<Article>>page(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "5")int size){
        log.info("分页查询：page={},size={}",page,size);
        return Result.success(articleService.findByPage(page,size));
    }
    @GetMapping("/search")
    public Result<List<Article>> search(@RequestParam String keyword) {
        log.info("搜索文章：keyword={}", keyword);
        return Result.success(articleService.searchByTitle(keyword));
    }

    @GetMapping("/search-advanced")
    public Result<List<Article>> searchAdvanced(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer authorId) {
        log.info("高级搜索：categoryId={}, authorId={}", categoryId, authorId);
        return Result.success(articleService.searchAdvanced(categoryId, authorId));
    }

    @PostMapping("/{id}/view")
    public Result<Void>increaseView(@PathVariable Integer id) {
        log.info("浏览器+1：id={}",id);
        articleService.increaseViewCount(id);
        return Result.success();
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
    @GetMapping("/{id}")
    public Result<Article> findById(@PathVariable Integer id) {
        log.info("查询文章：id={}", id);
        return Result.success(articleService.findDetailById(id));
    }
}


