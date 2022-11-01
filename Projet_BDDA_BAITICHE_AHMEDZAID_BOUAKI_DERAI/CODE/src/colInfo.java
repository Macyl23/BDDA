public class ColInfo {
    // toutes les instances de cette classe sont des chaines pour une meilleur gestion
    // indication de la prof
    //  mettre dans cette classe le nom et le type de la colonne
    
  // ATTRIBUTS NOM COLONNES ET TYPES COLONNES

    private String nom;
    private String type;
/* constructeur colInfo*/
    public ColInfo(String nom, String type) {
        this.nom = nom;
        this.type = type;

    }
    /* methode init() jsais pas si c'est necessaire je laisse au cas ou faudra implementer*/

    public void init() {
    };

    /* methode finish jsais pas si c'est necessaire je laisse au cas ou faudra implementer*/

    public void finish() {
    };

        /* methode getNom() pour obtenir nom colonne*/

    public String getNom() {
        return this.nom;
    }

            /* methode getType () pour obtenir type de colonnes*/

    public String getType() {
        return this.type;
    }
        /* methode setNom () pour obtenir set nom  colonne*/

    public void setNom(String nom) {
        this.nom = nom;
    }

        /* methode setNom () pour obtenir set nom  colonne*/


    public String setType(String typeReal) {
        return typeReal;
    }

}