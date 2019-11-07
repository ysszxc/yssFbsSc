package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Yss
 * @Date 2019/10/22 0022
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Email implements Serializable {

    private String subject;
    private String from;
    private String to;
    private String content;


}
