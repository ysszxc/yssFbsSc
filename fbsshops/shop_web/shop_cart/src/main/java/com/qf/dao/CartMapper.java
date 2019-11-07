package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Shopcart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/25 0025
 */

@Mapper
public interface CartMapper extends BaseMapper<Shopcart> {

    List<Shopcart> queryBuGid(@Param("gid") Integer[] gid, @Param("uid") Integer uid);
}
