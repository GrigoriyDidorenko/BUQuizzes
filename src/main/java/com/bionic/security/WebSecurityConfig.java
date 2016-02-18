
        package com.bionic.security;

        import com.bionic.security.CustomUserDetailService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.autoconfigure.security.SecurityProperties;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.core.annotation.Order;
        import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * Created by Sergiy on 24.11.2015.
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private CustomAuthenticationHandler customAuthenticationHandler;
    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailService)
                .passwordEncoder(getMd5PasswordEncoder());

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .authorizeRequests();

        http.formLogin()
                .loginPage("/pages/LoginPage.html").successHandler(customAuthenticationHandler)
                .usernameParameter("email").passwordParameter("password")
                .failureUrl("/login?error")
                .permitAll();


        http.logout()
// разрешаем делать логаут всем
                .permitAll()
// указываем URL логаута
                .logoutUrl("/logout")
// указываем URL при удачном логауте
                .logoutSuccessUrl("/pages/LoginPage.html")
// делаем не валидной текущую сессию
                .invalidateHttpSession(true);

        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/pages/openTests/**").permitAll()
                .antMatchers("/pages/addUser.html").access("hasAnyRole('SUPERADMIN, TRAINER')")
                .antMatchers("/pages/addTest.html").access("hasRole('TRAINER')")
                .antMatchers("/pages/addTestToUser.html").access("hasRole('TRAINER')")
                .antMatchers("/pages/allOpenQuestions.html").access("hasRole('TRAINER')")
                .antMatchers("/pages/auditOpenQuestion.html").access("hasRole('TRAINER')")
                .antMatchers("/pages/editorForTest.html").access("hasRole('TRAINER')")
                .antMatchers("/pages/editTest.html").access("hasRole('TRAINER')")
                .antMatchers("/pages/importTest.html").access("hasRole('TRAINER')")
                .antMatchers("/pages/mentorPage.html").access("hasRole('TRAINER')")
                .antMatchers("/pages/TestPage.html").access("hasAnyRole('STUDENT','TRAINER')")
                .antMatchers("/pages/userPage.html").access("hasAnyRole('STUDENT','TRAINER')")
                .and()
                .exceptionHandling().accessDeniedPage("/pages/errorPage.html");



    }

    @Bean
    public Md5PasswordEncoder getMd5PasswordEncoder(){
        return new Md5PasswordEncoder();
    }


}