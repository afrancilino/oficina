/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.francilino.oficina.models;

/**
 *
 * @author Margareth
 */
public enum StatusOrdemServico {

    AGUARDANDO_ORCAMENTO("Aguardando Orçamento"), //0
    AGUARDANDO_AUTORIZACAO("Aguardando Autorização"), //1
    PRONTO("Pronto"), //2
    FECHADO("Inativo"), //3
    EM_GARANTIA("Em Garantia"), //4
    RETORNO_GARANTIA("Retorno Garantia"), //5
    CONCLUIDO("Concluído"); //6

    private String statusOrdemServico;

    private StatusOrdemServico(String statusOrdemServico) {
        this.statusOrdemServico = statusOrdemServico;
    }

    /**
     * @return the statusOrdemServico
     */
    public String getStatusOrdemServico() {
        return statusOrdemServico;
    }

    /**
     * @param statusOrdemServico the statusOrdemServico to set
     */
    public void setStatusOrdemServico(String statusOrdemServico) {
        this.statusOrdemServico = statusOrdemServico;
    }
}
