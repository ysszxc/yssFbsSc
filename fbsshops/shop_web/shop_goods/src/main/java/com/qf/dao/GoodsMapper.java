package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Goods;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/10 0010
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> queryAllGoods();


}
