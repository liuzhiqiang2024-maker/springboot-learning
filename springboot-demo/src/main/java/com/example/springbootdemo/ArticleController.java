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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/{id}/like")
    public Result<Void> like(@PathVariable Integer id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            throw new BusinessException(401, "请先登录");
        }
        String redisKey = "article:like:" + id;

        Long added = stringRedisTemplate.opsForSet().add(redisKey, userId);

        if (added != null && added > 0) {
            log.info("用户{}给文章{}点赞成功", userId, id);
            articleMapper.increaseLikeCount(id);
            return Result.success();
        } else {
            throw new BusinessException(400, "你已经点过赞了");
        }
    }
    @GetMapping("/{id}/like/status")
    public Result<Boolean> likeStatus(@PathVariable Integer id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId == null) {
            return Result.success(false);
        }
        String redisKey = "article:like:" + id;
        Boolean isMatch = stringRedisTemplate.opsForSet().isMember(redisKey, userId);
        return Result.success(isMatch!=null&&isMatch);
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


