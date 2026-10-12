package com.example.springbootdemo.entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
public class Article {
    private Integer id;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotNull(message="作者不能为空")
    private Integer categoryId;
    private Integer authorId;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Article() {
    }
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}
    public Integer getAuthorId() {return authorId;}
    public void setAuthorId(Integer authorId) {this.authorId = authorId;}
    public Integer getCategoryId() {return categoryId;}
    public void setCategoryId(Integer categoryId) {this.categoryId = categoryId;}
    public Integer getViewCount() {return viewCount;}
    public void setViewCount(Integer viewCount) {this.viewCount = viewCount;}
    public Integer getLikeCount() {return likeCount;}
    public void setLikeCount(Integer likeCount) {this.likeCount = likeCount;}
    public LocalDateTime getCreateTime() {return createTime;}
    public void setCreateTime(LocalDateTime createTime) {this.createTime = createTime;}
    public LocalDateTime getUpdateTime() {return updateTime;}
    public void setUpdateTime(LocalDateTime updateTime) {this.updateTime = updateTime;}
    @Override
    public String toString() {
        return "Article{id=" + id + ", title=" + title + ", authorId=" + authorId+",categoryId="+categoryId+",viewCount="+viewCount+", likeCount=" + likeCount + "}";
    }
}
