public class SelectCondition {


    private int indiceColonne;
    private static final String[] operateur = {"=","<",">","<>","<=",">="};
    private String valeurComparaison;
    private String op;

    public SelectCondition(int iColonne,String op, String valeurComparaison) {
        this.indiceColonne = iColonne;
        this.valeurComparaison = valeurComparaison;
        this.op=op;
    }

    
    /**
     * Méthode qui permet de comparer la valeur de la condition et la valeur du record
     * @param valeurRecord la valeur du record r
     * @return TRUE si la condition est satisfaite false sinon
     */
    public  boolean verifConditionString(String valeurRecord){
        
        switch (this.op){
            case "=":
			return this.valeurComparaison.equals(valeurRecord);
            case "<>":
                return valeurRecord.compareTo(this.valeurComparaison) != 0;
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }

    public boolean verifConditionInt(int valeurRecord){
        switch (this.op){
            case "=":
			return Integer.valueOf(this.valeurComparaison)==valeurRecord;
            case "<":
                return valeurRecord < Integer.valueOf(this.valeurComparaison);
            case ">":
                return valeurRecord > Integer.valueOf(this.valeurComparaison);
            case "<=":
                return valeurRecord <= Integer.valueOf(this.valeurComparaison);
            case ">=":
                return valeurRecord >= Integer.valueOf(this.valeurComparaison);
            case "<>":
                return valeurRecord != Integer.valueOf(this.valeurComparaison);
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }
    public boolean verifConditionReal(Float valeurRecord){
        switch (this.op){
            case "=":
			    return Float.compare(valeurRecord, Float.valueOf(valeurComparaison)) == 0;
            case "<":
                return  Float.compare(valeurRecord, Float.valueOf(valeurComparaison))<0;
            case ">":
                return Float.compare(valeurRecord, Float.valueOf(valeurComparaison))>0;
            case "<=":
                return Float.compare(valeurRecord, Float.valueOf(valeurComparaison))<=0;
            case ">=":
                return Float.compare(valeurRecord, Float.valueOf(valeurComparaison))>=0;
            case "<>":
                return Float.compare(valeurRecord, Float.valueOf(valeurComparaison))!=0;
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }

    public String toString(){
        return "Valeur: "+valeurComparaison+"\noperateur: "+op+"\nindice: "+indiceColonne;
    }

    public int getIndiceColonne() {
        return indiceColonne;
    }

    public void setIndiceColonne(int indiceColonne) {
        this.indiceColonne = indiceColonne;
    }

    public static String[] getOperateur() {
        return operateur;
    }

    public String getValeurComparaison() {
        return valeurComparaison;
    }

    public void setValeurComparaison(String valeurComparaison) {
        this.valeurComparaison = valeurComparaison;
    }

    public String getOp() {
        return op;
    } 

    public void setOp(String op) {
        this.op = op;
    }
}


    

    

