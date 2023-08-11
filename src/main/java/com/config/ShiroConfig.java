package com.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.security.*;

@Configuration
public class ShiroConfig {
  @Bean
  public Realm realm() {
    return new CustomRealm();
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
    filterFactory.setSecurityManager(securityManager);

    Map<String, Filter> filters = new HashMap<>();
    filters.put("restAuthc", new RestAuthenticationFilter());
    filterFactory.setFilters(filters);

    Map<String, String> filterChain = new LinkedHashMap<>();
    filterChain.put("/api/auth/login", "anon");
    filterChain.put("/api/auth/register", "anon");
    filterChain.put("/**", "restAuthc");

    filterFactory.setFilterChainDefinitionMap(filterChain);

    return filterFactory;
  }
}
