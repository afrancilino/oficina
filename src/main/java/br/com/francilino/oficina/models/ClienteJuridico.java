package br.com.francilino.oficina.models;

import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class ClienteJuridico extends Cliente {

    @NotEmpty
    private String cnpj;

    @NotEmpty
    private String nomeEmpresa;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }
}
