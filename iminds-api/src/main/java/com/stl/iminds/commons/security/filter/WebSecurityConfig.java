package com.stl.iminds.commons.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.stl.iminds.commons.security.filter.BasicAuthenticationTokenFilter;
import com.stl.iminds.commons.security.filter.WebFilter;
import com.stl.iminds.commons.security.utils.SecurityConstant;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint unauthorizedHandler;


	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private Environment env;

	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		if(SecurityConstant.ENCRYPTION_TYPE_VALUE.equalsIgnoreCase(env.getProperty(SecurityConstant.ENCRYPTION_TYPE_PROPERTY))){
			authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());	
		}else{
			authenticationManagerBuilder.userDetailsService(this.userDetailsService);
		}
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry security = http
				.authorizeRequests();

		//Don't change without knowing functionality
		basicAuthenticationTokenFilter();
		webFilter();

		// don't create session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// To enable cross origin resource sharing 
		http.cors();
		http.headers();
		security.antMatchers(HttpMethod.POST, "/*").permitAll();
		security.antMatchers(HttpMethod.OPTIONS, "/*").permitAll();
		security.antMatchers(HttpMethod.GET, "/*").permitAll();
		security.antMatchers(HttpMethod.PUT, "/*").permitAll();
		security.and().csrf().disable().httpBasic();
		http.addFilterBefore(getWebFilter(), WebAsyncManagerIntegrationFilter.class);
		http.addFilterBefore(getAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
	}

	@Bean
	public FilterRegistrationBean<BasicAuthenticationTokenFilter> basicAuthenticationTokenFilter() {
		FilterRegistrationBean<BasicAuthenticationTokenFilter> registrationBean = new FilterRegistrationBean<>();
		BasicAuthenticationTokenFilter basicAuthenticationTokenFilter = getAuthenticationTokenFilter();
		registrationBean.setFilter(basicAuthenticationTokenFilter);
		registrationBean.setOrder(2);
		return registrationBean;
	}

	private BasicAuthenticationTokenFilter getAuthenticationTokenFilter() {
		BasicAuthenticationTokenFilter basicAuthenticationTokenFilter=new BasicAuthenticationTokenFilter();
		beanFactory.autowireBean(basicAuthenticationTokenFilter);
		return basicAuthenticationTokenFilter;
	}


	@Bean
	public FilterRegistrationBean<com.stl.iminds.commons.security.filter.WebFilter> webFilter() {
		FilterRegistrationBean<com.stl.iminds.commons.security.filter.WebFilter> registrationBean = new FilterRegistrationBean<>();
		com.stl.iminds.commons.security.filter.WebFilter webFilter = getWebFilter();
		registrationBean.setFilter(webFilter);
		registrationBean.setOrder(1);
		return registrationBean;
	}


	private com.stl.iminds.commons.security.filter.WebFilter getWebFilter() {
		com.stl.iminds.commons.security.filter.WebFilter webFilter=new WebFilter();
		beanFactory.autowireBean(webFilter);
		return webFilter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	
}