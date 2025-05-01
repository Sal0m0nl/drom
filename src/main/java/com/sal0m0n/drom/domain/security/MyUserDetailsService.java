package com.sal0m0n.drom.domain.security;

import com.sal0m0n.drom.web.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private DBService dbService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return dbService.findUserByUsername(username)
                .map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

    }

}
