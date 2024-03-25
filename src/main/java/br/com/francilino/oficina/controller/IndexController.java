package br.com.francilino.oficina.controller;

import br.com.francilino.oficina.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    //PÁGINA PRINCIPAL
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            model.addAttribute("usuario", funcionarioRepository.findByNomeUsuario(SecurityContextHolder.getContext().getAuthentication().getName()).getNomeUsuario());
        }
        return "index";
    }

    //PÁGINA DE ERRO PERSONALIZADA
    @RequestMapping(value = "erro")
    public String erro() {
        return "erro";
    }

    //PÁGINA DE PERMISSÃO NEGADA
    @RequestMapping(value = "negado")
    public String acessoNegado(SecurityException e) {
        return "negado";
    }

    //PÁGINA DE CONSTRUÇÃO
    @RequestMapping(value = "construcao")
    public String construcao() {
        return "construcao";
    }
}
