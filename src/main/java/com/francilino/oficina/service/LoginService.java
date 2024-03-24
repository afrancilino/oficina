package com.francilino.oficina.service;

import com.francilino.oficina.models.Funcionario;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import com.francilino.oficina.repository.FuncionarioRepository;

@Repository
@Transactional
public class LoginService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository fr;

    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
        Funcionario funcionario = fr.findByNomeUsuario(nomeUsuario);
        if (funcionario == null) {
            throw new UsernameNotFoundException("Funcionário não encontrado!");
        }
        return new User(funcionario.getUsername(), funcionario.getPassword(), true, true, true, true, funcionario.getAuthorities());
    }

}
