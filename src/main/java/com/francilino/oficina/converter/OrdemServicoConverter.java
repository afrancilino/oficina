package com.francilino.oficina.converter;

import com.francilino.oficina.models.OrdemServico;
import com.francilino.oficina.repository.OrdemServicoRepository;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author ciaa
 */
public class OrdemServicoConverter extends PropertyEditorSupport {

    private final OrdemServicoRepository repository;

    public OrdemServicoConverter(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setAsText(String text) {
        //transforma a string com o id em um Integer
        Integer id = new Integer(text);
        //recupera no db o profile do id referido
        OrdemServico ordemServico = repository.findById(id);
        //add o objeto profile, o qual faz parte do objeto user no controller
        super.setValue(ordemServico);
    }
}
