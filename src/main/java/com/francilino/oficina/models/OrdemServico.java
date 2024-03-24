package com.francilino.oficina.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class OrdemServico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    private String descricaoDefeito;

    @NotEmpty
    private String descricaoProduto;

    @NotEmpty
    private ArrayList<String> midiaEntrada;

    @Temporal(TemporalType.DATE)
    private Date dataEntrada;

    @NotEmpty
    private String parecerTecnico;

    @NotEmpty
    private String valorOrdemServico;

    @NotEmpty
    private Boolean aprovado;

    @NotEmpty
    private ArrayList<String> midiaSaida;

    @Temporal(TemporalType.DATE)
    private Date dataSaida;

    @NotEmpty
    private Boolean produtoRetirado;

    @Temporal(TemporalType.DATE)
    private Date garantia;

    @NotEmpty
    private StatusOrdemServico statusOrdemServico;

    @NotEmpty
    private FormaPagamento formaPagamento;

    @NotEmpty
    private Boolean pagamentoRealizado;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Funcionario tecnico;

    @ManyToOne
    private Funcionario recepcionista;

    public boolean isAprovado() {
        return aprovado;
    }

    public boolean produtoRetirado() {
        return produtoRetirado;
    }

    public boolean verificaGarantia() {
        Date data = new Date();
        return garantia.before(data);
    }

    public void retornoGarantia() {
        statusOrdemServico = StatusOrdemServico.RETORNO_GARANTIA;
    }

    public boolean verificaPagamento() {
        return pagamentoRealizado;
    }

    public void concluiOrdemServico() {
        Date data = new Date();
        if (garantia.after(data)) {
            statusOrdemServico = StatusOrdemServico.CONCLUIDO;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricaoDefeito() {
        return descricaoDefeito;
    }

    public void setDescricaoDefeito(String descricaoDefeito) {
        this.descricaoDefeito = descricaoDefeito;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public ArrayList<String> getMidiaEntrada() {
        return midiaEntrada;
    }

    public void setMidiaEntrada(ArrayList<String> midiaEntrada) {
        this.midiaEntrada = midiaEntrada;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getParecerTecnico() {
        return parecerTecnico;
    }

    public void setParecerTecnico(String parecerTecnico) {
        this.parecerTecnico = parecerTecnico;
    }

    public String getValorOrdemServico() {
        return valorOrdemServico;
    }

    public void setValorOrdemServico(String valorOrdemServico) {
        this.valorOrdemServico = valorOrdemServico;
    }

    public Boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public ArrayList<String> getMidiaSaida() {
        return midiaSaida;
    }

    public void setMidiaSaida(ArrayList<String> midiaSaida) {
        this.midiaSaida = midiaSaida;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Boolean getProdutoRetirado() {
        return produtoRetirado;
    }

    public void setProdutoRetirado(Boolean produtoRetirado) {
        this.produtoRetirado = produtoRetirado;
    }

    public Date getGarantia() {
        return garantia;
    }

    public void setGarantia(Date garantia) {
        this.garantia = garantia;
    }

    public StatusOrdemServico getStatusOrdemServico() {
        return statusOrdemServico;
    }

    public void setStatusOrdemServico(StatusOrdemServico statusOrdemServico) {
        this.statusOrdemServico = statusOrdemServico;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Boolean getPagamentoRealizado() {
        return pagamentoRealizado;
    }

    public void setPagamentoRealizado(Boolean pagamentoRealizado) {
        this.pagamentoRealizado = pagamentoRealizado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Funcionario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Funcionario tecnico) {
        this.tecnico = tecnico;
    }

    public Funcionario getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Funcionario recepcionista) {
        this.recepcionista = recepcionista;
    }

}
