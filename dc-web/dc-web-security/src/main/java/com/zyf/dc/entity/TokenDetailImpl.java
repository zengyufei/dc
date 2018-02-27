package com.zyf.dc.entity;

import com.zyf.dc.entity.sys.TokenDetail;

/**
 * @version V1.0.0
 */
public class TokenDetailImpl implements TokenDetail {

    private final String username;

    public TokenDetailImpl(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
