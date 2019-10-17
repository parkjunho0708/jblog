package kr.co.itcen.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // AOP를 걸어줌.
@PropertySource("classpath:kr/co/itcen/config/app/properties/jdbc.properties") // properties를 읽어주는 어노테이션 (classpath를 잡아줌.)
public class DBConfig {
	
//	@Autowired
//	private Environment env; // PropertyResolver
	
	@Value(value = "${jdbc.driverClassName}")
	private String driverClassName;
	
	@Value(value = "${jdbc.url}")
	private String url;
	
	@Value(value = "${jdbc.username}")
	private String userName;
	
	@Value(value = "${jdbc.password}")
	private String password;
	
	@Value(value = "${jdbc.initialSize}")
	private int initialSize;
	
	@Value(value = "${jdbc.maxActive}")
	private int maxActive;
	
	
//	@Bean
//	public DataSource dataSource() {
//		BasicDataSource basicDataSource = new BasicDataSource();
//		basicDataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//		basicDataSource.setUrl(env.getProperty("jdbc.url"));
//		basicDataSource.setUsername(env.getProperty("jdbc.username"));
//		basicDataSource.setPassword(env.getProperty("jdbc.password"));
//		basicDataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));
//		basicDataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));
//		return basicDataSource;
//	}
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driverClassName);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(userName);
		basicDataSource.setPassword(password);
		basicDataSource.setInitialSize(initialSize);
		basicDataSource.setMaxActive(maxActive);
		return basicDataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
