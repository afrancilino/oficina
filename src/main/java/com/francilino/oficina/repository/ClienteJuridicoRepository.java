package com.francilino.oficina.repository;

import com.francilino.oficina.models.ClienteJuridico;
import org.springframework.data.repository.CrudRepository;

public interface ClienteJuridicoRepository extends CrudRepository<ClienteJuridico, String> {

    ClienteJuridico findByCnpj(String cnpj);

    ClienteJuridico findById(Integer id);
}
