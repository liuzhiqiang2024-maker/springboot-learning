USE blog_db;

-- 用户（如果还没数据）
INSERT IGNORE INTO user (id, name, age, password, money) VALUES
(1, 'testuser', 25, '$2a$10$xxxx', 1000);

-- 分类
INSERT INTO category (name) VALUES ('Java'), ('Spring'), ('数据库'), ('前端');

-- 标签
INSERT INTO tag (name) VALUES ('SpringBoot'), ('MySQL'), ('Redis'), ('JWT');

-- 文章
INSERT INTO article (title, content, author_id, category_id) VALUES
                                                                 ('SpringBoot 入门', 'SpringBoot 是...', 1, 1),
                                                                 ('MySQL 索引详解', 'B+树是...', 1, 3),
                                                                 ('Redis 缓存实战', 'Redis 内存...', 1, 3);

-- 文章标签关联
INSERT INTO article_tag (article_id, tag_id) VALUES
                                                 (1, 1), (1, 2), (2, 2), (3, 3);