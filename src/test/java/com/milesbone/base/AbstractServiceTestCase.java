package com.milesbone.base;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//配置了@ContextConfiguration注解并使用该注解的locations属性指明spring和配置文件之后，
@ContextConfiguration(locations = {"classpath*:config/spring/spring-*.xml" })
@WebAppConfiguration("src/main/webapp/resource")//扫描了resources配置文件夹里面的配置，后恢复正常
//这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
@Transactional
//这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
@Rollback(true)
public abstract class AbstractServiceTestCase {
}
