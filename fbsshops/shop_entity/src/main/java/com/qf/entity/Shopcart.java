package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author Yss
 * @Date 2019/10/25 0025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Shopcart extends BaseEntity {

    private Integer uid;
    private Integer gid;
    private Integer number;
    private BigDecimal cartPrice;

    @TableField(exist = false)
    private Goods goods;
}
