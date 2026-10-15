package com.example.springbootdemo.service;
import com.example.springbootdemo.entity.Article;
import com.example.springbootdemo.exception.BusinessException;
import com.example.springbootdemo.mapper.ArticleMapper;
import com.example.springbootdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public Article findById(Integer id) {
        Article article=articleMapper.findById(id);
        if(article==null){
            throw new BusinessException(404,"文章不存在");
        }
        return article;
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

