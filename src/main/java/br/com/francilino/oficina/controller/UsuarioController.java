package br.com.francilino.oficina.controller;

import br.com.francilino.oficina.converter.ClienteFisicoConverter;
import br.com.francilino.oficina.models.Role;
import br.com.francilino.oficina.models.Cliente;
import br.com.francilino.oficina.repository.RoleRepository;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.francilino.oficina.repository.FuncionarioRepository;

@Controller
public class UsuarioController {
    
    @Autowired
    FuncionarioRepository usuarioRepository;
    @Autowired
    RoleRepository roleRepository;
    /*
    //INJETANDO CONVERSOR
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Membro.class, new ClienteFisicoConverter(membroRepository));
    }

    //PÁGINA DE CADASTRA USUARIO
    @RequestMapping(value = "cadastraUsuario", method = RequestMethod.GET)
    public String cadastra(Model model) {
        model.addAttribute("membro",membroRepository.findAll());
        model.addAttribute("role",roleRepository.findAllWithoutAdmin());
        model.addAttribute("status",EnumStatus.values());
        return "usuario/cadastra";
    }

    //PÁGINA DE CADASTRANDO USUARIO
    @RequestMapping(value = "cadastraUsuario", method = RequestMethod.POST)
    public String cadastrando(Cliente usuario, BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            System.out.println("Erro: " + result.getFieldError());
            System.out.println("\n\n");
            result.getFieldErrors().forEach((erros) -> {
                System.out.println("Erros: " + erros.getField());
            });
            System.out.println("errei\n\n");
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "erro";
        }

        try {
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
            usuarioRepository.save(usuario);

            //ATUALIZA MEMBRO
            usuario.getMembro().setUsuario(usuario);
            membroRepository.save(usuario.getMembro());
            
            //ATUALIZA ROLE
            Role role = roleRepository.findByFuncao(usuario.getRole().getFuncao());
                role.getUsuarios().add(usuario);
                roleRepository.save(role);

        } catch (Exception e) {
            System.out.println("\n\n" + e.getLocalizedMessage() + "\n\n");
        }
        model.addAttribute("nome", "USUÁRIO CADASTRADO");
        model.addAttribute("objeto", "CADASTRAR USUÁRIO");
        model.addAttribute("acao", "COM SUCESSO");
        return "resposta";
    }

    //PÁGINA DE CADASTRA USUARIO
    @RequestMapping(value = "consultaUsuario")
    public String consulta() {
        return "usuario/consulta";
    }

    //PÁGINA DE CADASTRA USUARIO
    @RequestMapping(value = "alteraUsuario")
    public String altera() {
        return "usuario/altera";
    }

    //PÁGINA DE CADASTRA USUARIO
    @RequestMapping(value = "alteraSenha")
    public String alteraSenha() {
        return "usuario/alteraSenha";
    }

    //PÁGINA DE CADASTRA USUARIO
    @RequestMapping(value = "relatorioUsuario")
    public String relatorio() {
        return "usuario/relatorio";
    }

    //PÁGINA DE CADASTRA USUARIO
    @RequestMapping(value = "deletaUsuario")
    public void deleta() {
    }
*/}
