package br.com.francilino.oficina.repository;

import br.com.francilino.oficina.models.Funcionario;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String> {

    Funcionario findByNomeUsuario(String nomeUsuario);

    Funcionario findById(Integer id);
}
