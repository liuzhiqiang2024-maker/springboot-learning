package com.example.springbootdemo.entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.time.LocalDateTime;
public class Comment {
    private Integer id;
    @NotNull(message = "文章ID不能为空")
    private Integer articleId;

    @NotNull(message="用户ID不能为空")
    private Integer  userId;
    @NotBlank(message = "评论内容不能为空")
    private String content;
    private Integer parentId;
    private LocalDateTime createTime;
    private String userName;
    private List<Comment> children;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getArticleId() {
        return articleId;
    }
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public List<Comment> getChildren() {
        return children;
    }
    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Comment{"+
                "id="+id+
                ",articleId="+articleId+
                ",userId="+userId+
                ",content="+content+
                ",parentId="+parentId+
                ",createTime="+createTime+
                ",userName="+userName+'\''+
                '}';
    }
}
