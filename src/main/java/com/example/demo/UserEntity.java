package com.example.demo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author yuanlb
 * @since 2022/9/28
 */
@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Long balance;

    private Integer status;

    private LocalDateTime createTime;

}
