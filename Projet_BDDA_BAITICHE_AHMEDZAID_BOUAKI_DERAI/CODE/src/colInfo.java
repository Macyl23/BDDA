import java.io.Serializable;

public class ColInfo implements Serializable {
    // toutes les instances de cette classe sont des chaines pour une meilleur gestion
    // indication de la prof
    private static final long serialVersionUID = 1234L;

    private String nom;
    private String type;

    public ColInfo(String nom, String type) {
        this.nom = nom;
        this.type = type;

    }

    public void init() {
    };

    public void finish() {
    };

    public String getNom() {
        return this.nom;
    }

    public String getType() {
        return this.type;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String setTypeReal(String typeReal) {
        return typeReal;
    }

    public String toString(){
        return "type de colonne: "+this.type;
    }

}