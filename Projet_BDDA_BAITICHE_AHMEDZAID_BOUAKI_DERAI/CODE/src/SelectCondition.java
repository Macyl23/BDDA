public class SelectCondition {
    private int indiceColonne;
    public int getIndiceColonne() {
        return indiceColonne;
    }

    public void setIndiceColonne(int indiceColonne) {
        this.indiceColonne = indiceColonne;
    }

    private static final String[] operateur = {"=","<",">","<>","<=",">="};
    public static String[] getOperateur() {
        return operateur;
    }

    private String valeurComparaison;
    public String getValeurComparaison() {
        return valeurComparaison;
    }

    public void setValeurComparaison(String valeurComparaison) {
        this.valeurComparaison = valeurComparaison;
    }

    private String op;

    public String getOp() {
        return op;
    } 

    public void setOp(String op) {
        this.op = op;
    }

    public SelectCondition(int iColonne,String op, String valeurComparaison) {
        this.indiceColonne = iColonne;
        this.valeurComparaison = valeurComparaison;
        this.op=op;
    }

    public boolean verifConditionNombre(Float valeurRecord, Float valeurCondition,String op){
        switch (op){
            case "=":
			return valeurCondition == valeurRecord;
            case "<":
                return valeurCondition < valeurRecord;
            case ">":
                return valeurCondition > valeurRecord;
            case "<=":
                return valeurCondition <= valeurRecord;
            case ">=":
                return valeurCondition >= valeurRecord;
            case "<>":
                return valeurCondition != valeurRecord;
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }

    public boolean verifConditionString(String valeurRecord, String valeurCondition,String op){
        switch (op){
            case "=":
			return valeurCondition.equals(valeurRecord);
            case "<":
                return valeurCondition.compareTo(valeurRecord) < 0;
            case ">":
                return valeurCondition.compareTo(valeurRecord) > 0;
            case "<=":
                return valeurCondition.compareTo(valeurRecord) <= 0;
            case ">=":
                return valeurCondition.compareTo(valeurRecord) >= 0;
            case "<>":
                return valeurCondition.compareTo(valeurRecord) != 0;
            default:
                System.out.println("Operateur incorrect");
                return false;
        }
    }
}
    

    

