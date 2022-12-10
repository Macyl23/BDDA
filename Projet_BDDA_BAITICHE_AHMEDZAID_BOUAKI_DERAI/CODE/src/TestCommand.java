public class TestCommand {
    public static void main(String[] args){
        String commande = "SELECT * FROM R,S WHERE R.C1=S.AA AND R.C1<S.BB";
        SelectJoinCommand c = new SelectJoinCommand(commande);
        System.out.println(c.relationUne+" "+c.relationDeux);

        
        System.out.println(c.listeConditions(c.commande[1]).toString());
    }
}
