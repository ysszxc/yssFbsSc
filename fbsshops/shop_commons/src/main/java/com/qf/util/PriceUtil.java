package com.qf.util;

import com.qf.entity.Shopcart;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/27 0027
 */
public class PriceUtil {


    public static  double allprice(List<Shopcart> shopcarts){

        BigDecimal allprice = BigDecimal.valueOf(0);

        if (shopcarts != null){
            for (Shopcart shopcart : shopcarts) {
                BigDecimal price = shopcart.getGoods().getPrice();

                BigDecimal number = BigDecimal.valueOf(shopcart.getNumber());

                BigDecimal multiply = price.multiply(number);

                allprice = allprice.add(multiply);
            }
        }

        return allprice.doubleValue();
    }
}
