package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.GoodsImagesMapper;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsImages;
import com.qf.feign.ItemFeign;
import com.qf.feign.SearchFeign;
import com.qf.service.IGoodsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/10 0010
 */

@Service
public class GoodsSereviceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsImagesMapper goodsImagesMapper;

    @Autowired
    private SearchFeign searchFeign;

    @Autowired
    private ItemFeign itemFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Override
    public List<Goods> queryAllGoods() {
        return goodsMapper.queryAllGoods();
    }

    @Override
    @Transactional
    public int goodsInsert(Goods goods) {

        goodsMapper.insert(goods);

        GoodsImages goodsImages = new GoodsImages(
                goods.getId(),
                null,
                goods.getFengmian(),
                1
        );

        goodsImagesMapper.insert(goodsImages);

        for (String s : goods.getImgother()) {
            GoodsImages otherImage = new GoodsImages(
                    goods.getId(),
                    null,
                    s,
                    0
            );

            goodsImagesMapper.insert(otherImage);
        }

//        System.out.println("GoodsServiceImpl中goods的值为："+goods.getId());
//        boolean flag = searchFeign.insertSolr(goods);
//        System.out.println("GoodsServiceImpl中flag的值为："+flag);
//
//        if (!flag) {
//            throw new RuntimeException("索引库添加失败");
//        }
//
//        itemFeign.createHtml(goods);

        rabbitTemplate.convertAndSend("goods_exchange","",goods);


        return 1;
    }

    @Override
    public Goods queryById(Integer gid) {

        Goods goods = goodsMapper.selectById(gid);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid",gid);
        queryWrapper.eq("isfengmian",1);
        GoodsImages goodsImages = goodsImagesMapper.selectOne(queryWrapper);

        goods.setFengmian(goodsImages.getUrl());
        return goods;
    }

    @Override
    public int goodsDel(Integer gid) {
        goodsMapper.deleteById(gid);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid",gid);
        goodsImagesMapper.delete(queryWrapper);
        return 1;
    }
}
