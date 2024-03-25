package br.com.francilino.oficina.controller;

import br.com.francilino.oficina.log.Log;
import br.com.francilino.oficina.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Controller;
import br.com.francilino.oficina.repository.FuncionarioRepository;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController implements ApplicationListener {

    //INJEÇÃO DE DEPENDÊNCIA
    @Autowired
    FuncionarioRepository funcionarioRepository;

    Funcionario usuario = null;

    //VFERIFICANDO SE O USUÁRIO EXISTE OU NÃO A PARTIR DO APPLICATION EVENT
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AbstractAuthenticationFailureEvent) {
            usuario = funcionarioRepository.findByNomeUsuario(((AbstractAuthenticationFailureEvent) event).getAuthentication().getName());
        }
    }

    //CONFIGURANDO A PÁGINA DE LOGIN
    @RequestMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {

        //VERIFICA SE HOUVE ERRO NO LOGIN
        if (error != null) {
            //SE HOUVE ERRO, VERIFICA SE O USUÁRIO EXISTE
            if (usuario != null) {
                Log.gravarLogInstrucao("Funcionario: " + usuario.getNome()
                        + ". Tentativa login mal sucedido. SENHA INVÁLIDA.", this);
                model.addAttribute("error", "Usuário ou Senha Inválido");
                return "login";

            } else {
                //CASO NÃO EXISTA, SERÁ EMITIDO APENAS UM LOG E UMA MENSAGEM
                Log.gravarLogInstrucao("Tentativa de LOGIN usando o Nome de Usuário: "
                        + SecurityContextHolder.getContext().getAuthentication().getName()
                        + ". USUÁRIO INVÁLIDO", this);
                model.addAttribute("error", "Usuário ou Senha Inválido");

                return "login";
            }
        }

        //PÁGINA INICIAL DE LOGIN
        return "login";
    }

    //PÁGINA APRESENTADA QUANDO O LOGIN É BEM SUCEDIDO
    @RequestMapping(value = "/logado", method = RequestMethod.GET)
    public String logado(HttpSession session, Model model) {

        //INSTANCIA O USUÁRIO
        usuario = funcionarioRepository.findByNomeUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
        session.setAttribute("nomeUsuario", usuario.getNomeUsuario());
        Log.gravarLogInstrucao("Funcionario: " + usuario.getNome()
                + "; Logon permitido.", this);

        return "index";
    }
}
