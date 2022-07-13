public class ItensEstranhos {
    private String nome;
    private int peso;
    private String descricao;
    
    public ItensEstranhos(String nome, int peso, String descricao){
        this.nome = nome;
        this.descricao = descricao;
        this.peso = peso;
    } 

    public String getNome(){
        return this.nome;
    }

    public String getItem(){
        String descricaoItem = "o nome do item e " + this.nome + " e ele sua descricao e " + this.descricao;
        return descricaoItem;
    } 
}
