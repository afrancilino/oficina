package com.francilino.oficina.converter;

import com.francilino.oficina.models.Produto;
import com.francilino.oficina.repository.ProdutoRepository;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author ciaa
 */
public class ProdutoConverter extends PropertyEditorSupport {

    private final ProdutoRepository repository;

    public ProdutoConverter(ProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setAsText(String text) {
        //transforma a string com o id em um Integer
        Integer id = new Integer(text);
        //recupera no db o profile do id referido
        Produto produto = repository.findById(id);
        //add o objeto profile, o qual faz parte do objeto user no controller
        super.setValue(produto);
    }
}
