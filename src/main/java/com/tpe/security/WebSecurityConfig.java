package com.tpe.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeRequests().
                antMatchers("/","index.html","/css/*","/js/*","/images/*").permitAll().
                anyRequest().
                authenticated().
                and().
                httpBasic(); // basic authetentication diyelim
    }

    //There is no PasswordEncoder mapped for the id "null
    //$2a$10$w0mm2OgUL3XIgrJ69EaqBO8PpHWyohJaZgp0nMtzHX.s1ddL7K.1q

    /*
     *
     --------------------INMEMORYUSERDETAILS------------------------------
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
//      UserDetails user1=User.builder().username("james").
//              password("{noop}bond").roles("ADMIN").build();

        UserDetails userJohn=User.builder().username("john").
                password(passwordEncoder().encode("coffee")).roles("ADMIN").build();

        UserDetails userDarth=User.builder().username("darth").
                password(passwordEncoder().encode("vader")).roles("STUDENT").build();

        UserDetails userWalt=User.builder().username("walter").
                password(passwordEncoder().encode("white")).roles("ADMIN","STUDENT").build();

        return new InMemoryUserDetailsManager(new UserDetails[] {userJohn,userDarth,userWalt});
    }
    ------------------- INMEMORYUSERDETAILS-------------------------------
    */



    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
        // user DB da var mi diye kontrol ediyor. ->UserDetailsService -> UserRepository-> DB
        // password encode edip dogry mu diye kontrol ediyor -> PasswordEncoder
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
        // Filter dan gecen request buraya geliyor.
        // authenticationProvider a gonderiyor.
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }



}