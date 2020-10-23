package com.PPROHORAK.Projekt.securingweb;



import com.PPROHORAK.Projekt.DAO.UctyDao;
import com.PPROHORAK.Projekt.Model.Ucet;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserPrincipalDetailImp implements UserDetailsService {
    private final UctyDao seznamUcty;

    public UserPrincipalDetailImp(UctyDao seznamUcty) {
        this.seznamUcty = seznamUcty;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {


        Ucet ucet = seznamUcty.findByLogin(login);
        UserPrincipal userPrincipal = new UserPrincipal(ucet);

        return userPrincipal;
    }


}
