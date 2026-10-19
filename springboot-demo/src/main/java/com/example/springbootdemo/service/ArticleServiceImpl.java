package com.example.springbootdemo.service;
import com.example.springbootdemo.entity.Article;
import com.example.springbootdemo.exception.BusinessException;
import com.example.springbootdemo.mapper.ArticleMapper;
import com.example.springbootdemo.mapper.CommentMapper;
import com.example.springbootdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.concurrent.TimeUnit;
import java.util.List;
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public Article add(Article article) {
        //1.检查作者存在
        if(userMapper.findById(article.getAuthorId())==null){
            throw new BusinessException(400,"作者不存在");
        }
        //2.插入
        articleMapper.insert(article);
        //3.日志
        log.info("新增文章：id={},title={}",article.getId(),article.getTitle());
        //4.返回
        return article;
    }
    // 在类里面注入 RedisTemplate
    @Autowired
    private StringRedisTemplate redisTemplate;
    // 还需要一个 JSON 转换工具
    @Autowired
    private ObjectMapper objectMapper; // Spring 默认提供的 Jackson 工具

    @Override
    public Article update(Article article) {
        Article exist=articleMapper.findById(article.getId());
        if(exist==null){
            throw new BusinessException(404,"文章不存在");
        }
        //2.更新
        articleMapper.update(article);
        //3.日志
        log.info("修改文章：id={}",article.getId());
        return article;
    }
    @Override
    public void delete(Integer id) {
        //1.检查存在
        if(articleMapper.findById(id)==null){
            throw new BusinessException(404,"文章不存在");
        }
        //2.删除
        articleMapper.deleteById(id);
        //3.日志
        log.info("删除文章：id={}",id);
    }

    @Override
    public Article findDetailById(Integer id) {
        String cacheKey = "article:detail:" + id;

        try {
            // 1. 先查 Redis
            String cachedJson = redisTemplate.opsForValue().get(cacheKey);
            if (cachedJson != null) {
                log.info("从 Redis 获取文章详情，id={}", id);
                // 把 JSON 转回 Article 对象
                return objectMapper.readValue(cachedJson, Article.class);
            }

            // 2. Redis 没有，查 MySQL
            log.info("从 MySQL 查询文章详情，id={}", id);
            Article article = articleMapper.findDetailById(id);
            if (article == null) {
                throw new BusinessException(404, "文章不存在");
            }

            // 查标签
            List<String> tags = articleMapper.findTagsByArticleId(id);
            article.setTags(tags);

            // 3. 写回 Redis，并设置 10 分钟过期
            String json = objectMapper.writeValueAsString(article);
            redisTemplate.opsForValue().set(cacheKey, json, 10, TimeUnit.MINUTES);

            return article;
        } catch (Exception e) {
            log.error("文章缓存异常", e);
            // 如果 Redis 挂了，走降级方案，直接查数据库
            Article article = articleMapper.findDetailById(id);
            if (article == null) throw new BusinessException(404, "文章不存在");
            article.setTags(articleMapper.findTagsByArticleId(id));
            return article;
        }
    }
    @Override
    public Article findById(Integer id) {
        Article article = articleMapper.findById(id);
        if (article == null) {
            throw new BusinessException(404, "文章不存在");
        }
        return article;
    }

    @Override
    public List<Article>findByPage(int page, int size) {
        int offset = (page - 1) * size;
        return articleMapper.findByPage(offset,size);
    }
    @Override
    public List<Article> searchByTitle(String keyword) {
        return articleMapper.searchByTitle(keyword);
    }
    @Override
    public List<Article> searchAdvanced(Integer categoryId,Integer authorId) {
        return articleMapper.searchAdvanced(categoryId,authorId);
    }

    @Override
    public List<Article> findAll() {
        return articleMapper.findAll();
    }
    @Override
    public void increaseViewCount(Integer id) {
        articleMapper.increaseViewCount(id);
        Article article=articleMapper.findById(id);
    }
}

