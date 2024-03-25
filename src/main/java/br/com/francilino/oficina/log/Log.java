package br.com.francilino.oficina.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledFuture;

/**
 * Classe que implementa o serviço de log de eventos. O serviço de log possúi 6
 * níveis: DEBUG, INTERFACE, INSTRUCAO, SISTEMA, EXCECAO, DESATIVADO. O sistema
 * só irá gravar os registros de log do nível ativo. Os níveis são hierárquicos,
 * da esquerda para a direita, ou seja, ao definir um nível, os demais na
 * hierarquia também serão habilitados. EX: Se for definido o nivel INSTRUCAO,
 * os registros de SISTEMA e EXCECAO também serão salvos. Nível recomendado:
 * INSTRUCAO. No nível DEBUG os 'printStackTrace' das exceções são exibidos.
 */
public class Log {

    public enum Nivel {

        DESATIVADO, EXCECAO, SISTEMA, INSTRUCAO, INTERFACE, DEBUG;
    };

    private static final int TAMANHO_MAXIMO_ARQUIVO = (int) Math.pow(2, 20) * 10; //bytes (10MB)

    private static Log instancia;

    private Nivel nivel;

    private final BlockingQueue<MensagemLog> filaMensagensProcessar;

    private FileOutputStream out;

    private File arquivoLogAtual;

    private boolean verbose;

    private int contagemArquivoAntigo;

    private String diretorioFinal;

    private File fileDiretorio;

    private ScheduledFuture gravadorLog;
    
    private String diretorio = "/opt/oficina/log";

    private final List<OuvinteLog> ouvintes;

    private Log() {
        filaMensagensProcessar = new LinkedBlockingDeque<>();
        ouvintes = new ArrayList<>();
        nivel = Nivel.INTERFACE;
    }

    public static Log getInstancia() {
        if (instancia == null) {
            instancia = new Log();
        }
        return instancia;
    }

    /**
     * Inicia o processamento do serviço de log
     *
     * @param info informações básicas que irão no cabeçalho do arquivo de log
     * @param versao do sistema que irá compor o nome do arquivo
     * @throws java.lang.Exception
     */
    public void inicia(String info, String versao) throws Exception {
        if (nivel == Nivel.DESATIVADO) {
            return;
        }

        try {

            GregorianCalendar c = new GregorianCalendar();

            String hora = String.format("%02d", Math.round(c.get(Calendar.HOUR_OF_DAY)));
            String minuto = String.format("%02d", Math.round(c.get(Calendar.MINUTE)));
            String segundo = String.format("%02d", Math.round(c.get(Calendar.SECOND)));

            diretorio += c.get(Calendar.YEAR) + File.separator + (c.get(Calendar.MONTH) + 1) + File.separator + c.get(Calendar.DAY_OF_MONTH);
            diretorioFinal = hora + minuto;

            if (new File(diretorio + File.separator + diretorioFinal).exists()) {
                diretorioFinal += segundo;
            }

            String diretorioCompleto = diretorio + File.separator + diretorioFinal + File.separator;

            fileDiretorio = new File(diretorioCompleto);
            fileDiretorio.mkdirs();

            String arquivo = diretorioCompleto + "Oficina - " + versao + ".log";

            arquivoLogAtual = new File(arquivo);

            if (!arquivoLogAtual.exists()) {
                arquivoLogAtual.createNewFile();
            }

            String informacoes = "Arquivo de log de Oficina\n\n";

            if (info != null) {
                informacoes += info;
            }

            informacoes += obterInformacoesMaquina() + "----------------\n\n";

            out = new FileOutputStream(arquivo, true);

            out.write(informacoes.getBytes());

            if (nivel == Nivel.DEBUG) {
                System.out.print(informacoes);
            }

        } catch (IOException ex) {
            Log.gravarLogExcecao("Erro ao abrir arquivo log", this, ex);
            if (out != null) {
                out.close();
            }
            throw new Exception("Não é possível acessar o arquivo de log em " + arquivoLogAtual);
        }
        Log.gravarLogInstrucao("Iniciando serviço de log", this);
        gravadorLog = ThreadPool.agendarExecucaoPeriodica(new ProcessarLog(), 100, "Processador Mensagens Log");
    }

