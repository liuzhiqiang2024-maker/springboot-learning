package com.example.springbootdemo.service;
import com.example.springbootdemo.entity.Article;
import java.util.List;
public interface ArticleService {
    Article add(Article article);
    Article update(Article article);
    void delete(Integer id);
    Article findById(Integer id);
    List<Article> findAll();
    void increaseViewCount(Integer id);
    List<Article> findByPage(int page,int size);
    List<Article> searchByTitle(String keyword);
    List<Article> searchAdvanced(Integer categoryId,Integer authorId);
}
