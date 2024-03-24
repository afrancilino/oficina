package com.francilino.oficina.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Funcionario extends Pessoa implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String nomeUsuario;

    @NotEmpty
    private String senha;

    @ManyToOne
    private Role role;

    @OneToMany
    private List<OrdemServico> ordemServico;

    public void incluirOrdemServico() {

    }

    public void atualizarOrdemServico() {

    }

    public void encerrarOrdemServico() {

    }

    public void incluirCliente() {

    }

    public void atualizarCliente() {

    }

    public void incluirProduto() {

    }

    public void atualizarProduto() {

    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<OrdemServico> getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(List<OrdemServico> ordemServico) {
        this.ordemServico = ordemServico;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> getAuthorities = new ArrayList<>();
        getAuthorities.add(this.role);
        return (Collection<? extends GrantedAuthority>) getAuthorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.senha;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.nomeUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
}
