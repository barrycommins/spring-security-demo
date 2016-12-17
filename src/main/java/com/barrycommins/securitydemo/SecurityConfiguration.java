package com.barrycommins.securitydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityConfiguration {

    interface TestSecurityConfigurer extends SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {

    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    @Configuration
    static class Test1WebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        final List<TestSecurityConfigurer> testSecurityConfigurers;

        @Autowired(required = false)
        public Test1WebSecurityConfigurerAdapter(List<TestSecurityConfigurer> testSecurityConfigurers) {
            this.testSecurityConfigurers = testSecurityConfigurers;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            if (testSecurityConfigurers != null) {
                for (TestSecurityConfigurer testSecurityConfigurer : testSecurityConfigurers) {
                    http.apply(testSecurityConfigurer);
                }

            }
        }
    }

    @Configuration
    static class Test1SecurityConfigurer implements TestSecurityConfigurer {

        @Override
        public void init(HttpSecurity builder) throws Exception {
            builder.authorizeRequests().antMatchers("/test1")
                    .hasAnyRole("ADMIN")
                    .and()
                    .formLogin();
        }

        @Override
        public void configure(HttpSecurity builder) throws Exception {

        }
    }

    @Configuration
    static class Test2SecurityConfigurer implements TestSecurityConfigurer {

        @Override
        public void init(HttpSecurity builder) throws Exception {
            builder.authorizeRequests().antMatchers("/test2")
                    .hasAnyRole("ADMIN")
                    .and()
                    .formLogin();
        }

        @Override
        public void configure(HttpSecurity builder) throws Exception {

        }
    }
}
