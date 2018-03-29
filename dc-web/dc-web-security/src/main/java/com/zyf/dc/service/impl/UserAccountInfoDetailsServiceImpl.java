package com.zyf.dc.service.impl;

import com.zyf.dc.entity.SecurityModelFactory;
import com.zyf.dc.entity.sys.UserAccountInfo;
import com.zyf.dc.mapper.UserAccountInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @version V1.0.0
 */
@Service
public class UserAccountInfoDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAccountInfoMapper userMapper;

    /**
     * 获取 userDetail
     * @param username
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccountInfo user = this.userMapper.getUserFromDatabase(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return SecurityModelFactory.create(user);
        }
    }
}
