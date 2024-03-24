package com.francilino.oficina.converter;

import com.francilino.oficina.models.Role;
import com.francilino.oficina.repository.RoleRepository;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author ciaa
 */
public class RoleConverter extends PropertyEditorSupport {

    private final RoleRepository repository;

    public RoleConverter(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setAsText(String text) {
        //transforma a string com o id em um Integer
        Integer id = new Integer(text);
        //recupera no db o profile do id referido
        Role role = repository.findById(id);
        //add o objeto profile, o qual faz parte do objeto user no controller
        super.setValue(role);
    }
}
