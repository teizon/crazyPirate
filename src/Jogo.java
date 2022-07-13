/**
 * Essa é a classe principal da aplicacao "World of Zull".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.
 * 
 * Usuários podem caminhar em um cenário. E é tudo! Ele realmente precisa ser 
 * estendido para fazer algo interessante!
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o método "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os ambientes, 
 * cria o analisador e começa o jogo. Ela também avalia e  executa os comandos que 
 * o analisador retorna.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */

public class Jogo {
    // analisador de comandos do jogo
    private Analisador analisador;
    // ambiente onde se encontra o jogador
    private Ambiente ambienteAtual;
    private Monstro monstro;
    private ItensEstranhos item;
        
    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo()  {
        criarAmbientes();
        analisador = new Analisador();
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes() {  
        Monstro antrax, capivara;
        antrax = new Monstro("antrax", 4000, 1);
        capivara = new Monstro("capivara", 1000, 500);

        ItensEstranhos garrafa, espada;
        garrafa = new ItensEstranhos("garrafa", 2, "garrafa de whisky");
        espada = new ItensEstranhos("espada", 8, "espada pirata");
         
        Ambiente navio, porto, pub, cabana, poraoDaCabana, centro, trilha, caverna, feira;

        // cria os ambientes
        navio = new Ambiente("em seu navio pirata");
        porto = new Ambiente("no porto da cidade");
        feira = new Ambiente("na feira de pesca");
        pub = new Ambiente("no pub mais copo sujo da cidade", garrafa); //garrafa quebrada
        cabana = new Ambiente("na cabana da floresta");
        poraoDaCabana = new Ambiente("porao da cabana", espada); //arma magica
        centro = new Ambiente("no centro da cidade");
        trilha = new Ambiente("em uma trilha suspeita", capivara);
        caverna = new Ambiente("em uma caverna na floresta", antrax);
        
        // inicializa as saidas dos ambientes
        navio.ajustarSaida("paraBaixo", porto); // navio
        porto.ajustarSaida("paraCima", navio);
        porto.ajustarSaida("leste", feira);
        porto.ajustarSaida("norte", centro);
        porto.ajustarSaida("oeste", pub);
        feira.ajustarSaida("oeste", porto);
        feira.ajustarSaida("sul", centro);
        pub.ajustarSaida("leste", porto);
        pub.ajustarSaida("sul", centro);
        pub.ajustarSaida("norte", trilha);
        trilha.ajustarSaida("norte", cabana);
        cabana.ajustarSaida("paraBaixo", poraoDaCabana);
        cabana.ajustarSaida("norte", caverna);
        cabana.ajustarSaida("sul", trilha);
        poraoDaCabana.ajustarSaida("paraCima", cabana);
        caverna.ajustarSaida("sul", cabana);

        ambienteAtual = navio;  // o jogo comeca em frente à reitoria
    }

    /**
     *  Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar()  {
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nós repetidamente lemos comandos e 
        // os executamos até o jogo terminar.
                
        boolean terminado = false;
        while (! terminado) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        System.out.println("Obrigado por jogar. Até mais!");
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo ao Crazy Pirate!");
        System.out.println("Craze Pirate eh um novo jogo de aventura, incrivelmente doido.");
        System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
        System.out.println();
        
        //forma de ganhar é pegar o tesouro
        saidas();
    }

    private void saidas() {
        System.out.println(ambienteAtual.getDescricaoLonga());
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    public boolean processarComando(Comando comando) {
        boolean querSair = false;
        if(comando.ehDesconhecido()) {
        System.out.println("Eu não entendi o que você disse...");
        return false;
        }
        String palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando.equals("ajuda")) {
            imprimirAjuda();
        }
        else if (palavraDeComando.equals("ir")) {
            irParaAmbiente(comando);
        }
        else if (palavraDeComando.equals("sair")) {
            querSair = sair(comando);
        }
        else if (palavraDeComando.equals("observar")) {
            observar();
        }
        else if (palavraDeComando.equals("pegar")) {
            pegar(comando);
        }
        
        return querSair;
    }

    private void observar() {
        imprimirLocalizacaoAtual();
    }

    public void imprimirLocalizacaoAtual(){
        System.out.println("voce esta: " + ambienteAtual.getDescricaoLonga());
    }

    public void pegar(Comando comando){
        if(!comando.temSegundaPalavra()) {            
            System.out.println("pegar o que?");
            //System.out.println("suas saidas sao: " + ambienteAtual.getSaidas());
            return;
        }
        
        String itemDesejado = comando.getPalavraDeComando();

        ItensEstranhos itemPego = null;

        itemPego = ambienteAtual.pegarItem(itemDesejado);

        if (itemPego == null){
            System.out.println("nao ha item");
        }else{
            ambienteAtual.consultarItem() = itemPego;

            saidas();
        }

    }


    /**
     * Exibe informações de ajuda.
     * Aqui nós imprimimos algo bobo e enigmático e a lista de  palavras de comando
     */
    public void imprimirAjuda()  {
        System.out.println("Voce esta perdido. Voce esta sozinho.");
        System.out.println("voce esta ancorado em uma cidade pequena, em busca de seu tesouro.");
        System.out.println();
        System.out.println("Suas palavras de comando sao:");
        System.out.println("   " + analisador.getComandos());
    }

    /** 
     * Tenta ir em uma direcao. Se existe uma saída para lá entra no novo ambiente, 
     * caso contrário imprime mensagem de erro.
     */
    public void irParaAmbiente(Comando comando)  {
        // se não há segunda palavra, não sabemos pra onde ir...
        if(!comando.temSegundaPalavra()) {            
            System.out.println("Ir pra onde?");
            //System.out.println("suas saidas sao: " + ambienteAtual.getSaidas());
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        Ambiente proximoAmbiente = null;

        proximoAmbiente = ambienteAtual.getAmbiente(direcao);


        if (proximoAmbiente == null) {
            System.out.println("Nao ha passagem!");
        }
        else {
            ambienteAtual = proximoAmbiente;
            
            saidas();
        }
    }

    /** 
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nós queremos 
     * realmente sair do jogo.
     * @return true, se este comando sai do jogo, false, caso contrário.
     */
    public boolean sair(Comando comando)  {
        if(comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        }
        else {
            return true;  // sinaliza que nós realmente queremos sair
        }
    }
}
