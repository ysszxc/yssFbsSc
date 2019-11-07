package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/17 0017
 */
public interface ISearchService {

    boolean insert(Goods goods);

    List<Goods> queryByKey(String keyword);

}