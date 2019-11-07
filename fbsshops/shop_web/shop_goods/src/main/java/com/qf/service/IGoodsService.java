package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/10 0010
 */
public interface IGoodsService {

    List<Goods> queryAllGoods();

    int goodsInsert(Goods goods);

    Goods queryById(Integer gid);

    int goodsDel(Integer gid);
}
