package com.francilino.oficina.converter;

import com.francilino.oficina.models.ClienteJuridico;
import com.francilino.oficina.repository.ClienteJuridicoRepository;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author ciaa
 */
public class ClienteJuridicoConverter extends PropertyEditorSupport {

    private final ClienteJuridicoRepository repository;

    public ClienteJuridicoConverter(ClienteJuridicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setAsText(String text) {
        //transforma a string com o id em um Integer
        Integer id = new Integer(text);
        //recupera no db o profile do id referido
        ClienteJuridico clienteJuridico = repository.findById(id);
        //add o objeto profile, o qual faz parte do objeto user no controller
        super.setValue(clienteJuridico);
    }
}
