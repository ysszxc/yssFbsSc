package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yss
 * @Date 2019/10/10 0010
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Goods extends BaseEntity {

    private String subject;
    private String info;
    private BigDecimal price;
    private Integer save;

    @TableField(exist = false)
    private String fengmian;

    @TableField(exist = false)
    private List<String> imgother;

    @TableField(exist = false)
    private  List<GoodsImages> goodsImages;

}
