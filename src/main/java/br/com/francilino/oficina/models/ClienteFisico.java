package br.com.francilino.oficina.models;

import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class ClienteFisico extends Cliente {

    @NotEmpty
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
