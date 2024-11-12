package com.spring.boot;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
public class SpringBootBoardApplication {

	@Autowired
	ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootBoardApplication.class, args);
	}
	
	//마이바티스를 연결할 때 팩토리를 만들고 템플릿을 만들고 데이타소스로 연결했다.
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) 
			throws Exception{
		
		SqlSessionFactoryBean sessionFactory =
				new SqlSessionFactoryBean();
		
		sessionFactory.setDataSource(dataSource);
		
		/*
		Resource[] res = new PathMatchingResourcePatternResolver()
				.getResources("classpath:mybatis/mapper/*.xml");
		
		sessionFactory.setMapperLocations(res);
		*/
		
		//위와 아래는 같다.
		//단, 아래를 쓰기 위해선 의존성 주입을 해주어야한다.(18,19)
		sessionFactory.setMapperLocations(
				applicationContext.getResources(
						"classpath:mybatis/mapper/*.xml"));
		
		
		/*	
		<bean id="sessionFactory"
		class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource"/>
		<property name="configLocation"
		value="classpath:/com/exe/springmybatis/mybatis-config.xml"/>
		</bean> 
		  
		 */
		
		return sessionFactory.getObject();

		
	}
}
