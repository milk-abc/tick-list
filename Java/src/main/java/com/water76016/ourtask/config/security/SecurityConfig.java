package com.water76016.ourtask.config.security;

import com.water76016.ourtask.config.security.handler.MyAuthenticationFailureHandler;
import com.water76016.ourtask.config.security.handler.MyAuthenticationSuccessHandler;
import com.water76016.ourtask.config.security.jwt.JwtAuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
/**
 * @program: our-task
 * @description: Security配置类
 * @author: water76016
 * @create: 2020-09-24 16:45
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private MyAuthenticationSuccessHandler successHandler;

    @Resource
    private MyAuthenticationFailureHandler failureHandler;

    @Resource
    private JwtAuthTokenFilter jwtAuthTokenFilter;

    @Resource
    UserDetailsService myUserDetailsService;

    /**
     * 解决 无法直接注入AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * 需要加上这个，否则会被SpringSecurity拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/sign", "/druid/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 认证失败处理类
                //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //配置权限
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
                .antMatchers("/login", "/register").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/swagger-ui/",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.ico",
                        "/swagger-resources/**",
                        "/druid/**",
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/v2/api-docs/**"
                )
                .permitAll()
                //跨域请求会先进行一次options请求
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
//                需要对外暴露的资源路径
                .antMatchers("login", "register")
//                user角色和admin角色都可以访问
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("user/*", "task/*", "category/*")
                //admin角色可以访问
                .hasAnyRole("ADMIN")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                //authenticated()要求在执行该请求时，必须已经登录了应用
                .and()
                // CRSF禁用，因为不使用session
                //禁用跨站csrf攻击防御，否则无法登陆成功
                .csrf().disable();
        //登出功能
        httpSecurity.logout().logoutUrl("/logout");
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}