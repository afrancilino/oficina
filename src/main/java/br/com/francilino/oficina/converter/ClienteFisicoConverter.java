package br.com.francilino.oficina.converter;

import br.com.francilino.oficina.models.ClienteFisico;
import br.com.francilino.oficina.repository.ClienteFisicoRepository;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author ciaa
 */
public class ClienteFisicoConverter extends PropertyEditorSupport {

    private final ClienteFisicoRepository repository;

    public ClienteFisicoConverter(ClienteFisicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setAsText(String text) {
        //transforma a string com o id em um Integer
        Integer id = new Integer(text);
        //recupera no db o profile do id referido
        ClienteFisico clienteFisico = repository.findById(id);
        //add o objeto profile, o qual faz parte do objeto user no controller
        super.setValue(clienteFisico);
    }
}
