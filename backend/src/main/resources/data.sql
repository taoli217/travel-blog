-- 初始用户 (密码: 123456)
INSERT INTO `user` (`username`, `password`, `nickname`, `email`, `role`, `status`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin@example.com', 1, 1),
('test', 'e10adc3949ba59abbe56e057f20f883e', '测试用户', 'test@example.com', 0, 1);

-- 初始分类
INSERT INTO `category` (`name`, `description`, `sort`) VALUES
('日本', '日本旅游相关文章', 1),
('泰国', '泰国旅游相关文章', 2),
('欧洲', '欧洲旅游相关文章', 3),
('美洲', '美洲旅游相关文章', 4),
('国内游', '国内旅游相关文章', 5);

-- 初始标签
INSERT INTO `tag` (`name`) VALUES
('东京'), ('大阪'), ('京都'), ('曼谷'), ('清迈'), ('巴黎'), ('伦敦'), ('纽约'), ('上海'), ('北京'), ('美食'), ('购物'), ('风景'), ('人文');

-- 初始文章
INSERT INTO `article` (`title`, `content`, `summary`, `cover_image`, `view_count`, `like_count`, `comment_count`, `user_id`, `category_id`, `status`, `is_featured`) VALUES
('日本东京五日游攻略', '# 日本东京五日游攻略\n\n## 第一天：抵达与市区\n\n抵达东京后，先入住酒店，然后前往**涩谷**体验潮流文化。\n\n## 第二天：传统与现代\n\n上午参观**浅草寺**，下午前往**东京晴空塔**。\n\n## 第三天：美食之旅\n\n筑地市场吃海鲜，银座购物。\n\n## 第四天：动漫文化\n\n秋叶原动漫天堂，宅男圣地。\n\n## 第五天：返程\n\n根据航班时间安排返程。', '一篇详细的日本东京旅游攻略，包含行程安排、美食推荐', 'https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=800', 520, 45, 12, 1, 1, 1, 1),
('泰国曼谷清迈十日游', '# 泰国曼谷清迈十日游\n\n## 曼谷（5天）\n\n- 大皇宫\n- 郑王庙\n- 湄南河\n- 考山路夜市\n\n## 清迈（5天）\n\n- 清迈古城\n- 夜间动物园\n- 素贴山\n- 周六夜市', '泰国曼谷和清迈的十日游详细攻略', 'https://images.unsplash.com/photo-1528181304800-259b08848526?w=800', 380, 28, 8, 1, 2, 1, 1),
('法国巴黎浪漫之旅', '# 法国巴黎浪漫之旅\n\n## 必去景点\n\n1. 埃菲尔铁塔\n2. 3. 塞卢浮宫\n纳河\n4. 巴黎圣母院\n\n## 美食推荐\n\n- 法式蜗牛\n- 可丽饼\n- 马卡龙', '法国巴黎旅游攻略，感受浪漫之都的魅力', 'https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=800', 420, 35, 10, 1, 3, 1, 1);
