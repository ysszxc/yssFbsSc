package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.CartMapper;
import com.qf.entity.Goods;
import com.qf.entity.Shopcart;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import com.qf.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Yss
 * @Date 2019/10/25 0025
 */

@Service
public class CartServiceImpl implements ICartService {


    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsFeign goodsFeign;

    @Override
    public String insertCart(Integer gid, Integer number, User user,String cartToken) {

        Shopcart shopcart = new Shopcart( user != null ? user.getId() : null,
                gid,number,null,null);

        if (user != null){
            cartMapper.insert(shopcart);
        }else{

            cartToken = cartToken == null ?  UUID.randomUUID().toString() : cartToken;

            redisTemplate.opsForList().leftPush(cartToken,shopcart);
            redisTemplate.expire(cartToken,365, TimeUnit.DAYS);

            return cartToken;
        }

        return null;
    }

    @Override
    public List<Shopcart> queryShopCart(String cartToken, User user) {

        List<Shopcart> list = null;
        if (user != null){
            //已登录
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid",user.getId());
            //按照时间降序排序
            queryWrapper.orderByDesc("create_time");
            list = cartMapper.selectList(queryWrapper);
        }else {
            //未登录
            if (cartToken != null){
                Long size = redisTemplate.opsForList().size(cartToken);

                list = redisTemplate.opsForList().range(cartToken, 0, size);
            }

        }

        if (list != null){
            for (Shopcart shopcart : list) {
                Goods goods = goodsFeign.queryById(shopcart.getGid());
//                System.out.println(goods);
                shopcart.setGoods(goods);
            }
        }

        return list;
    }

    @Override
    public int mergeShopCart(String cartToken, User user) {

        if (cartToken != null){
            Long size = redisTemplate.opsForList().size(cartToken);

            List<Shopcart> shopcarts = redisTemplate.opsForList().range(cartToken, 0, size);

            for (Shopcart shopcart : shopcarts) {
                shopcart.setUid(user.getId());

                cartMapper.insert(shopcart);
            }

            redisTemplate.delete(cartToken);

            return 1;
        }


        return 0;
    }

    @Override
    public List<Shopcart> queryBuGid(Integer[] gid, Integer uid) {

        List<Shopcart> shopcarts = cartMapper.queryBuGid(gid, uid);

        if (shopcarts != null){
            for (Shopcart shopcart : shopcarts) {
                Goods goods = goodsFeign.queryById(shopcart.getGid());
//                System.out.println(goods);
                shopcart.setGoods(goods);
            }
        }
        return shopcarts;
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return cartMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public List<Shopcart> queryByIds(Integer[] ids) {
        List<Shopcart> shopcarts = cartMapper.selectBatchIds(Arrays.asList(ids));
        if(shopcarts != null) {
            for (Shopcart shopcart : shopcarts) {
                Goods goods = goodsFeign.queryById(shopcart.getGid());
                shopcart.setGoods(goods);
            }
        }

        return shopcarts;
    }
}
