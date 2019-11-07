package com.qf.hystrix;

import com.qf.entity.Goods;
import com.qf.feign.SearchFeign;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchFeignHystrix implements SearchFeign {
    @Override
    public boolean insertSolr(Goods goods) {
        return false;
    }

    @Override
    public List<Goods> query(String keyword) {
        return null;
    }
}
