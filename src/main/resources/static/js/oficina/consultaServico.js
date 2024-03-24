$(document).ready(function () {

//REALIZA A BUSCA PELO SERVIÇO OFERECIDO DE ACORDO COM A OPÇÃO ESCOLHIDA ABAIXO
    function buscarServico(url){
        //$('#carregando').show();
        $.ajax({
            type: "GET",
            url: url,
            contentType: "application/json",
            dataType: "json",
            success: function (dados) {

                if(dados == 0){
                    alert("N\u00e3o existem servi\u00e7os cadastrados no Sistema para este tipo de busca.");
                } else{

                    var servico = "<div class='input-field col s10 offset-s1'>\n<select name='servico' id='escolherServicoOferecido'><br>" +
                        "<option value=''>Escolher Servi&ccedil;o</option>";

                    var option = "";

                    $.each(dados, function (i, obj) {
                        option += '<option value="' + obj.id + '">' + obj.descricaoEspecificacao + '</option>\n';
                        console.log(option);
                    })

                    servico += option;
                    servico += "</select>\n<label for=\"servicoOferecido\">Servi&ccedil;o Oferecido</label>\n</div>";

                    //MONTA A COMBO BOX DOS SERVIÇOS OFERECIDOS
                    $('#servicoOferecido').html("select").hide();
                    $('#servicoOferecido').html(servico).show();

                    $('select').material_select();
                }
            },
            error: function () {
                alert("Ocorreu um erro. \nContate o administrador do Sistema.")
            }
        });
        $('#carregando').hide();
    }
    
//------------VISITANTES CONSULTANDO SERVIÇOS OFERECIDOS-----------------------

    //ESCONDENDO AS OPÇÕES
    $('#carregando').hide();
    $('#escolherFinalidadeServicoOferecido').hide();
    $('#buscaInstrumento').hide();
    $('#buscaLaboratorio').hide();
    $('#buscaNormaEmpregada').hide();
    $('#buscaOrganizacao').hide();
    $('#servicoOferecido').hide();
    $('.esconder').hide();

    //ESCOLHENDO O MODO DE PESQUISA
    $('#consultaServicoVisitante').change(function(){
        //$('#carregando').show();
        if($('#consultaServicoVisitante').val() == 0){
            $('#escolherFinalidadeServicoOferecido').hide();
            $('#buscaInstrumento').hide();
            $('#buscaLaboratorio').hide();
            $('#buscaNormaEmpregada').hide();
            $('#buscaOrganizacao').hide();
            $('#servicoOferecido').hide();
            $('.esconder').hide();
            $('#carregando').hide();
            return;
        }

        $('#buscaInstrumento').hide();
        $('#buscaLaboratorio').hide();
        $('#buscaNormaEmpregada').hide();
        $('#buscaOrganizacao').hide();
        $('#servicoOferecido').hide();
        $('.esconder').hide();

        //MONTADO A COMBO BOX FINALIDADE
        var finalidade=
        '<select name="escolhaServicoOferecido" id="escolhaServicoOferecido">'+
            '<option value="0">Selecione o Servi&ccedil;o</option>'+
            '<option value="1">Calibra&ccedil;&atilde;o</option>'+
            '<option value="2">Ensaio</option>'+
        '</select>'+
        '<label for="escolhaServicoOferecido">Finalidade do Servi&ccedil;o</label>';

        //MOSTRANDO A COMBO BOX
        $('#escolherFinalidadeServicoOferecido').html("select").hide();
        $('#escolherFinalidadeServicoOferecido').html(finalidade).show();
        $('select').material_select();
        
        $('#carregando').hide();
    });

    //MONTARÁ A COMBO BOX DO TIPO DE BUSCA QUE SERÁ REALIZADA
    $('#escolherFinalidadeServicoOferecido').change(function(){
        //$('#carregando').show();
        if($('#escolhaServicoOferecido').val() == 0){
            $('#buscaInstrumento').hide();
            $('#buscaLaboratorio').hide();
            $('#buscaNormaEmpregada').hide();
            $('#buscaOrganizacao').hide();
            $('#servicoOferecido').hide();
            $('.esconder').hide();
            $('#carregando').hide();
            return;
        }

        $('#buscaInstrumento').hide();
        $('#buscaLaboratorio').hide();
        $('#buscaNormaEmpregada').hide();
        $('#buscaOrganizacao').hide();
        $('#servicoOferecido').hide();
        $('.esconder').hide();

        switch($('#consultaServicoVisitante').val()){

            //BUSCA POR SERVIÇOS QUE UTILIZAM UM DETERMINADO INSTRUMENTO
            case '1':
                $.ajax({
                    type: "GET",
                    url: 'buscaInstrumento',
                    contentType: "application/json",
                    dataType: "json",
                    success: function (dados) {

                        if(dados == 0)
                            alert("N\u00e3o existem servi\u00e7os cadastrados com este Instrumento.");
                        else{

                            var servico = "<div class='input-field col s10 offset-s1'>\n<select name='instrumento' id='instrumentoServicoOferecido'><br>" +
                                "<option value='0'>Escolher Instrumento</option>";

                            var option = "";

                            $.each(dados, function (i, obj) {
                                option += '<option value="' + obj.id + '">' + obj.instrumento + '</option>\n';
                                console.log(option);
                            })

                            servico += option;
                            servico += "</select>\n<label for=\"instrumentoServicoOferecido\">Instrumento</label>\n</div>";

                            //MONTA A COMBO BOX DE ESCOLHA DE INSTRUMENTO
                            $('#buscaInstrumento').html("select").hide();
                            $('#buscaInstrumento').html(servico).show();

                            $('select').material_select();
                        }
                    },
                    error: function () {
                        alert("Ocorreu um erro. \nContate o administrador do Sistema.")
                    }
                });
                $('#carregando').hide();
                //FIM DA BUSCA POR INSTRUMENTOS
                break;

//-------------------------------------------------------------------------------------------------------------------------------------------
            //BUSCA POR SERVIÇOS OFERECIDOS POR UM DETERMINADO LABORATÓRIO
            case '2':
                $.ajax({
                    type: "GET",
                    url: 'buscaLaboratorioFinalidade?finalidade='+$('#escolhaServicoOferecido').val(),
                    contentType: "application/json",
                    dataType: "json",
                    success: function (dados) {

                        if(dados == 0)
                            alert("Este Laborat\u00f3rio n\u00e3o possui servi\u00e7o cadastrado.");
                        else{

                            var servico = "<div class='input-field col s10 offset-s1'>\n<select name='laboratorio' id='laboratorioServicoOferecido'><br>" +
                                "<option value='0'>Escolher Laborat&oacute;rio</option>";

                            var option = "";

                            $.each(dados, function (i, obj) {
                                option += '<option value="' + obj.id + '">' + obj.nome + '</option>\n';
                                console.log(option);
                            })

                            servico += option;
                            servico += "</select>\n<label for=\"laboratorioServicoOferecido\">Laborat&oacute;rio</label>\n</div>";

                            //MONTA A COMBO BOX DE ESCOLHA DOS LABORATÓRIOS
                            $('#buscaLaboratorio').html("select").hide();
                            $('#buscaLaboratorio').html(servico).show();

                            $('select').material_select();
                        }
                    },
                    error: function () {
                        alert("Ocorreu um erro. \nContate o administrador do Sistema.")
                    }
                });
                $('#carregando').hide();
                //FIM DA BUSCA POR LABORATÓRIOS
                break;

//-------------------------------------------------------------------------------------------------------------------------------------------
            //BUSCA POR SERVIÇOS QUE ATENDAM UMA DETERMINADA NORMA
            case '3': 
                $.ajax({
                    type: "GET",
                    url: 'buscaNormaAplicavel',
                    contentType: "application/json",
                    dataType: "json",
                    success: function (dados) {

                        if(dados == 0)
                            alert("N\u00e3o existem servi\u00e7os cadastrados com esta Norma.");
                        else{

                            var servico = "<div class='input-field col s10 offset-s1'>\n<select name='norma' id='normaEmpregadaServicoOferecido'><br>" +
                                "<option value='0'>Escolher Norma Empregada</option>";

                            var option = "";

                            $.each(dados, function (i, obj) {
                                option += '<option value="' + obj.id + '">' + obj.normaDocAplicavel + '</option>\n';
                                console.log(option);
                            })

                            servico += option;
                            servico += "</select>\n<label for=\"normaEmpregadaServicoOferecido\">Norma Empregada</label>\n</div>";

                            //MONTA A COMBO BOX DE NORMAS
                            $('#buscaNormaEmpregada').html("select").hide();
                            $('#buscaNormaEmpregada').html(servico).show();

                            $('select').material_select();
                        }
                    },
                    error: function () {
                        alert("Ocorreu um erro. \nContate o administrador do Sistema.")
                    }
                });
                $('#carregando').hide();
                //FIM DA BUSCA POR NORMAS
                break;

//-------------------------------------------------------------------------------------------------------------------------------------------
            //BUSCA POR SERVIÇOS OFERECIDOS POR UMA DETERMINADA ORGANIZAÇÃO
            case '4':
                $.ajax({
                    type: "GET",
                    url: 'buscaOrganizacao',
                    contentType: "application/json",
                    dataType: "json",
                    success: function (dados) {

                        if(dados == 0)
                            alert("N\u00e3o existem servi\u00e7os cadastrados para esta Organiza\u00e7\u00e3o.");
                        else{

                            var servico = "<div class='input-field col s10 offset-s1'>\n<select name='organizacao' id='organizacaoServicoOferecido'><br>" +
                                "<option value='0'>Escolher Organiza&ccedil;&atilde;o</option>";

                            var option = "";

                            $.each(dados, function (i, obj) {
                                option += '<option value="' + obj.id + '">' + obj.sigla + '</option>\n';
                                console.log(option);
                            })

                            servico += option;
                            servico += "</select>\n<label for=\"organizacaoServicoOferecido\">Organiza&ccedil;&atilde;o</label>\n</div>";

                            //MONTA A COMBO BOX DAS ORGANIZAÇÕES
                            $('#buscaOrganizacao').html("select").hide();
                            $('#buscaOrganizacao').html(servico).show();

                            $('select').material_select();
                        }
                    },
                    error: function () {
                        alert("Ocorreu um erro. \nContate o administrador do Sistema.")
                    }
                });
                $('#carregando').hide();
                //FIM BUSCA POR ORGANIZAÇÃO
                break;
                
            case '5': 

                //BUSCA O SERVIÇO OFERECIDO
                url= 'buscaServicoOferecido?servicoOferecido=' + $('#escolhaServicoOferecido').val();
                buscarServico(url);
                $('#carregando').hide();
                //FIM BUSCA POR SERVIÇO OFERECIDO
                break;//Serviço Oferecido

            default:
                $('#carregando').hide();
                //SE NÃO ESCOLHER OPÇÃO ALGUMA, PEDIRÁ PARA ESCOLHER
                return alert("Escolha uma op\u00e7\u00e3o");
        }
    });

//------------------------------------------------------------------------------------------------------------------------------------------
    //AO ESCOLHER O INSTRUMENTO, BUSCARÁ OS SERVIÇOS QUE UTILIZAM ESTE INSTRUMENTO
    $('#buscaInstrumento').change(function(){
        //$('#carregando').show();
        $('#servicoOferecido').hide();
        $('.esconder').hide();
        
        //BUSCA O SERVIÇO OFERECIDO
        if($('#instrumentoServicoOferecido').val() != 0){
            var url = 'servicoOferecidoInstrumento?instrumento=' + $('#instrumentoServicoOferecido').val() + '&servicoOferecido=' + $('#escolhaServicoOferecido').val();
            buscarServico(url);
        }
    });

//------------------------------------------------------------------------------------------------------------------------------------------    
    //AO ESCOLHER O LABORATÓRIO, BUSCARÁ OS SERVIÇOS OFERECIDOS POR ESTE LABORATÓRIO
    $('#buscaLaboratorio').change(function(){
        //$('#carregando').show();
        $('#servicoOferecido').hide();
        $('.esconder').hide();
            
        //BUSCA OS SERVIÇOS OFERECIDOS
        if($('#laboratorioServicoOferecido').val() != 0){
            var url = 'servicoOferecidoLaboratorio?laboratorio=' + $('#laboratorioServicoOferecido').val() + '&servicoOferecido=' + $('#escolhaServicoOferecido').val();
            buscarServico(url);
        }
    });

//------------------------------------------------------------------------------------------------------------------------------------------    
    //AO ESCOLHER A NORMA EMPREGADA, BUSCARÁ OS SERVIÇOS QUE CUMPREM A NORMA EMPREGADA
    $('#buscaNormaEmpregada').change(function(){
        //$('#carregando').show();
        $('#servicoOferecido').hide();
        $('.esconder').hide();
        
        //BUSCA OS SERVIÇOS OFERECIDOS
        if($('#normaEmpregadaServicoOferecido').val() != 0){
            var url = 'servicoOferecidoNormaEmpregada?normaEmpregada=' + $('#normaEmpregadaServicoOferecido').val() + '&servicoOferecido=' + $('#escolhaServicoOferecido').val();
            buscarServico(url);
        }
    });

//------------------------------------------------------------------------------------------------------------------------------------------    
    //AO ESCOLHER A ORGANIZAÇÃO, BUSCARÁ OS SERVIÇOS OFERECIDOS POR ESTA ORGANIZAÇÃO
    $('#buscaOrganizacao').change(function(){
        //$('#carregando').show();
        $('#servicoOferecido').hide();
        $('.esconder').hide();

        //BUSCA OS SERVIÇOS OFERECIDOS
        if($('#organizacaoServicoOferecido').val() != 0){
            var url = 'servicoOferecidoOrganizacao?organizacao=' + $('#organizacaoServicoOferecido').val() + '&servicoOferecido=' + $('#escolhaServicoOferecido').val();
            buscarServico(url);
        }
    });

//------------------------------------------------------------------------------------------------------------------------------------------    
//APRESENTAÇÃO DOS DADOS DO SERVIÇO OFERECIDO ESCOLHIDO
    $('#servicoOferecido').change(function (){
        //$('#carregando').show();
        $('.esconder').hide();
        $('.servicoCalibracao').hide();
        $('.servicoEnsaio').hide();

        $.ajax({
            type: "GET",
            url: 'dadosServicoOferecido?id=' + $('#escolherServicoOferecido').val()
                + '&servicoOferecido=' + $('#escolhaServicoOferecido').val(),
            contentType: "application/json",
            dataType: "json",
            success: function (dados) {

                //RELACIONAMENTOS COMUNS
                $('#grupo').val(dados.grupo.descricao);
                $('#subGrupo').val(dados.subGrupo.descricao);

                // INSTRUMENTOS
                var optionInstrumentos = "<select name='instrumentos' id='instrumentosVisitante'>\n" +
                    "<option value='' selected>Instrumentos Utilizados</option>";

                    $.each(dados.validadeInstrumento, function (i, obj) {
                        optionInstrumentos += '<option value="' + i + '">' + obj.instrumento.instrumento + '</option>\n';
                    })

                optionInstrumentos += "</select>\n<label for=\"instrumentos\">Instrumentos</label>";

                $('#servicoOferecidoInstrumentos').html("select").hide();
                $('#servicoOferecidoInstrumentos').html(optionInstrumentos).show();

                $('select').material_select();

                // NORMAS APLICAVEIS
                var optionNormas = "<select name='normasAplicaveis'>\n" +
                    "<option value='0' selected>Normas Utilizadas</option>";

                $.each(dados.normasAplicaveis, function (i, obj) {
                    optionNormas += '<option value="' + obj.id + '">' + obj.normaDocAplicavel + '</option>\n';
                })

                optionNormas += "</select>\n<label for=\"normasAplicaveis\">Normas Aplicav&eacuteis</label>";
                console.log(optionNormas);

                $('#servicoOferecidoNormaAplicavel').html("select").hide();
                $('#servicoOferecidoNormaAplicavel').html(optionNormas).show();

                $('select').material_select();

                //ATRIBUTOS COMUNS
                $('#laboratorio').val(dados.laboratorio.nome);
                $('#unidadeRastreada').val(dados.unidadeRastreada);
                $('.cmcFo').val(dados.cmcFo)
                $('.descricaoEspecificacao').val(dados.descricaoEspecificacao);
                $('.outrosItensEnvolvidos').val(dados.outrosItensEnvolvidos);

                //MODAL LABORATÓRIO

                $('#gerenteResponsavel').val(dados.laboratorio.gerenteTecnico.nomeGuerra);
                
                if(dados.gerenteSubstituto != null)
                    $('#gerenteSubstituto').val(dados.laboratorio.gerenteSubstituto.nomeGuerra);
                    
                $('#email').val(dados.laboratorio.email);
                $('#organizacao').val(dados.laboratorio.organizacao.sigla);
                $('#sitioInternet').val(dados.laboratorio.sitioInternet);

                $('#telefone').val(dados.laboratorio.telefone1);
                if(dados.laboratorio.telefone2 != ""){
                     $('#telefone').val( $('#telefone').val() + "/ " + dados.laboratorio.telefone2);
                     if(dados.laboratorio.telefone3 != ""){
                        $('#telefone').val( $('#telefone').val() + "/ " + dados.laboratorio.telefone3);
                     }
                }

                $('#endereco').val(dados.laboratorio.tipoLogradouro.sigla + " "
                    +dados.laboratorio.logradouro + ", "
                    +dados.laboratorio.numero + " - "
                    +dados.laboratorio.bairro + ", "
                    +dados.laboratorio.cidade.nome + ' - '
                    +dados.laboratorio.uf.nome);

                //MODAL INSTRUMENTOS
                $('#detalhes').hide();
                $('#instrumentosVisitante').change(function(){
                    //$('#carregando').show();

                    if($('#instrumentosVisitante').val() == '')
                        $('#detalhes').hide();

                    $('#instrumento').val(dados.validadeInstrumento[$('#instrumentosVisitante').val()].instrumento.instrumento);
                    $('#modelo').val(dados.validadeInstrumento[$('#instrumentosVisitante').val()].instrumento.modelo);
                    $('#unidadeFo').val(dados.validadeInstrumento[$('#instrumentosVisitante').val()].instrumento.unidadeFo);
                    $('#especificacaoFo').val(dados.validadeInstrumento[$('#instrumentosVisitante').val()].instrumento.especificacaoFo);

                    $('#detalhes').show();
                });

                if(dados.realizadoCliente == true && dados.realizadoLaboratorio == true){                    
                    $('#localRealizacaoEnsaio').val("Cliente e Laborat\u00f3rio");
                } else if(dados.realizadoCliente == true){                
                    $('#localRealizacaoEnsaio').val("Cliente");
                } else{                    
                    $('#localRealizacaoEnsaio').val("Laboratorio");
                }
                
                if(dados.disponivel == true){                
                    $('#disponivel').val("Dispon\u00edvel");
                } else{                    
                    $('#disponivel').val("Indispon\u00edvel");
                }

                //CASO CALIBRAÇÃO
                if($('#escolhaServicoOferecido').val() == 1){
                    $('#grandezaBasica').val(dados.grandezaBasica.nome);
                    $('#grandezaDerivada').val(dados.grandezaDerivada.nome);
                    $('.servicoCalibracao').show();
                } 

                //CASO ENSAIO
                else if($('#escolhaServicoOferecido').val() == 2){
                    $('#areaAtividade').val(dados.areaAtividade.descricao);
                    $('#classeEnsaio').val(dados.classeEnsaio.descricao);
                    $('#metodo').val(dados.metodo);
                    $('.servicoEnsaio').show();
                }

                $('.esconder').show();

            },
            error: function () {
                $('.esconder').hide();
            }
        });
        $('#carregando').hide();
    });
});