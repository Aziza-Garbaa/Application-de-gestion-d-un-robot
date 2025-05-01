import java.util.Scanner;

import javax.print.attribute.standard.Destination;

public class RobotLivraison extends RobotConnecte {
    private String colisActuel;
    private String destination;
    private boolean enlivraison;
    private static final int ENERGIE_LIVRAISON = 15;
    private static final int ENERGIE_CHARGEMENT=5;

    public RobotLivraison(String id ,int x ,int y){
        super(id, x, y, 100, 0, false);
        this.colisActuel = "0";//colisAcuel normalement une description de colis mais il contient un entier
        this.destination = null;
        this.enlivraison = false;
    }

    
    @Override
    public void effectuerTache() throws RobotException {
        if(!isEnMarche()){
            throw new RobotException("Le robot doit etre demarré pour effectuer une tache.");

        }
        if(enlivraison){
            
            int xdestination;
            int ydestination;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez la coordonnée x de la destination : ");
            xdestination = scanner.nextInt();
            System.out.println("Entrez la coordonnée y de la destination : ");
            ydestination = scanner.nextInt();

            FaireLivraison(xdestination, ydestination);

        }
        else{
            System.out.println("Veuillez charger un nouveau colis(oui/non).");
            Scanner scanner = new Scanner(System.in);
           String reponse = scanner.nextLine();
            boolean reponseValidee = false;
            while(!reponseValidee){
                if(reponse.equalsIgnoreCase("oui")){
                    if(verifierEnergie(ENERGIE_CHARGEMENT)){
                        System.out.println("Veuillez entrer la destination du colis : ");
                        String dest= scanner.nextLine();
                        chargerColis(dest);
                        reponseValidee = true;
                        
                    }
                    
                }
                else if(reponse.equalsIgnoreCase("non")){
                    System.out.println("Aucun colis à charger.");
                    ajouterHistorique("En attente de colis.");
                    reponseValidee = true;
                }
                else{
                    System.out.println("Réponse invalide. Veuillez entrer 'oui' ou 'non'.");
                }
            }
            

    }
}
public void FaireLivraison(int Destx,int Desty) throws RobotException{
    if(!verifierEnergie(ENERGIE_LIVRAISON)){
        throw new RobotException("Energie insuffisante pour effectuer la livraison.");
    }
    deplacer(Destx, Desty);
    this.colisActuel="0";
    this.enlivraison=false;
    ajouterHistorique("Livraison terminée à "+destination);
    

}
@Override
public void deplacer(int x,int y) throws RobotException{
 float distance=(float)Math.sqrt((x-this.getX())*(x-this.getX())+(y-this.getY())*(y-this.getY()));
 if(distance>100){
    throw new RobotException("la distance est superieur à 100 .Desolé, impossible de livrer ce colis");

 }
 if(verifierEnergie((int)(0.3 * distance)) && verifierMaintenance()){
        consommerEnergie((int)(0.3 * distance));
        this.setX(x);
        this.setY(y);
       
        this.setHeuresUtilisation(getHeuresUtilisation()+(int)distance/10);
        ajouterHistorique("Le robot est deplacé à ("+this.getX()+","+this.getY()+").");

    }
    

 
}
public void chargerColis(String destination)throws RobotException{
    if(!enlivraison){
        if(this.colisActuel.equals("0")){
            if(verifierEnergie(ENERGIE_CHARGEMENT)){
                colisActuel="1";
                this.destination=destination;
                consommerEnergie(ENERGIE_CHARGEMENT);
                enlivraison=true;
                ajouterHistorique("chargement du colis à destination de "+destination);
            }

        }
    }
}
@Override
public String toString(){
    String reponse = isConnecte() ? "Oui" : "Non";
    return "RobotIndustriel [ID :"+this.getId()+" , Position : (" + this.getX() +","+this.getY()+"), Energie : "+this.getEnergie()+"%, Heures : "+this.getHeuresUtilisation()+" Colis: "+colisActuel+" Destination : "+this.destination+" Connecté : "+reponse+" ]";

}}