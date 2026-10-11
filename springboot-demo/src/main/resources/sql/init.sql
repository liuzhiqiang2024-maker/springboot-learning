-- 数据库
CREATE DATABASE IF NOT EXISTS blog_db DEFAULT CHARACTER SET utf8mb4;
USE blog_db;

-- 用户表（已存在，仅参考）
CREATE TABLE IF NOT EXISTS user (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(50) NOT NULL UNIQUE,
                                    age INT,
                                    password VARCHAR(100),
                                    avatar VARCHAR(255),
                                    money DECIMAL(10,2) DEFAULT 0,
                                    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 分类
CREATE TABLE IF NOT EXISTS category (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(50) NOT NULL,
                                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 文章
CREATE TABLE IF NOT EXISTS article (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       title VARCHAR(200) NOT NULL,
                                       content TEXT NOT NULL,
                                       author_id INT NOT NULL,
                                       category_id INT,
                                       view_count INT DEFAULT 0,
                                       like_count INT DEFAULT 0,
                                       create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                       update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 标签
CREATE TABLE IF NOT EXISTS tag (
                                   id INT PRIMARY KEY AUTO_INCREMENT,
                                   name VARCHAR(50) NOT NULL UNIQUE
);

-- 文章标签关联
CREATE TABLE IF NOT EXISTS article_tag (
                                           id INT PRIMARY KEY AUTO_INCREMENT,
                                           article_id INT NOT NULL,
                                           tag_id INT NOT NULL
);

-- 评论
CREATE TABLE IF NOT EXISTS comment (
                                       id INT PRIMARY KEY AUTO_INCREMENT,
                                       article_id INT NOT NULL,
                                       user_id INT NOT NULL,
                                       content VARCHAR(500) NOT NULL,
                                       parent_id INT DEFAULT NULL,
                                       create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 点赞
CREATE TABLE IF NOT EXISTS `like` (
                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                      user_id INT NOT NULL,
                                      article_id INT NOT NULL,
                                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      UNIQUE KEY uk_user_article (user_id, article_id)
);

-- 索引
CREATE INDEX idx_user_name ON user(name);
CREATE INDEX idx_article_author ON article(author_id);
CREATE INDEX idx_article_category ON article(category_id);
CREATE INDEX idx_article_create_time ON article(create_time DESC);
CREATE INDEX idx_article_tag_article ON article_tag(article_id);
CREATE INDEX idx_article_tag_tag ON article_tag(tag_id);
CREATE INDEX idx_comment_article ON comment(article_id);
CREATE INDEX idx_comment_parent ON comment(parent_id);