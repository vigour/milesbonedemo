package com.milesbone.base;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//配置了@ContextConfiguration注解并使用该注解的locations属性指明spring和配置文件之后，
@ContextConfiguration(locations = {"classpath:config/spring/spring-*.xml" })
public abstract class AbstactBeanTestCase{
	protected static final Logger logger = LoggerFactory.getLogger(AbstactBeanTestCase.class);
}
