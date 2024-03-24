package com.francilino.oficina.repository;

import com.francilino.oficina.models.ClienteFisico;
import org.springframework.data.repository.CrudRepository;

public interface ClienteFisicoRepository extends CrudRepository<ClienteFisico, String> {

    ClienteFisico findByCpf(String cpf);

    ClienteFisico findById(Integer id);
}
