package fmi.softech.topkino.services;

import fmi.softech.topkino.exceptions.DaoException;
import fmi.softech.topkino.models.User;
import fmi.softech.topkino.persistence.UserDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAuthService implements AuthenticationProvider {
    @Autowired
    private UserDao userDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = new User();
        user.setUsername(name);
        String hashedPass = DigestUtils.sha256Hex(authentication.getCredentials().toString());
        user.setPassword(hashedPass);
        User foundUser;

        try {
            foundUser = userDao.findUser(user);
        } catch (DaoException e) {
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }

        if(foundUser != null) {
            String permissions = "ROLE_USER";
            if(foundUser.getAdmin()) {
                permissions = "ROLE_ADMIN";
            }

            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permissions);
            List<SimpleGrantedAuthority> newAuthorities  =  new ArrayList<>();
            newAuthorities.add(grantedAuthority);

            return new UsernamePasswordAuthenticationToken(
                    name, password, newAuthorities);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

