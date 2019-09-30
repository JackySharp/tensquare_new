package com.tensquare.search.service;

import com.tensquare.common.entity.PageResult;
import com.tensquare.common.utils.IdWorker;
import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao searchDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        searchDao.save(article);
    }

    public List<Article> searchAll() {
        Iterator<Article> iterator = searchDao.findAll().iterator();
        List<Article> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    public PageResult<Article> searchByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1,size);
        Page<Article> pageList = searchDao.findByContentOrTitleLike(keyword, keyword, pageable);
        return new PageResult<>(pageList.getTotalElements(),pageList.getContent());
    }

}