    /**
     * Grava uma menssagem de Debug no log, apenas menssagens de teste de
     * desenvolvimento devem ser inseridas EX: Saída do SQL
     *
     * @param descricao
     * @param classe
     */
    public static void gravarLogDebug(String descricao, Object classe) {
        if (Nivel.DEBUG.ordinal() > getInstancia().getNivel().ordinal()) {
            return;
        }
        getInstancia().processarMensagem(new MensagemLog(Nivel.DEBUG, descricao, classe));
    }

    /**
     * Grava uma menssagem de Interface no log, apenas mensagens de ação na IHM
     * devem ser inseridas EX: Clique do Mouse, janela aberta...
     *
     * @param descricao
     * @param classe
     */
    public static void gravarLogInterface(String descricao, Object classe) {
        if (Nivel.INTERFACE.ordinal() > getInstancia().getNivel().ordinal()) {
            return;
        }
        getInstancia().processarMensagem(new MensagemLog(Nivel.INTERFACE, descricao, classe));
    }

    /**
     * Grava uma menssagem de Instrução no log, apenas menssagens de nivel
     * operacional devem ser inseridas EX: Driver carregado, modulo iniciado
     *
     * @param descricao
     * @param classe
     */
    public static void gravarLogInstrucao(String descricao, Object classe) {
        if (Nivel.INSTRUCAO.ordinal() > getInstancia().getNivel().ordinal()) {
            return;
        }
        getInstancia().processarMensagem(new MensagemLog(Nivel.INSTRUCAO, descricao, classe));
    }

    /**
     * Grava uma menssagem de Exceção no log, apenas menssagens de exceção do
     * sistema devem ser inseridas EX: Erro em leitura do arquivo...
     *
     * @param descricao
     * @param classe
     * @param e do try-catch
     */
    public static void gravarLogExcecao(String descricao, Object classe, Exception e) {
        if (Nivel.EXCECAO.ordinal() > getInstancia().getNivel().ordinal()) {
            return;
        }
        getInstancia().processarMensagem(new MensagemLog(Nivel.EXCECAO, descricao, classe, e));
    }

    /**
     * Grava uma menssagem de Sistema no log, apenas menssagens do sistema
     * operacional devem ser inseridas EX: Memória disponível, uso do HD...
     *
     * @param descricao
     */
    public static void gravarLogSistema(String descricao) {
        if (Nivel.SISTEMA.ordinal() > getInstancia().getNivel().ordinal()) {
            return;
        }
        getInstancia().processarMensagem(new MensagemLog(Nivel.SISTEMA, descricao));
    }

    /**
     * Monta a mensagem que será salva no log e notifica os ouvintes
     */
    private void processarMensagem(MensagemLog mensagemLog) {
        //Exibe no terminal
        if (isVerbose()) {
            System.out.println(mensagemLog.toString() + "\n");
            if (mensagemLog.getException() != null) {
                mensagemLog.getException().printStackTrace();
            }
        }

        //Adiciona a mensagem na fila para gravação
        filaMensagensProcessar.add(mensagemLog);

    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Obtém o nível do LOG
     *
     * @return
     */
    public Nivel getNivel() {
        return nivel;
    }

    /**
     * Define o nível do LOG
     *
     * @param nivel
     */
    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
        Log.gravarLogInstrucao("Nível do serviço de log definido: " + nivel.name(), this);
    }

    /**
     * Define o nível do LOG
     *
     * @param nivel
     */
    public void setNivel(String nivel) {
        try {
            setNivel(Nivel.valueOf(nivel.toUpperCase().trim()));
        } catch (NullPointerException | IllegalArgumentException ex) {
            setNivel(Nivel.INSTRUCAO);
        }
    }

    /**
     * Verifica se o Log encontrasse no modo de debug
     *
     * @return Retorna verdadeiro caso a configuração atual do log esteja no
     * modo debug
     */
    public static boolean isDebug() {
        return Log.getInstancia().getNivel() == Nivel.DEBUG;
    }

    /**
     * Verifica se tem mensagens pendentes para serem salvas no arquivo
     *
     * @return
     */
    public boolean temMensagensPendentes() {
        return !filaMensagensProcessar.isEmpty();
    }

