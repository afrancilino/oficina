package br.com.francilino.oficina.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public abstract class Cliente extends Pessoa {

    @OneToMany
    private List<Produto> produto;

    @OneToMany
    private List<OrdemServico> ordemServico;

    public List<Produto> getProduto() {
        return produto;
    }

    public void setProduto(List<Produto> produto) {
        this.produto = produto;
    }

    public List<OrdemServico> getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(List<OrdemServico> ordemServico) {
        this.ordemServico = ordemServico;
    }

}
