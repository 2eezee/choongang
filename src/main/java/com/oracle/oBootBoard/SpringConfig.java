package com.oracle.oBootBoard;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dao.JdbcDao;

@Configuration
public class SpringConfig {
		private final DataSource dataSource;
		public SpringConfig(DataSource dataSource) {
			this.dataSource = dataSource;
		}
		
		// @Bean로 설정하면 component처럼 사용할 수 있다 ?
		
		@Bean
		public BDao jdbcDao() {
			return new JdbcDao(dataSource);
			// return new MemoryMemberRepository();
		}
	}

