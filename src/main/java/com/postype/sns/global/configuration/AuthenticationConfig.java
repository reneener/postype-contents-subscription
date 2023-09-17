package com.postype.sns.global.configuration;

import com.postype.sns.global.exception.CustomAuthenticationEntryPoint;
import com.postype.sns.global.configuration.filter.JwtTokenFilter;
import com.postype.sns.domain.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

	private final MemberService memberService;
	@Value("${jwt.secret-key}")
	private String key;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().regexMatchers("^(?!/api/).*");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "/auth/**", "/api/*/members/register", "/api/*/members/login", "/swagger-ui/**").permitAll()
			.antMatchers("/api/**").authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(new JwtTokenFilter(key, memberService), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling()
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
		;
	}
}
