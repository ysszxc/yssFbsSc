package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Yss
 * @Date 2019/10/21 0021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User extends BaseEntity {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
}
