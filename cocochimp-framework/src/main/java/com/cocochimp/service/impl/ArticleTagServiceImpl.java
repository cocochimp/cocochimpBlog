package com.cocochimp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cocochimp.domain.entity.ArticleTag;
import com.cocochimp.mapper.ArticleTagMapper;
import com.cocochimp.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-02-18 09:03:31
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

