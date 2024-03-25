package br.com.francilino.oficina.converter;

import br.com.francilino.oficina.models.Funcionario;
import br.com.francilino.oficina.repository.FuncionarioRepository;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author ciaa
 */
public class FuncionarioConverter extends PropertyEditorSupport {

    private final FuncionarioRepository repository;

    public FuncionarioConverter(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setAsText(String text) {
        //transforma a string com o id em um Integer
        Integer id = new Integer(text);
        //recupera no db o profile do id referido
        Funcionario funcionario = repository.findById(id);
        //add o objeto profile, o qual faz parte do objeto user no controller
        super.setValue(funcionario);
    }
}
