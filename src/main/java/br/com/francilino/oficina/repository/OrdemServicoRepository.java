package br.com.francilino.oficina.repository;

import br.com.francilino.oficina.models.OrdemServico;
import org.springframework.data.repository.CrudRepository;

public interface OrdemServicoRepository extends CrudRepository<OrdemServico, String> {

    OrdemServico findById(Integer id);
}
