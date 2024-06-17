package com.profileeM.profileeM.admin;

import com.profileeM.profileeM.config.JasyptConfig;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final JasyptConfig jasyptStringEncryptor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (jasyptStringEncryptor.stringEncryptor().decrypt("Yaqhy0K4X+6tCqZFdwzzmfECE1NPMl1p").equals(username)) {
            return User.withUsername(username)
                    .password(passwordEncoder.encode(jasyptStringEncryptor.stringEncryptor().decrypt("CVndGYEOZpqC1HsCo8ABtM2vxfNXfvkM")))
                    .roles("ADMIN")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
