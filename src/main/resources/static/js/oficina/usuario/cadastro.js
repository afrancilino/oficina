$(document).ready(function () {

    //SOMENTE NÚMEROS
    $('#cpf').keypress(function (e) {
        if (e.which > 47 && e.which < 91)
            return true;
        else if (e.which == 8 || e.which == 0)
            return true;
        else
            return false;
        });

    $('#senha').change(function(){
        if($('#senha').length < 8){
            alert("A senha deve possuir ao menos DEZ caracteres.");
            $('#senha').val("");
            return;
        }
    });

    $('#confirmarSenha').change(function(){
        if($('#confirmarSenha').val() != $('#senha').val()){
            $('#confirmarSenha').val("");
            $('#senha').val("");

            return alert("Senhas Diferentes");
        }
        $('#senha').val(sha256($('#senha').val()));
        $('#confirmarSenha').val($('#senha').val());
    });

    //ENVIAR OS DADOS E DEPOIS RESGATAR PARA INSERIR JUNTO COM O MEMBRO
    $('#enviarUsuario').click(function (event) {
        if($('#selectMembro').val() == "0"){
            event.preventDefault();
            alert("Escolha um Membro.");
        } else if($('#selectRole').val() == "0"){
            event.preventDefault();
            alert("Escolha uma Fun\u00e7\u00e3o.");
        } else if($('#cpf').val() === ""){
            event.preventDefault();
            alert("Digite o CPF do Usu\u00e1rio.");
        } else if($('#nomeGuerra').val() === ""){
            event.preventDefault();
            alert("Digite o Nome de Guerra do Usu\u00e1rio.");
        } else if($('#selectStatus').val() == "0"){
            event.preventDefault();
            alert("Escolha o Status do Usu\u00e1rio.");
        } else if($('#senha').val() === ""){
            event.preventDefault();
            alert("Digite uma senha.");
        } else if($('#confirmarSenha').val() === ""){
            event.preventDefault();
            alert("Confirme a sua senha.");
        } else {
            var num1 = $('#cpf').unmask();
            $('#cpf').val(num1.val());
        }
    });
});