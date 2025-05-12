import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class RobotLivraison extends RobotConnecte {
    private String colisActuel;
    private String destination;
    private boolean enlivraison;
    private List <Integer> xdest=new ArrayList<>();
    private List <Integer> ydest=new ArrayList<>();

    private static final int ENERGIE_LIVRAISON = 15;
    private static final int ENERGIE_CHARGEMENT = 5;

    public RobotLivraison(String id, int x, int y) {
        super(id, x, y, 100, 0, false);
        this.colisActuel = "0";// colisAcuel normalement une description de colis mais il contient un entier
        this.destination = null;
        this.enlivraison = false;
        this.setPortenvoie("shutdown");
    }

    Scanner scanner = new Scanner(System.in);

    @Override
    public void effectuerTache() throws RobotException {
        if (!isEnMarche()) {
            throw new RobotException("Le robot doit etre demarré pour effectuer une tache.");

        }
        if(isModeeco()) {
            ajouterHistorique("Mode ecologique actif : optimisation des livraisons ...");
            
            if(enlivraison) {
                // Livrer d'abord les colis groupés
                FaireLivraison();
                return;
            }
            
            // Demander si l'utilisateur veut ajouter un colis
            System.out.println("Voulez-vous charger un nouveau colis? (oui/non)");
            String reponse = scanner.nextLine();
            
            
            if(reponse.equalsIgnoreCase("oui")) {
                System.out.println("Entrez la destination du colis : ");
                String dest = scanner.nextLine();
               // scanner.nextLine(); // consommer le retour à la ligne
                
               
                    chargerColis(dest);
                    ajouterHistorique("Colis accepté pour livraison groupée: " + dest);
                
            }
            return;
        }
       
       
        if (enlivraison) {
            FaireLivraison(xdest.get(0),ydest.get(0));

        } 
        
            System.out.println("Veuillez charger un nouveau colis(oui/non).");
            boolean reponseValidee = false;
            while (!reponseValidee) {
                String reponse = scanner.nextLine();
                if (reponse.equalsIgnoreCase("oui")) {
                    if (verifierEnergie(ENERGIE_CHARGEMENT)) {
                        System.out.println("Veuillez entrer la destination du colis : ");
                        
                        String dest = scanner.nextLine();
                       
                        chargerColis(dest);
                        System.out.println("Colis chargé avec succès.");
                        FaireLivraison();
                        System.out.println("Colis livré avec succès.");
                        reponseValidee = true;

                    }
                    else {
                        System.out.println("Energie insuffisante pour charger le colis.");
                        reponseValidee = true;
                    }

                } else if (reponse.equalsIgnoreCase("non")) {
                    System.out.println("Aucun colis à charger.");
                    ajouterHistorique("En attente de colis.");
                    reponseValidee = true;
                } else {
                    System.out.println("Réponse invalide. Veuillez entrer 'oui' ou 'non'.");
                }
            }
            

        }

        public boolean regrouper(int x1,int x2,int y1,int y2){
            
            return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2))<=15;
          
        }
    

    public void FaireLivraison(int Destx, int Desty) throws RobotException {

        if (verifierEnergie(ENERGIE_LIVRAISON)) {
            deplacer(Destx, Desty);
            this.colisActuel = Integer.toString(Integer.parseInt(this.colisActuel) - 1);
            this.enlivraison = false;
            String []destinations=destination.split(" et ");
            ajouterHistorique("Livraison terminée à " + destinations[0]);
        }

    
    }
    public void FaireLivraison()throws RobotException{
        if (verifierEnergie(ENERGIE_LIVRAISON)) {
            for(int i=0;i<xdest.size();i++){
                deplacer(xdest.get(i), ydest.get(i));
                this.colisActuel = Integer.toString(Integer.parseInt(this.colisActuel) - 1);
                if(destination!= null && destination.contains(" et ")){
                String [] destinations=this.destination.split(" et ",2);
                this.destination=destinations[1];}
            }
            xdest.clear();
            ydest.clear();
            this.enlivraison=false;
        }
    }

    @Override
    public void deplacer(int x, int y) throws RobotException {
        float distance = (float) Math.sqrt((x - this.getX()) * (x - this.getX()) + (y - this.getY()) * (y - this.getY()));
        if (distance > 100) {
            if(destination.contains(" et ")){
            
            this.destination=destination.substring(destination.indexOf(" et ")+4);}
            else{
                this.destination=null;
            }
            xdest.remove(0);
            ydest.remove(0);
            throw new RobotException("la distance est superieur à 100 .Desolé, impossible de livrer ce colis");
                


        }
        if (verifierEnergie((int) (0.3 * distance)) && verifierMaintenance()) {
            consommerEnergie((int) (0.3 * distance));
            this.setX(x);
            this.setY(y);

            this.setHeuresUtilisation(getHeuresUtilisation() + (int) distance / 10);
            ajouterHistorique("Le robot est deplacé à (" + this.getX() + "," + this.getY() + ").");

        }

    }

    public void chargerColis(String destination) throws RobotException {
        if (!enlivraison ) {
            if (this.colisActuel.equals("0")) {

                if (verifierEnergie(ENERGIE_CHARGEMENT)) {
                    colisActuel = "1";
                    this.destination = destination;
                    int xdestination;
                int ydestination;
                System.out.println("Entrez la coordonnée x de la destination : ");
                xdestination = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Entrez la coordonnée y de la destination : ");
                ydestination = scanner.nextInt();
                scanner.nextLine();
                xdest.add(xdestination);
                ydest.add(ydestination);
                System.out.println("Colis chargé avec succès en attente d'autre chargements.");
            
            
            }
                else{
                    System.out.println("Energie insuffisante pour charger le colis.");
                }

            }
            else{
                if(verifierEnergie(ENERGIE_CHARGEMENT)){
                
                    
                int xdestination;
                int ydestination;
                System.out.println("Entrez la coordonnée x de la destination : ");
                xdestination = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Entrez la coordonnée y de la destination : ");
                ydestination = scanner.nextInt();
                scanner.nextLine();
                
                if(xdest.isEmpty() || ydest.isEmpty() || regrouper(xdest.get(xdest.size()-1),xdestination,ydest.get(ydest.size()-1),ydestination)){
                    this.destination = this.destination+" et "+destination;
                    xdest.add(xdestination);
                    ydest.add(ydestination);
                    colisActuel = Integer.toString(Integer.parseInt(colisActuel) + 1);
                    consommerEnergie(ENERGIE_CHARGEMENT);
                    ajouterHistorique("nouvelle destination ajoutée: "+destination);
                    System.out.println("Colis regroupé avec succès.");
                }
                else{
                    System.out.println("Regroupement impossible, chargement annulée.");
                    enlivraison=true;
                    FaireLivraison();
                    System.out.println("Colis regroupés sont livrés avec succès.");
                    
                    return;
                }}
                else{
                    System.out.println("Energie insuffisante pour charger le colis.");
                }
            }
                
                  


            }
            else {
                System.out.println("Le robot est déjà en cours de livraison.");
            }
        }
    

    @Override
    public String toString() {
        String destString = this.destination != null ? this.destination.split(" et ")[0] : "Aucune destination";
        String reponse = isConnecte() ? "Oui" : "Non";
        return "RobotIndustriel [ID :" + this.getId() +" , Puissance :"+this.getPuissance()+ " , Position : (" + this.getX() + "," + this.getY()
                + "), Energie : " + this.getEnergie() + "% , Heures : " + this.getHeuresUtilisation() + ", Colis: "
                + colisActuel + ", Destination : " + destString + ", Connecté : " + reponse + " ,Etat du port: "+ getPortenvoie()+" , mode ecologique : " + isModeeco()+" ,Co2 degagé: "+co2degagé()+"g ]";

    }

}
