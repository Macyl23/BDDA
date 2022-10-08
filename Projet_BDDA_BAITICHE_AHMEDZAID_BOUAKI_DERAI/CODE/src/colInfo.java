public class ColInfo{
    // tout les instances de cette classe sont des chaines pour une meilleur gestion
    // indication de la prof
    private String nom;
    private String typeInteger;
    private String typeReal;
    private String typeVarChar;
    public ColInfo(String nom,String typeInteger,String tr,String tvc){
        this.nom = nom;
        this.typeInteger = typeInteger;
        this.typeReal = tr;
        this.typeVarChar = tvc;

    }    
    public void init(){};
    public void finish(){};

    public String getNom() {
        return this.nom;
    }
    public String getTypeInteger(){
        return this.typeInteger;
    }
    public String getTypeReal() {
        return typeReal;
    }
    public String getTypeVarChar(){
        return this.typeInteger;
    }
    public void setNom(String nom) {
         this.nom = nom ;
    }
    public void setTypeInteger(String typeInteger){
        this.typeInteger = typeInteger;
    }
    public String setTypeReal(String typeReal) {
        return typeReal;
    }
    public void setTypeVarChar(String typeVarChar){
        this.typeVarChar = typeVarChar;
    }
    

}