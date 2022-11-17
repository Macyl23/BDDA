public class SelectCondition {
    int indiceColonne;
    String operateur;
    int valeurComparaison;

    public SelectCondition(int iColonne, String operateur, int valeurComparaison) {
        this.indiceColonne = iColonne;
        this.operateur = operateur;
        this.valeurComparaison = valeurComparaison;
    }

}
