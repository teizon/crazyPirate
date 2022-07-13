import java.util.HashMap;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe é parte da aplicação "World of Zuul".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.  
 *
 * Um "Ambiente" representa uma localização no cenário do jogo. Ele é conectado aos 
 * outros ambientes através de saídas. As saídas são nomeadas como norte, sul, leste 
 * e oeste. Para cada direção, o ambiente guarda uma referência para o ambiente vizinho, 
 * ou null se não há saída naquela direção.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */
public class Ambiente  {
        private String descricao;
        private Monstro monstro;
        private ItensEstranhos item;
        private HashMap<String, Ambiente> saidas;

        public Ambiente(String descricao) {
            this.descricao = descricao;
            this.saidas = new HashMap<String,Ambiente>();
        }
        public Ambiente(String descricao, Monstro monstro){
            this(descricao);
            this.monstro = monstro;
        }
        public Ambiente(String descricao, ItensEstranhos item){
            this(descricao);
            this.item = item;

        }

        public void ajustarSaida(String direcao, Ambiente ambiente) {
            this.saidas.put(direcao, ambiente);
        }
        public String getDescricao() {
            return this.descricao;
        }
        public Ambiente getAmbiente(String direcao) {
            return this.saidas.get(direcao);
        }
        public String getSaidas() {
            String textoSaidas = "";
            for (String direcao : saidas.keySet()) {
                textoSaidas = textoSaidas + direcao + " ";
            }   
            return textoSaidas;
        }

        public String getDescricaoLonga(){
            String texto = "voce esta " + getDescricao() + "\n" + "saidas: " + getSaidas() + "\n";

            if (temMonstro() || temItem()){
                texto += "aqui tem um monstro e item " +"\n" + this.monstro.getMonstro();
                texto += "\n" + this.item.getItem();
            }else if (temMonstro() || !temItem()){
                texto += "nao tem item aqui, mas, tem um monstro aqui" + "\n";
                texto += this.monstro.getMonstro();
            }else if (!temMonstro() || !temItem()){
                texto += "nao tem monstro aqui, mas, tem um item aqui" + "\n";
                texto += this.item.getItem();
            }else{
                texto += "nao tem nada aqui";
            }
            return texto;
        }

        public boolean temItem(){
            if (this.item != null){
                return true;
            }else{
                return false;
            }
        }

        public boolean temMonstro(){
            if (this.monstro != null){
                return true;
            }else{
                return false;
            }
        }

        public Object consultarItem(){
            if (temItem()){
                return this.item.getItem();
            }else{
                return null;
            }
        }

        public ItensEstranhos coletarItem(){
            if (temItem()){
                ItensEstranhos itemPego = this.item;
                this.item = null;
                return itemPego;
            }else{
                return null;
            }
        }

        

}
