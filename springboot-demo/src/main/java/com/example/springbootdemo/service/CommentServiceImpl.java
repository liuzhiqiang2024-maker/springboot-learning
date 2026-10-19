package com.example.springbootdemo.service;
import com.example.springbootdemo.entity.Comment;
import com.example.springbootdemo.exception.BusinessException;
import com.example.springbootdemo.mapper.CommentMapper;
import com.example.springbootdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Slf4j
public class CommentServiceImpl  implements CommentService{
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Comment add(Comment comment) {
        //校验用户存在
        if(userMapper.findById(comment.getUserId())==null){
            throw new BusinessException(400,"用户不存在");
        }
        //插入
        commentMapper.insert(comment);
        log.info("新增评论：id={},userName{}",comment.getId(),comment.getUserId(),comment.getArticleId());
        return comment;
    }
    @Override
    public List<Comment> getCommentTree(Integer articleId) {
        //1.查出该文章的所有平铺评论
        List<Comment>allComments=commentMapper.findByArticleId(articleId);
        //2.构建树形结构
        Map<Integer,List<Comment>> childrenMap=new HashMap<>();
        for(Comment c:allComments){
            if(c.getParentId()!=null){
                childrenMap.computeIfAbsent(c.getParentId(),k -> new ArrayList<>()).add(c);
            }
        }
        //3.把子评论挂到父评论上
        List<Comment>rootComments=new ArrayList<>();
        for(Comment c:allComments){
            if(c.getParentId()==null){
                c.setChildren(childrenMap.getOrDefault(c.getId(),new ArrayList<>()));
                rootComments.add(c);
            }
        }
        return rootComments;
    }


}


