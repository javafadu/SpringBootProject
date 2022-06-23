package com.tpe.security.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // kendi UserDetailsService imizi olusturuyoruz ama Spring in UserDetailsServicine implemente etmemiz
    // gerekiyor.

    @Autowired
    UserRepository userRepository;


    @Autowired //Sadece bir constructor var ise Autowired koymaya gerek yok.
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // User class dan bir obje olusturuyoruz,
        User user= userRepository.findByUserName(username).
                orElseThrow(()->new ResourceNotFoundException("User not found username:"+username));


        if(user!=null) {
            return new org.springframework.security.core.userdetails.
                    User(user.getUserName(), user.getPassword(),buildGrantedAuthorities(user.getRoles()));
            // user tablosunda tuttuğum kendi role entity lerim, GrantedAuthority ile
            // Spring yapisina uyumlu hale getiriyoruz.
        }else {
            throw new UsernameNotFoundException("User not found username:"+username);
        }

    }

    private static List<SimpleGrantedAuthority> buildGrantedAuthorities(final Set<Role> roles){
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(Role role:roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }

        return authorities;
    }


}