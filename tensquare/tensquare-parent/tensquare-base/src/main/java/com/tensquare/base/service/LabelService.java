package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import com.tensquare.common.entity.PageResult;
import com.tensquare.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    //添加标签
    public void add(Label label) {
        //生成随机id
        String labelId = String.valueOf(idWorker.nextId());
        //给新增的标签加上随机的id
        label.setId(labelId);
        //新增标签
        labelDao.save(label);
    }

    //查询所有标签
    public List<Label> findAll(Label label) {
        return labelDao.findAll();
    }

    //查询推荐标签
    public List<Label> findRecommand() {
        return labelDao.findAllByRecommendEquals("1");
    }

    //查询有效标签
    public List<Label> findValid() {
        return labelDao.findAllByStateEquals("1");
    }

    //依据id查询标签
    public Label findById(String labelId) {
        return labelDao.findById(labelId).get();
    }

    //依据id修改标签
    public void update(String labelId, Label label) {
        //更新标签id
        label.setId(labelId);
        //保存新的标签
        labelDao.save(label);
    }

    //依据id删除标签
    public void remove(String labelId) {
        labelDao.deleteById(labelId);
    }

    //有条件查询
    public List<Label> search(Label label) {
        //创建查询条件
        Specification<Label> example = null == label ? null : createQueryCriteria(label) ;
        return labelDao.findAll(example);
    }

    //分页查询标签
    public PageResult<Label> findByPage(int page, int size, Label label) {

        //创建分页条件
        //依据Pageable接口的静态方法of()的注释，该方法的第一个参数是从0开始算起，因此"2"实际代表从第三页开始查询
        Pageable pageable = PageRequest.of(page - 1, size);
        //创建查询条件
        Specification<Label> example = null != label ? createQueryCriteria(label) : null;

        Page<Label> pageList = labelDao.findAll(example, pageable);
        return new PageResult<Label>(pageList.getTotalElements(),pageList.getContent());
    }

    private Specification<Label> createQueryCriteria(Label label) {

        return new Specification<Label>() {

            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                //创建列表，存放查询条件
                List<Predicate> predicateList = new ArrayList<>();

                //如果标签状态不为空，则将标签状态添加到查询条件
                if (!StringUtils.isEmpty(label.getState())) {
                    predicateList.add(criteriaBuilder.equal(root.get("state").as(String.class), label.getState()));
                }
                //如果是否推荐标签字段不为空，则将是否推荐添加到查询条件
                if (!StringUtils.isEmpty(label.getRecommend())) {
                    predicateList.add(criteriaBuilder.equal(root.get("recommend").as(String.class), label.getRecommend()));
                }
                //如果标签名称不为空，则将标签名称添加到查询条件
                if (!StringUtils.isEmpty(label.getLabelname())) {
                    predicateList.add(criteriaBuilder.like(root.get("labelname").as(String.class), "%".concat(label.getLabelname()).concat("%")));
                }
                //如果标签使用数量大于0，则将标签使用数量添加到查询条件
                if (null != label.getCount() && label.getCount().intValue() > 0) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("count").as(BigInteger.class), label.getCount()));
                }
                if (null != label.getFans() && label.getFans().intValue() > 0) {
                    predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fans").as(BigInteger.class), label.getFans()));
                }
                //构造查询条件数组
                Predicate[] predicates = new Predicate[predicateList.size()];
                //将查询条件列表转换为查询条件数组，合并查询条件并返回
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };
    }

}
