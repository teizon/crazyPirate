public class Monstro {
    private String nome;
    private int forca;
    private int vida;

    public Monstro(String nome, int forca, int vida){
        this.nome = nome;
        this.forca = forca;
        this.vida = vida;
    }

    public String getMonstro(){
        String descricaoMostro = "o nome do mostro e "+nome+" tem forca "+forca+" e total de vida valendo "+vida+" xps";
        return descricaoMostro;
    }

}
