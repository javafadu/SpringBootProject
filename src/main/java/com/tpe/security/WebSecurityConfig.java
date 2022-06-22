package com.tpe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // roller var ama hangisine perm verecegiz, method bazinda verebiliriz
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // Basic Authorization : username , password
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeRequests().
                antMatchers("/", "index.html", "/css/*", "/js/*").permitAll().
                // yukardaki security ile girmeyi haric tutuyoruz. Yani buralara direk girilebilir
                        anyRequest().
                authenticated().
                and().
                httpBasic();
    }


    // There is no PasswordEncoder mapped for the id \"null\"
    // password("{noop}bond") yaparsak sifreyi encode etmeden kullanir
    // coffee: $2a$10$3Owja2rXPZW84pFrXp/f2e8SxFLNFsRaTXLV7Kf.nDqo3blPuxT6y

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        //   UserDetails user1 = User.builder().username("james").password("{noop}bond").roles("ADMIN").build();
        UserDetails userJohn = User.builder().username("john").password(passwordEncoder().encode("coffee")).roles("ADMIN").build();
        UserDetails userDarth = User.builder().username("darth").password(passwordEncoder().encode("wader")).roles("STUDENT").build();
        UserDetails userWalt = User.builder().username("walter").password(passwordEncoder().encode("white")).roles("ADMIN", "STUDENT").build();

        return new InMemoryUserDetailsManager(new UserDetails[]{userJohn, userDarth, userWalt});
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}