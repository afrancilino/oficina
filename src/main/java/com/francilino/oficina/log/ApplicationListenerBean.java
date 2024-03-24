package com.francilino.oficina.log;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextClosedEvent;

public class ApplicationListenerBean implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        //ABERTURA DO SERVIÇO DE LOG
        if (event instanceof ContextRefreshedEvent) {
            if (Log.getInstancia().getArquivoLogAtual() == null) {
                Log.getInstancia().setNivel(Log.Nivel.INTERFACE);
                try {
                    Log.getInstancia().inicia("Oficina", "Versão de Testes 1.0");
                } catch (Exception ex) {
                    System.out.println("Erro LOG");
                    Log.gravarLogExcecao("Iniciando o serviço", this, ex);
                }
            }
        }

        //ENCERRAMENTO DO SERVIÇO DE LOG
        if (event instanceof ContextClosedEvent) {
            try {
                Log.getInstancia().encerrar();
                ThreadPool.getInstancia().parar();
            } catch (Exception ex) {
                System.out.println("Erro LOG");
                Log.gravarLogExcecao("Encerrando o serviço", this, ex);
            }
        }
    }
}
