package com.example.springbootdemo.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.springbootdemo.entity.Article;
@Mapper
public interface ArticleMapper {
    Article findById(@Param("id") Integer id);
    List<Article> findAll();
    int insert(Article article);
    int update(Article article);
    int deleteById(@Param("id") Integer id);
    int increaseViewCount(@Param("id") Integer id);
    int increaseLikeCount(@Param("id") Integer id);

}
