package com.francilino.oficina.controller;

import com.francilino.oficina.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Controller;
import com.francilino.oficina.repository.FuncionarioRepository;

@Controller
public class LoginController implements ApplicationListener {

    //INJEÇÃO DE DEPENDÊNCIA
    @Autowired
    FuncionarioRepository funcionarioRepository;

    Funcionario funcionario = null;

    //VFERIFICANDO SE O USUÁRIO EXISTE OU NÃO A PARTIR DO APPLICATION EVENT
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AbstractAuthenticationFailureEvent) {
            funcionario = funcionarioRepository.findByNomeUsuario(((AbstractAuthenticationFailureEvent) event).getAuthentication().getName());
        }
    }
/*
    //CONFIGURANDO A PÁGINA DE LOGIN
    @RequestMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {

        //VERIFICA SE HOUVE ERRO NO LOGIN
        if (error != null) {
            //SE HOUVE ERRO, VERIFICA SE O USUÁRIO EXISTE
            if (usuario != null) {
                //SE EXISTE, INCREMENTA O CAMPO LOGIN
                usuario.setQtdLogin(usuario.getQtdLogin() + 1);

                //SE O CAMPO LOGIN FOR MAIOR OU IGUAL A 3, O USUÁRIO SERÁ BLOQUEADO
                if (usuario.getQtdLogin() >= 3) {
                    //CAMPO LOGIN E STATUS ALTERADOS
                    usuario.setStatus(EnumStatus.BLOQUEADO);
                    //usuarioService.updateUsuario(usuario);

                    Log.gravarLogInstrucao("Nip: " + usuario.getCpf()
                            + "; Nome Guerra: " + usuario.getNomeGuerra()
                            + "; Tentativa login: " + usuario.getQtdLogin()
                            + ". USUÁRIO BLOQUEADO por excesso de tentativas de LOGIN sem sucesso.", this);

                    model.addAttribute("nome", "<br><br>BLOQUEADO!");
                    model.addAttribute("acao", "<br><br>Por excesso de tentativas sem sucesso.");

                    return "resposta";
                }
                
                //SE FOR MENOR QUE 3, ATUALIZA APENAS O CAMPO LOGIN
                //usuarioService.updateUsuario(usuario);

                Log.gravarLogInstrucao("ID: " + usuario.getCpf()
                        + "; CPF: " + usuario.getNomeGuerra()
                        + "; Tentativa login: " + usuario.getQtdLogin()
                        + ". mal sucedido. SENHA INVÁLIDA.", this);

                model.addAttribute("error", "Usu&aacute;rio ou Senha Inv&aacute;lido");
                return "login";

            } else {

                //CASO NÃO EXISTA, SERÁ EMITIDO APENAS UM LOG E UMA MENSAGEM
                Log.gravarLogInstrucao("Tentativa de LOGIN usando o CPF: "
                        + SecurityContextHolder.getContext().getAuthentication().getName()
                        + ". USUÁRIO INVÁLIDO", this);

                model.addAttribute("error", "Usu&aacute;rio ou Senha Inv&aacute;lido");

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
        usuario = ur.findByCpf(SecurityContextHolder.getContext().getAuthentication().getName());

        //STATUS BLOQUEADO
        if (usuario.getStatus() == EnumStatus.BLOQUEADO
                && !usuario.getExcluido()) {

            model.addAttribute("objeto", "Usu&aacute;rio ");
            model.addAttribute("nome", usuario.getNomeGuerra());
            model.addAttribute("acao", ".<br><br>Seu usu&aacute;rio encontra-se BLOQUEADO "
                    + "por excesso de tentativas de LOGIN sem sucesso.<br><br>Entre em contato com o Admin.");

            Log.gravarLogInstrucao("ID: " + usuario.getCpf()
                    + "; CPF: " + usuario.getNomeGuerra()
                    + "; Logon NÃO PERMITIDO."
                    + "; Motivo: BLOQUEADO.", this);

            return "resposta";

        } //STATUS INATIVO.
        else if (usuario.getStatus() == EnumStatus.INATIVO
                && usuario.getPrimeiroAcesso().equals(Boolean.FALSE)
                && !usuario.getExcluido()) {

            usuario.setQtdLogin(0);
            usuario.setStatus(EnumStatus.ATIVO);
            //usuarioService.updateUsuario(usuario);
            session.setAttribute("nomeGuerra", usuario.getNomeGuerra());

            Log.gravarLogInstrucao("ID: " + usuario.getCpf()
                    + "; CPF: " + usuario.getNomeGuerra()
                    + "; Logon permitido."
                    + "; Observação: USUÁRIO REATIVADO.", this);

            return "index";

        } //STATUS INATIVO e PRIMEIRO ACESSO.
        else if (usuario.getStatus() == EnumStatus.INATIVO
                && usuario.getPrimeiroAcesso().equals(Boolean.TRUE)
                && !usuario.getExcluido()) {

            usuario.setQtdLogin(0);
            usuario.setStatus(EnumStatus.ATIVO);
            //usuarioService.updateUsuario(usuario);
            session.setAttribute("nomeGuerra", usuario.getNomeGuerra());
            model.addAttribute("NIP", usuario.getCpf());

            Log.gravarLogInstrucao("ID: " + usuario.getCpf()
                    + "; CPF: " + usuario.getNomeGuerra()
                    + "; Logon permitido."
                    + "; Observação: USUÁRIO REATIVADO"
                    + "; PRIMEIRO ACESSO.", this);

            return "usuario/alterarSenha";

        } //STATUS PRIMEIRO ACESSO.
        else if (usuario.getPrimeiroAcesso().equals(Boolean.TRUE)
                && !usuario.getExcluido()) {

            usuario.setQtdLogin(0);
            //usuarioService.updateUsuario(usuario);
            session.setAttribute("nomeGuerra", usuario.getNomeGuerra());
            model.addAttribute("NIP", usuario.getCpf());

            Log.gravarLogInstrucao("ID: " + usuario.getCpf()
                    + "; CPF: " + usuario.getNomeGuerra()
                    + "; Logon permitido."
                    + "; Observação: PRIMEIRO ACESSO.", this);

            return "usuario/alterarSenha";

        } //STATUS EXLUÍDO
        else if (usuario.getExcluido()) {

            Log.gravarLogInstrucao("ID: " + usuario.getCpf()
                    + "; CPF: " + usuario.getNomeGuerra()
                    + "; Logon não permitido."
                    + "; Observação: USUÁRIO EXCLUÍDO.", this);

            model.addAttribute("objeto", "Usu&aacute;rio ");
            model.addAttribute("nome", usuario.getNomeGuerra());
            model.addAttribute("acao", ".<br><br>Seu usu&aacute;rio encontra-se EXCLU&Iacute;DO."
                    + "<br><br>Entre em contato com o Admin.");

            return "resposta";

        } else {
            //EXECUÇÃO NORMAL
            usuario.setQtdLogin(0);
            //usuarioService.updateUsuario(usuario);

            session.setAttribute("nomeGuerra", usuario.getNomeGuerra());

            Log.gravarLogInstrucao("ID: " + usuario.getCpf()
                    + "; CPF: " + usuario.getNomeGuerra()
                    + "; Logon permitido.", this);

            return "index";
        }
    }
*/}
