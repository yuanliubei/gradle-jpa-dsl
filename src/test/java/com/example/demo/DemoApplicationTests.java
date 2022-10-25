package com.example.demo;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    private UserRepository userRepository;

    @Resource
    private JPAQueryFactory jpaQueryFactory;

    @Test
    void contextLoads() {


    }

    @Test
    public void insert() {
        UserEntity user = new UserEntity();
        user.setUsername("yuanlb");
        user.setPassword("123456");
        user.setBalance(100L);
        user.setStatus(10);
        userRepository.saveAndFlush(user);
    }

    @Test
    public void update() {
        Optional<UserEntity> user = userRepository.findById(11L);
        user.ifPresent(u -> {
            u.setUsername("yuanliubei88");
            userRepository.saveAndFlush(u);
        });
    }

    @Test
    public void delete() {
        userRepository.deleteById(12L);
    }

    @Test
    public void list() {
        QUserEntity qUser = QUserEntity.userEntity;
        BooleanExpression expression = qUser.username.eq("yuanliubei");
        Iterable<UserEntity> users = userRepository.findAll(expression);
        System.out.println("users = " + users);
    }

    @Test
    public void query(){
        BooleanExpression expression = QUserEntity.userEntity.username.eq("yuanlb");
        PageRequest pageQuery = PageRequest.of(0, 10, Sort.by(
                Sort.Direction.DESC, "id"));
        Page<UserEntity> page = userRepository.findAll(expression, pageQuery);
        System.out.println("page = " + page.getContent());
    }

    @Test
    public void factoryQuery(){
        BooleanBuilder builder = new BooleanBuilder();

        QUserEntity QUser = QUserEntity.userEntity;
        List<UserEntity> users = jpaQueryFactory.selectFrom(QUser)
                .where(QUser.username.endsWith("liubei"))
                .fetch();
        System.out.println("users = " + users);

        List<Tuple> tuples = jpaQueryFactory.select(QUser.username, QUser.balance)
                .from(QUser)
                .where(QUser.balance.goe(200))
                .fetch();
        tuples.forEach(tuple -> {
            String name = tuple.get(QUser.username);
            System.out.println("name = " + name);
        });
        System.out.println("tupls = " + tuples);
    }

    @Test
    @Modifying
    @Transactional
    public void factoryUpdate(){
        QUserEntity QUser = QUserEntity.userEntity;
        jpaQueryFactory.update(QUser)
                .where(QUser.balance.eq(300L))
                .set(QUser.status, 30)
                .execute();
    }
}
