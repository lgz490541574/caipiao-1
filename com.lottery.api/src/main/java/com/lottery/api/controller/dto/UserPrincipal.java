package com.lottery.api.controller.dto;

import com.passport.rpc.dto.UserDTO;

import java.security.Principal;

public class UserPrincipal implements Principal {
    private UserDTO userDTO;

    @Override
    public String getName() {
        if(userDTO==null){
            return null;
        }
        return userDTO.getPin();
    }

    public UserPrincipal() {

    }

    public UserPrincipal(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
