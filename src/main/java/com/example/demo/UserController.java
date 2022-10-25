package com.example.demo;

import com.example.demo.frame.Result;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author yuanlb
 * @since 2022/9/29
 */
@RestController
@RequestMapping(value = "/foo")
public class UserController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;


    @RequestMapping("/hello")
    @Transactional
    public String update(){
        QUserEntity QUser = QUserEntity.userEntity;
        jpaQueryFactory.update(QUser)
                .where(QUser.balance.eq(300L))
                .set(QUser.status, 20)
                .execute();
        return "success";
    }


    @RequestMapping(value = "add")
    public Result add(@RequestBody UserEntity user){
        userRepository.saveAndFlush(user);
        return Result.ok();
    }

    @RequestMapping(value = "query")
    public Object query(){
        PageRequest pageQuery = PageRequest.of(0, 10, Sort.by(
                Sort.Direction.DESC, "id"));
        Page<UserEntity> page = userRepository.findAll( pageQuery);
        page.getContent();

        ArrayList<UserEntity> list = new ArrayList<>(page.getContent());
        list.add(null);
        return page;
    }

}
