package com.francilino.oficina.log;

import java.text.SimpleDateFormat;

public class MensagemLog {

    private final Log.Nivel nivelMensagem;
    private final String descricao;
    private Object classe;
    private Exception exception;

    public MensagemLog(Log.Nivel nivelMensagem, String descricao, Object classe, Exception exception) {
        this.nivelMensagem = nivelMensagem;
        this.descricao = descricao;
        this.classe = classe;
        this.exception = exception;
    }

    public MensagemLog(Log.Nivel nivelMensagem, String descricao) {
        this.nivelMensagem = nivelMensagem;
        this.descricao = descricao;
    }

    public MensagemLog(Log.Nivel nivelMensagem, String descricao, Object classe) {
        this.nivelMensagem = nivelMensagem;
        this.descricao = descricao;
        this.classe = classe;
    }

    public Log.Nivel getNivelMensagem() {
        return nivelMensagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public Object getClasse() {
        return classe;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        String novaDescricao = descricao.trim();
        
        novaDescricao = novaDescricao.replaceAll("\n", " ");

        //Prepara a mensagem
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM HH:mm:ss:SSS");
        String data = sd.format(System.currentTimeMillis());

        String mensagem = nivelMensagem.name() + " | " + data + " | " + novaDescricao;

        if (classe != null) {
            if (classe.getClass().getCanonicalName() == null) {
                mensagem += " | " + classe.getClass();
            } else {
                mensagem += " | " + classe.getClass().getCanonicalName();
            }
        }

        if (exception != null) {
            mensagem += " | " + exception.toString() + ". StackTrace:";

            StackTraceElement[] trace = exception.getStackTrace();
            for (StackTraceElement traceElement : trace) {
                mensagem += " at " + traceElement;
            }
        }

        return mensagem;
    }
}