    /**
     * Obtem o numero de mensagens restantes a serem gravadas do log
     *
     * @return
     */
    public int getNumMensagensPendentes() {
        return filaMensagensProcessar.size();
    }

    public void addOuvinte(OuvinteLog ouvinteLog) {
        ouvintes.add(ouvinteLog);
    }

    /**
     * Encerra o serviço de log
     *
     */
    public void encerrar() {

        Log.gravarLogInstrucao("Encerrando serviço de log", this);

        if (getNivel() == Nivel.DESATIVADO) {
            return;
        }

        gravadorLog.cancel(false);

        setNivel(Log.Nivel.DESATIVADO);

        StringBuilder mensagensRestantes = new StringBuilder();
        while (!filaMensagensProcessar.isEmpty()) {
            try {
                mensagensRestantes.append(filaMensagensProcessar.take()).append("\n");
            } catch (InterruptedException ex) {
                Log.gravarLogExcecao("Erro de interrupção de thread", this, ex);
            }
        }

        try {
            out.write(mensagensRestantes.toString().getBytes());
            out.close();
        } catch (IOException ex) {
            Log.gravarLogExcecao("Erro ao encerrar o log", this, ex);
        }
    }

    /**
     * Obtem informações da maquina para compor o cabeçalho do arquivo de log
     *
     * @return
     * @throws UnknownHostException
     */
    private String obterInformacoesMaquina() throws UnknownHostException, SocketException {
        StringBuilder info = new StringBuilder();

        //SO
        String nameOS = System.getProperty("os.name");
        String versionOS = System.getProperty("os.version");
        String architectureOS = System.getProperty("os.arch");
        info.append("\nSistema operacional: ").append(nameOS).append(" ").append(versionOS).append(" ").append(architectureOS).append("\n");

        //Informações do Java
        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        info.append("JAVA: ").append(javaVendor).append(" ").append(javaVersion).append("\n");

        //Processador
        int numeroProcessadores = Runtime.getRuntime().availableProcessors();
        info.append("Processador (núcleo): ").append(numeroProcessadores).append("\n");

        return info.toString();
    }

    public File getArquivoLogAtual() {
        return arquivoLogAtual;
    }

    private void verificaArquivoGrande() throws IOException {
        if (arquivoLogAtual.length() >= TAMANHO_MAXIMO_ARQUIVO) {
            out.close();
            contagemArquivoAntigo++;
            File arquivoRenomear = new File(arquivoLogAtual.getAbsoluteFile() + "." + contagemArquivoAntigo);
            arquivoLogAtual.renameTo(arquivoRenomear);
        }
    }
 
   private class ProcessarLog implements Runnable {

        @Override
        public void run() {

            if (out != null && !filaMensagensProcessar.isEmpty()) {
                StringBuilder mensagens = new StringBuilder();
                int cont = 0;
                while (!filaMensagensProcessar.isEmpty()) {
                    cont++;
                    try {
                        MensagemLog mensagemLog = filaMensagensProcessar.take();

                        //Notifica os ouvintes
                        for (OuvinteLog ouvinteLog : ouvintes) {
                            ouvinteLog.notifica(mensagemLog);
                        }

                        mensagens.append(mensagemLog.toString()).append("\n");
                    } catch (InterruptedException ex) {
                        Log.gravarLogExcecao("Erro de interrupção de thread", this, ex);
                    }
                    if (cont >= 500) {
                        break;
                    }
                }
                String msg = mensagens.toString();
                if (!msg.isEmpty()) {
                    try {
                        verificaArquivoGrande();
                        if (!fileDiretorio.exists()) {
                            fileDiretorio.mkdirs();
                        }
                        if (!arquivoLogAtual.exists()) {
                            arquivoLogAtual.createNewFile();
                            out = new FileOutputStream(arquivoLogAtual, true);
                        }
                        out.write((msg).getBytes());
                    } catch (IOException ex) {
                        Log.gravarLogExcecao("Erro ao gravar registro de log", this, ex);
                    }
                }
            }
        }

        @Override
        public String toString() {
            return "Processador de logs";
        }

    }

}
