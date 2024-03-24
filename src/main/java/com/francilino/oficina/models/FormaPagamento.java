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
public enum FormaPagamento {

    PIX("PIX"), //0
    DEBITO("Débito"), //1
    CREDITO("Crédito"), //2
    DINHEIRO("Dinheiro"); //3

    private String formaPagamento;

    private FormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    /**
     * @return the formaPagamento
     */
    public String getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * @param formaPagamento the formaPagamento to set
     */
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
