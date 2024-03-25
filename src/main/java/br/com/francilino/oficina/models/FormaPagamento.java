package br.com.francilino.oficina.models;

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
