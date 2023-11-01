package com.haidong.storysharing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haidong.storysharing.config.security.Impl.*;
import com.haidong.storysharing.config.security.LoginAuthProvider;
import com.haidong.storysharing.config.security.filter.LoginAuthenticationFilter;
import com.haidong.storysharing.config.security.metadatasource.CustomerSecurityMetadataSource;
import com.haidong.storysharing.config.security.rememberme.MyPersistentTokenBasedRememberMeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: HaiDong
 * @Date: 2023/02/23/17:05
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private LoginAuthProvider loginAuthProvider;

    @Autowired
    private CustomerSecurityMetadataSource customerSecurityMetadataSource;

    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandlerImpl authenticationFailureHandler;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    //springboot 对 security 默认配置中  在工厂中默认创建 AuthenticationManager
    // 这里的AuthenticationManagerBuilder会自己去默认的配置中查找
   /* @Autowired
    public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) throws Exception {
        *//*builder.jdbcAuthentication().dataSource(dataSource).withUser("dave")
                .password("123456").roles("USER");*//*
        System.out.println("springboot的默认设置:" + builder);
    }*/

    //自定义AuthenticationManager   使用这个自定义的后 就需要自己手动 设置 不回去全局中查找  推荐
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(loginAuthProvider);
    }

    /**
     * 作用：用来将自定义的AuthenticationManager在工厂中进行暴露，可以在任意位置注入
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 将自定义的 filter 交给工厂管理
     * @return
     */
    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter();
        //指定认证的 url
        loginFilter.setFilterProcessesUrl("/user/login");
        //自定义 自定接受 json 数据中 的用户名和密码的key
        loginFilter.setUsernameParameter("username");
        loginFilter.setPasswordParameter("password");
        //将自己定义的 AuthenticationManager 设置给自定义的 LoginAuthenticationFilter
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        //设置认证成功时使用自定义 rememberMeService  第一次认证成功 往回写
        loginFilter.setRememberMeServices(rememberMeServices());
        //认证成功处理
        loginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        //认证失败处理
        loginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.获取工厂对象
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        //2.设置自定义 url  权限处理
        http.apply(new UrlAuthorizationConfigurer<>(applicationContext))
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(customerSecurityMetadataSource);
                        return object;
                    }
                });
        http.addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests()
                .antMatchers("/",
                        "/user/insertOne",
                        "/user/test",
                        "/user/login",
                        "/userInfo/insertOne",
                        "/redisTest",
                        "/test4",
                        "/testRole/test",
                        "/oss/policy",
                        "/register",
                        "/categories",
                        "/tags",
                        "/stories/{startIndex}/{length}",
                        "/comments",
                        "/comments/{commentId}/replies",
                        "/story/search",
                        "/getStoryCount",
                        "/stories",
                        "/hostStoryList",
                        "/imserver/**",
                        "/sendEmail",
                        "/sendEmail/{username}",
                        "/stories/condition",
                        "/stories/{storyId}",
                        "/stories/condition/{startIndex}/{length}").permitAll()
                //除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and().formLogin()
//                .loginPage("/login.html")
                //.loginProcessingUrl("/user/login").permitAll()
                //.usernameParameter("user")
                //.passwordParameter("1256")
                //.successForwardUrl("/index")  请求转发的形式  浏览器中的请求中地址不变
                //.defaultSuccessUrl("/index",true) 从定向的形式  第二个参数为 总是验证成功后条状到 /index 请求，不要这个参数的时候是用户之前哪个请求被要求验证就跳转到哪个请求中
                //.successHandler() 以上只适合传统的方式，在前后端分离中，推荐使用这个方法，该方法的参数有两个实现类，分别对应着上面两个成功后的相关操作

                //.failureForwardUrl()  请求转发
                //.failureUrl()  重定向
                //.failureHandler()

                //.and().logout()//拿到logout配置对象
                .and().logout()
                //指定注销登录的url
                .logoutUrl("/logout")
                //是否让当前session失效
                .invalidateHttpSession(true)
                //清除 认证标记
                .clearAuthentication(true)
                //注销登录成功后跳转的路径
                .logoutSuccessUrl("/index")
                //来指定推出登录时候的请求方式   默认的是GET 所以提供了该对象
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout","GET"),
                        new AntPathRequestMatcher("/logout","POST")
                ))
                //用户注销时清除Session中的消息
                .invalidateHttpSession(true)
                //用户注销时清除认证信息
                .clearAuthentication(true)
                //  前后端分离时  用户注销时进行的操作
                .logoutSuccessHandler(logoutSuccessHandler)
                /*.and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, ex)->{
                    resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                    resp.getWriter().print("请认证之后再去处理！");
                })*/
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                /*.and()
                .rememberMe()   //开启记住我的功能
                .key(UUID.randomUUID().toString())
                .alwaysRemember(true) //总是记住我
                .rememberMeParameter("remember-me")//用来接收请求中*/
                .and()
                .rememberMe()
                //设置自动登录使用哪个 rememberMeService   第二次 解码这一块
                .rememberMeServices(rememberMeServices())
                //开启跨域访问
                .and().cors()
                .configurationSource(configurationSource())
                //关闭csrf防护
                //.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().csrf().disable()
                //开启会话管理
                .sessionManagement()
                //允许会话最大并发只能一个客户端
                .maximumSessions(1)
                //会话过期处理  传统web的方式  跳转的路径
                //.expiredUrl("/user/login")
                //前后端分离方式
                .expiredSessionStrategy(event -> {
                    HttpServletResponse response = event.getResponse();
                    Map<String,Object> result = new HashMap<>();
                    result.put("msg","登陆成功");
                    result.put("code",5001);
                    response.setContentType("application/json;charset=utf-8");
                    System.out.println("----------->");
                    String s = new ObjectMapper().writeValueAsString(result);
                    response.getWriter().print(s);
                    response.flushBuffer();
                })
                //一旦登录  禁止再次登录
                .maxSessionsPreventsLogin(true);
        //at 用某个 filter 替换过滤器链中哪个filter；
        // before：放在过滤器链中哪个 filter 之前； after： 放在过滤器链中哪个 filter 之后

        //没有权限会默认跳到登录页面，需要开启登录的页面
                //登录页面的路径
                /*.loginProcessingUrl("/login")
                .loginPage("/login.xml")
                .and().csrf().disable();*/
                //.defaultSuccessUrl("")  登录成功后跳转的路径

    }

    @Bean
    public RememberMeServices rememberMeServices(){
        return new MyPersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(),userDetailsService,new InMemoryTokenRepositoryImpl());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }



    /**
     * 用户密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
