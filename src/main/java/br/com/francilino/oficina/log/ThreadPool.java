package br.com.francilino.oficina.log;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Esta classe define os pools de threads criados para serem usados no sistema,
 * nenhuma thread deve ser criada fora dessa estrutura. O Pool de Threads se
 * encarrega de otmizar o uso do processador, balanceando as threads pelos
 * diversos nucleos
 */
public class ThreadPool {

    private static ThreadPool instancia;
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private final int tamanhoCoreMinimo;

    private ThreadPool() {
        tamanhoCoreMinimo = Runtime.getRuntime().availableProcessors();

        scheduledThreadPoolExecutor = new RunnableNameThreadPoolExecutor(tamanhoCoreMinimo);
        scheduledThreadPoolExecutor.setMaximumPoolSize(20000);
    }

    public static ThreadPool getInstancia() {
        if (instancia == null) {
            instancia = new ThreadPool();
        }
        return instancia;
    }

    private ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
        return scheduledThreadPoolExecutor;
    }

    /**
     * Submete uma tarefa do tipo Runnable para execução pelo pool e retorna um
     * 'Future' representando essa terefa. O método {@code get} do 'Future' irá
     * retornar {@code null} em caso de <em>sucesso</em>. Este método <b>NÃO</b>
     * grava um log pela submissão dessa Thread.
     *
     * @param r a tarefa a ser submetida
     * @return 'Future' representando essa terefa
     */
    public static Future executar(Runnable r) {
        return executar(r, null);
    }

    /**
     * Submete uma tarefa do tipo Runnable para execução pelo pool e retorna um
     * 'Future' representando essa terefa. O método {@code get} do 'Future' irá
     * retornar {@code null} em caso de <em>sucesso</em>. Este método grava um
     * log pela submissão dessa Thread.
     *
     * @param r a tarefa a ser submetida
     * @param nome o nome da Thread para registro no log
     * @return 'Future' representando essa terefa
     */
    public static Future executar(Runnable r, String nome) {
        ThreadPool.getInstancia().verificaEstadoPool();
        if (nome != null) {
            Log.gravarLogDebug("Thread '" + nome + "' enviada para o pool de Threads | " + ThreadPool.getInstancia().getInfo(), r);
        }
        return ThreadPool.getInstancia().getScheduledThreadPoolExecutor().submit(r);
    }

    /**
     * Submete uma tarefa do tipo Callable para execução pelo pool e retorna um
     * 'Future' representando essa terefa. O método {@code get} do 'Future' irá
     * retornar {@code null} em caso de <em>sucesso</em>. Este método grava um
     * log pela submissão dessa Thread.
     *
     * @param c a tarefa a ser submetida
     * @param nome o nome da Thread para registro no log
     * @return 'Future' representando essa terefa
     */
    public static Future<String> executar(Callable<String> c, String nome) {
        ThreadPool.getInstancia().verificaEstadoPool();
        if (nome != null) {
            Log.gravarLogDebug("Thread '" + nome + "' enviada para o pool de Threads | " + ThreadPool.getInstancia().getInfo(), c);
        }
        return ThreadPool.getInstancia().getScheduledThreadPoolExecutor().submit(c);
    }

    /**
     * Agenda uma tarefa do tipo Runnable para execução pelo pool e retorna um
     * 'Future' representando essa terefa. O método {@code get} do 'Future' irá
     * retornar {@code null} em caso de <em>sucesso</em>. Este método grava um
     * log pelo agendamento dessa Thread.
     *
     * @param r a tarefa a ser agendada
     * @param atraso tempo em que a thread deve ser executada
     * @param unidade define a unidade de tempo do atraso
     * @param nome o nome da Thread para registro no log
     * @return 'Future' representando essa terefa
     */
    public static Future agendarExecucao(Runnable r, long atraso, TimeUnit unidade, String nome) {
        ThreadPool.getInstancia().verificaEstadoPool();
        if (nome != null) {
            Log.gravarLogDebug("Thread '" + nome + "' agendada no o pool de Threads, será executada em " + atraso + unidade.name() + " | " + ThreadPool.getInstancia().getInfo(), r);
        }
        return ThreadPool.getInstancia().getScheduledThreadPoolExecutor().schedule(r, atraso, unidade);
    }

    /**
     * Submete uma tarefa do tipo Runnable e a executa periodicamente e retorna
     * um 'ScheduledFuture' representando essa terefa. A frequencia para ela ser
     * executada novamente leva em consideração o tempo de execução da Thread,
     * aguardando o tempo restante da frequencia para uma nova execução. Ex: Se
     * fordefinido uma frequncia de 500ms e a Thread demorar 100ms para
     * executar, o pool de threads aguardará mais 400ms para executar novamente.
     *
     * @param r a tarefa a ser submetida
     * @param frequencia de execução da Thread em <em>milessegundos</em>
     * @param nome o nome da Thread para registro no log
     * @return 'ScheduledFuture' representando essa terefa
     */
    public static ScheduledFuture agendarExecucaoPeriodica(Runnable r, long frequencia, String nome) {
        return agendarExecucaoPeriodica(r, 0, frequencia, TimeUnit.MILLISECONDS, nome);
    }

    /**
     * Submete uma tarefa do tipo Runnable e a executa periodicamente e retorna
     * um 'ScheduledFuture' representando essa terefa. A frequencia para ela ser
     * executada novamente leva em consideração o tempo de execução da Thread,
     * aguardando o tempo restante da frequencia para uma nova execução. Ex: Se
     * fordefinido uma frequncia de 500ms e a Thread demorar 100ms para
     * executar, o pool de threads aguardará mais 400ms para executar novamente.
     *
     * @param r a tarefa a ser submetida
     * @param frequencia de execução da Thread
     * @param unidade define a unidade de tempo da frequencia
     * @param nome o nome da Thread para registro no log
     * @return 'ScheduledFuture' representando essa terefa
     */
    public static ScheduledFuture agendarExecucaoPeriodica(Runnable r, long frequencia, TimeUnit unidade, String nome) {
        return agendarExecucaoPeriodica(r, 0, frequencia, unidade, nome);
    }

    /**
     * Submete uma tarefa do tipo Runnable e a executa periodicamente e retorna
     * um 'ScheduledFuture' representando essa terefa. A frequencia para ela ser
     * executada novamente leva em consideração o tempo de execução da Thread,
     * aguardando o tempo restante da frequencia para uma nova execução. Ex: Se
     * fordefinido uma frequncia de 500ms e a Thread demorar 100ms para
     * executar, o pool de threads aguardará mais 400ms para executar novamente.
     *
     * @param r a tarefa a ser submetida
     * @param atrasoInicial tempo inicial para agurdar a primeira execução
     * @param frequencia de execução da Thread
     * @param unidade define a unidade de tempo do atraso incial e da frequencia
     * @param nome o nome da Thread para registro no log
     * @return 'ScheduledFuture' representando essa terefa
     */
    public static ScheduledFuture agendarExecucaoPeriodica(Runnable r, long atrasoInicial, long frequencia, TimeUnit unidade, String nome) {
        ThreadPool.getInstancia().verificaEstadoPool();
        Log.gravarLogInstrucao("Thread '" + nome + "' agendada e enviada para o pool de Threads. Será executada com a frequência de " + frequencia + " " + unidade.name() + " | " + ThreadPool.getInstancia().getInfo(), r);
        return ThreadPool.getInstancia().getScheduledThreadPoolExecutor().scheduleAtFixedRate(r, atrasoInicial, frequencia, unidade);
    }

    /**
     * Verifica se a contagem atual de threads no pool ultrapassa os 50% do
     * maximo que o mesmo aceita, se sim ele aumenta o proprio tamanho em 50%
     */
    private void verificaEstadoPool() {

        int atual = scheduledThreadPoolExecutor.getActiveCount();
        int pool = scheduledThreadPoolExecutor.getCorePoolSize();

        double porcentagem = ((double) atual / (double) pool) * 100;

        if (porcentagem > 75) {
            int novoPool = (int) (pool * 2);
            scheduledThreadPoolExecutor.setCorePoolSize(novoPool);

            Log.gravarLogInstrucao("O pool de thread está com " + ((int) porcentagem) + "% de uso. O tamanho do pool será duplicado. Valor atual: " + pool + ", novo valor: " + novoPool + ". Estado atual: " + getInfo(), this);
        } else if (porcentagem < 10) {
            int novoPool = Math.max((int) (pool / 2), tamanhoCoreMinimo);
            if (novoPool != pool) {//Somente se o valor a ser definido for diferente do atual
                scheduledThreadPoolExecutor.setCorePoolSize(novoPool);
                Log.gravarLogInstrucao("O pool de thread está com " + ((int) porcentagem) + "% de uso. O tamanho do pool será reduzido pela metade. Valor atual: " + pool + ", novo valor: " + novoPool + ". Estado atual: " + getInfo(), this);
            }
        }
    }

    /**
     * Obtém o numero de Threds na fila do pool, Threads agendadas permanencem
     * nessa fila indefinidamente
     *
     * @return o numero de Threds na fila do pool
     */
    public int getTamanhoFila() {
        return getScheduledThreadPoolExecutor().getQueue().size();
    }

    /**
     * Obtém informações gerais do Pool de Threads
     *
     * @return
     */
    public String getInfo() {
        int atual = scheduledThreadPoolExecutor.getActiveCount();
        int pool = scheduledThreadPoolExecutor.getCorePoolSize();
        return "Uso: " + ((int) getUsoPoll()) + "%. "
                + "Tamanho da fila: " + scheduledThreadPoolExecutor.getQueue().size() + ". "
                + "Contagem atual: " + atual + ". "
                + "Tamanho: " + pool;
    }
    
    public void parar(){
        scheduledThreadPoolExecutor.shutdown();
    }

    /**
     * Obtem a porcentagem de uso atual do pool de threads
     *
     * @return
     */
    public double getUsoPoll() {
        int atual = scheduledThreadPoolExecutor.getActiveCount();
        int pool = scheduledThreadPoolExecutor.getCorePoolSize();
        double porcentagem = ((double) atual / (double) pool) * 100;
        return porcentagem;
    }

    public class RunnableNameThreadPoolExecutor extends ScheduledThreadPoolExecutor {

        public RunnableNameThreadPoolExecutor(int corePoolSize) {
            super(corePoolSize);
        }

        @Override
        protected <V> RunnableScheduledFuture<V> decorateTask(Callable<V> callable, RunnableScheduledFuture<V> task) {

            String nome = callable.getClass().getSimpleName();

            String toString = callable.toString();
            String toStringDefault = callable.getClass().getName() + "@" + Integer.toHexString(callable.hashCode());

            if (!toString.equals(toStringDefault)) {
                nome += " | " + toString;
            }

            nome += " | Callable";

            Thread.currentThread().setName(nome);

            return super.decorateTask(callable, task);
        }

        @Override
        protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task) {

            String nome = runnable.getClass().getSimpleName();

            String toString = runnable.toString();
            String toStringDefault = runnable.getClass().getName() + "@" + Integer.toHexString(runnable.hashCode());

            if (!toString.equals(toStringDefault)) {
                nome += " | " + toString;
            }

            if (nome == null || nome.isEmpty()) {
                nome = toString;
            }

            nome = "#" + Thread.currentThread().getId() + " | " + nome + " | Runnable";

            Thread.currentThread().setName(nome);

            return super.decorateTask(runnable, task);
        }

    }
}
