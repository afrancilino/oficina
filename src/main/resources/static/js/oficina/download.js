$(document).ready(function () {

//--------DOCUMENTOS---------------------------------------------    
    $('#downloadDocumento').click(function(){
        url = 'download?id=' + $('#downloadDocumento').val() + '&controlador=documento';
        $(location).attr("href", url);
    });

//--------SISTEMA DE GESTÃO---------------------------------------------    
    $('#downloadCertificadoSistemaGestao').click(function(){
        console.log('Funcionei downloadCertificadoSistemaGestao');
        console.log($('#fileCertificadoSistemaGestao').val());
        console.log('download?id=' + $('#fileCertificadoSistemaGestao').val() + '&arquivo=certificado&controlador=sistemaGestao');
        url = 'download?id=' + $('#fileCertificadoSistemaGestao').val() + '&arquivo=certificado&controlador=sistemaGestao';
        $(location).attr("href", url);
    });

//--------RECONHECIMENTO DE COMPETÊNCIA---------------------------------------------
    $('#downloadEscopoReconhecimentoCompetencia').click(function(){
        console.log('Funcionei downloadEscopoReconhecimentoCompetencia');
        console.log($('#fileEscopoReconhecimentoCompetencia').val());
        console.log('download?id=' + $('#fileEscopoReconhecimentoCompetencia').val() + '&arquivo=escopo&controlador=reconhecimentoCompetencia');
        url = 'download?id=' + $('#fileEscopoReconhecimentoCompetencia').val() + '&arquivo=escopo&controlador=reconhecimentoCompetencia';
        $(location).attr("href", url);
    });
    
    $('#downloadDocumentoReconhecimentoCompetencia').click(function(){
        console.log('Funcionei downloadDocumentoReconhecimentoCompetencia');
        console.log($('#fileDocumentoReconhecimentoCompetencia').val());
        console.log('download?id=' + $('#fileDocumentoReconhecimentoCompetencia').val() + '&arquivo=documento&controlador=reconhecimentoCompetencia');
        url = 'download?id=' + $('#fileDocumentoReconhecimentoCompetencia').val() + '&arquivo=documento&controlador=reconhecimentoCompetencia';
        $(location).attr("href", url);
    });
});