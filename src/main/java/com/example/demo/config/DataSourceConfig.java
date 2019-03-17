package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
//@PropertySource("application.properties")
@EnableJpaRepositories({"com.example.demo.core.dao"})
public class DataSourceConfig {
//    private static final String DATABASE_DRIVER = "db.driver";
//    private static final String DATABASE_URL = "db.url";
//    private static final String DATABASE_USER = "db.user";
//    private static final String DATABASE_PASSWORD = "db.password";
//    private static final String PACKAGES_TO_SCAN = "packages.to.scan";
//    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
//    private static final String HIBERNATE_SHOW_SQL = "hibernate.show.sql";

//    @Resource
//    private Environment env;
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }
    @Bean
    public PropertyConfigurer propertyConfigurer(){
        PropertyConfigurer propertyConfigurer = new PropertyConfigurer();
        propertyConfigurer.setIgnoreResourceNotFound(true);//propertyConfigurer.setLocations(locations);
        return propertyConfigurer;
    }
    /**
     * 配置监控服务器
     * @return 返回监控注册的servlet对象
     * @author SimpleWu
     */
//    @Bean
//    public ServletRegistrationBean statViewServlet() {
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//        // 添加IP白名单
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
//        // 添加IP黑名单"当白名单和黑名单重复时"黑名单优先级更高
//        servletRegistrationBean.addInitParameter("deny", "127.0.0.1");
//        // 添加控制台管理用户
//        servletRegistrationBean.addInitParameter("loginUsername", "SimpleWu");
//        servletRegistrationBean.addInitParameter("loginPassword", "123456");
//        // 是否能够重置数据
//        servletRegistrationBean.addInitParameter("resetEnable", "false");
//        return servletRegistrationBean;
//    }

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
//    @Bean
//    public FilterRegistrationBean statFilter() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        // 添加过滤规则
//        filterRegistrationBean.addUrlPatterns("/*");
//        // 忽略过滤格式
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
//        return filterRegistrationBean;
//    }

}
