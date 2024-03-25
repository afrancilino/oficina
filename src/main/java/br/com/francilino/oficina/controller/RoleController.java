package br.com.francilino.oficina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleController {

    //PÁGINA DE CADASTRA ROLE
    @RequestMapping(value = "cadastraRole")
    public String cadastra() {
        return "role/cadastra";
    }

    //PÁGINA DE CADASTRA ROLE
    @RequestMapping(value = "consultaRole")
    public String consulta() {
        return "role/consulta";
    }

    //PÁGINA DE CADASTRA ROLE
    @RequestMapping(value = "alteraRole")
    public String altera() {
        return "role/altera";
    }

    //PÁGINA DE CADASTRA ROLE
    @RequestMapping(value = "relatorioRole")
    public String relatorio() {
        return "role/relatorio";
    }

    //PÁGINA DE CADASTRA ROLE
    @RequestMapping(value = "deletaRole")
    public void deleta() {
    }
    
}
