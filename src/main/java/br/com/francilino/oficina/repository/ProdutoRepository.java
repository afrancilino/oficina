package br.com.francilino.oficina.repository;

import br.com.francilino.oficina.models.Produto;
import org.springframework.data.repository.CrudRepository;

public interface ProdutoRepository extends CrudRepository<Produto, String> {

    Produto findById(Integer id);
}
