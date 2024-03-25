package br.com.francilino.oficina.repository;

import br.com.francilino.oficina.models.Role;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {

    @Query(value = "SELECT * FROM role o WHERE o.funcao != 'ROLE_ADMIN' ORDER BY o.funcao ASC", nativeQuery = true)
    List<Role> findAllWithoutAdmin();

    Role findByFuncao(String funcao);
    Role findById(Integer id);
}
