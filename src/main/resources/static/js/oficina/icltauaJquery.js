/*//FORMATADOR DE DATAS
function formatarData(e) {
    var mes = e.substring(0, 3);
    var ano = e.substring(e.length - 4, e.length);
    var dia = e.substring(4, e.length - 6);
    dia = dia.concat("/", mes, "/", ano);
    return dia;
}
*/
$(document).ready(function () {
/*
    $('#logar').click(function(){
        $('#senha').val(sha256($('#senha').val()+$('#cpf').val()));
    });
*/
    $(document).keypress(function(e) {
        if (e.which == 13) {
             $('#logar').trigger('click'); 
        }
    });
    
//------------ESCONDER CONTEÚDOS--------------------
    $('.esconder').hide();

//------------ATIVAÇÃO DO SELCT----------------------
    $('select').material_select();

//------------ATIVAÇÃO DO MODAL-------------------
    $('.modal').modal();

//------------MÁSCARAS-----------
    
    //CPF
    $('#cpf').mask('000.000.000-00', {reverse: true});

    //matricula
    $('#matricula').mask('000.000');

     //TELEFONE
    $("#telefoneResidencial").mask('(00) 0000-0000');
    $("#telefoneCelular").mask('(00) 00000-0000');

//------------CALENDÁRIO--------------------
/*
    //FORMATAÇÃO DE DATA
    function dataFormatada(data) {
        var partesData = data.split("/");
        return new Date(partesData[2], partesData[1] - 1, partesData[0]);
    }
*/
    //ATIVAÇÃO E MONTAGEM DO CALENDÁRIO
    $('.datepicker').pickadate({

        monthsFull: ['janeiro', 'fevereiro', 'mar&ccedil;o', 'abril', 'maio', 'junho', 'julho', 'agosto', 'setembro', 'outubro', 'novembro', 'dezembro'],
        monthsShort: ['jan', 'fev', 'mar', 'abr', 'mai', 'jun', 'jul', 'ago', 'set', 'out', 'nov', 'dez'],
        weekdaysFull: ['domingo', 'segunda-feira', 'ter&ccedil;a-feira', 'quarta-feira', 'quinta-feira', 'sexta-feira', 's&aacute;bado'],
        weekdaysShort: ['dom', 'seg', 'ter', 'qua', 'qui', 'sex', 'sab'],
        weekdaysLetter: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'],


        today: 'hoje',
        clear: 'limpar',
        close: 'ok',

        format: 'dd/mm/yyyy',

        selectMonths: true, // Creates a dropdown to control month
        selectYears: 100, // Creates a dropdown of 15 years to control year,
        max: true,

        closeOnSelect: true // Close upon selecting a date,
    });

//-------------EDITAR--------------------
    $('#edita').click(function () {
        console.log($('#editar').val());
        var alvo = location.href.split("consulta");
        var url = 'altera' + alvo[1] + '?id=' + $('#editar').val();
        console.log(url);
        $(location).attr("href", url);
    });

//-------------EXCLUIR----------------------
    $('#exclui').click(function () {

        var r=confirm("Este dado ser\u00e1 apagado definitivamente. Deseja realmente prosseguir?");

        if(r==true){    
            var alvo = location.href.split("consulta");
            var url = 'remove' + alvo[1] + '?id=' + $('#excluir').val();
            
            $.ajax({
                type: "GET",
                url: url,
                statusCode: {
                    200: function () {
                        alert("Dado exclu\u00eddo com sucesso!");
                        window.setTimeout("location.reload()", 60);
                    },

                    203: function () {
                        alert("Este dado n\u00e3o p\u00f4de ser exclu\u00eddo!\nVerifique se n\u00e3o h\u00e1 v\u00ednculos para ele.");
                        window.setTimeout("location.reload()", 60);
                    }
                },
            });
        }
        
        else{
            
        }
    });
    
//------------TESTA CPF---------------------------

    $('#cpf').change(function(){

        var cpf = $('#cpf').val();
        cpf = cpf.replace(/[^\d]+/g, '');

        if (cpf == '')
        
            alert("CPF inv\u00e1lido");

        else if (cpf.length != 11 ||
            cpf == "00000000000" ||
            cpf == "11111111111" ||
            cpf == "22222222222" ||
            cpf == "33333333333" ||
            cpf == "44444444444" ||
            cpf == "55555555555" ||
            cpf == "66666666666" ||
            cpf == "77777777777" ||
            cpf == "88888888888" ||
            cpf == "99999999999")

            alert("CPF inv\u00e1lido");
        
        else{

            add = 0;
            for (i = 0; i < 9; i++)
                add += parseInt(cpf.charAt(i)) * (10 - i);

            rev = 11 - (add % 11);

            if (rev == 10 || rev == 11)
                rev = 0;

            if (rev != parseInt(cpf.charAt(9)))
                alert("CPF inv\u00e1lido");

            add = 0;
            for (i = 0; i < 10; i++)                    
                add += parseInt(cpf.charAt(i)) * (11 - i);
            
            rev = 11 - (add % 11);
            
            if (rev == 10 || rev == 11)                
                rev = 0;
            
            if (rev != parseInt(cpf.charAt(10)))
                alert("CPF inv\u00e1lido");            
        }
    });
});