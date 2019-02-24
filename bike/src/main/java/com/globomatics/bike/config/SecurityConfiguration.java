package com.globomatics.bike.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@EnableWebSecurity // https://spring.io/blog/2013/07/03/spring-security-java-config-preview-web-security
@Configuration // run this class when spring boot container starts
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Value(value = "${auth0.apiAudience}") // inject values
	private String apiAudience;
	@Value(value = "${auth0.issuer}")
	private String issuer;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    JwtWebSecurityConfigurer
        .forRS256(apiAudience, issuer)
        .configure(http)
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/v1/bikes").permitAll()
        .antMatchers(HttpMethod.GET, "/api/v1/bikes").hasAuthority("view:registrations") // auth0 scope
        .antMatchers(HttpMethod.GET, "/api/v1/bikes/**").hasAuthority("view:registration")
        .anyRequest().authenticated();
	}

}
