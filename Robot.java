import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Robot {
    private String id;
    private int x;
    private int y;
    private int energie;//l'energie varie de 0 à 100
    private int heuresUtilisation;
    private boolean enMarche;
    private boolean modeeco=false;
    private int puissance;
    private List<String> historiqueActions;
    public Robot(String id, int x, int y, int energie, int heuresUtilisation, boolean EnMarche,int puissance) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.energie = energie;
        this.heuresUtilisation = heuresUtilisation;
        this.enMarche = EnMarche;
        this.historiqueActions = new ArrayList<>();
        ajouterHistorique("Robot créé");
        
        this.puissance = puissance;
        
       
       
        ajouterHistorique("id du robot: "+id+" puissance: "+puissance+" KWh");

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getEnergie() {
        return energie;
    }
    public void setEnergie(int energie) {
        this.energie = energie;
    }
    public int getHeuresUtilisation() {
        return heuresUtilisation;
    }
    public void setHeuresUtilisation(int heuresUtilisation) {
        this.heuresUtilisation = heuresUtilisation;
    }
    public boolean isEnMarche() {
        return enMarche;
    }
    public void setEnMarche(boolean enMarche) {
        this.enMarche = enMarche;
    }
    public void ajouterHistorique(String action){
        Date datetime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy HH :mm :ss", Locale.FRENCH);
        historiqueActions.add(format.format(datetime)+" "+action);
    }
    public boolean verifierEnergie(int energieRequise) throws EnergieInsuffisanteException{
        if (energieRequise > energie) 
           { throw new EnergieInsuffisanteException("Energie insuffisante pour effectuer l'action. Veuillez recharger le robot."); }
        return true;
        
    }
    public boolean verifierMaintenance() throws MaintenanceRequiseException{
        if (heuresUtilisation > 100) {
            throw new MaintenanceRequiseException("Le robot nécessite une maintenance.");
        }
        return true;
    }
    public void demarrer() throws RobotException{
        if (enMarche) {
            ajouterHistorique("Echec de demarrage du robot: Le robot est déja allumé.");
            throw new RobotException("Echec de demarrage : Le robot est déjà allumé.");
        }
        else if (energie < 10) {
            ajouterHistorique("Echec de demarrage du robot: energie insuffisante( "+energie+" % ).");
            throw new RobotException("Energie insuffisante pour démarrer le robot.");
        }
        
            enMarche = true;
            ajouterHistorique("Le robot a démarré avec "+energie+" % d'energie.");
        
       
    }
    public void arreter()throws RobotException{
        if (!enMarche) {
            ajouterHistorique("Echec d'arret du robot: le robot est déja éteint.");
            throw new RobotException("Echec d'arret : Le robot est déjà arrêté.");
        }
            enMarche = false;
            ajouterHistorique("Le robot a été arrêté.");
        
           
    }
    public void consommerEnergie(int energieConsommé) throws  RobotException,IllegalArgumentException{
        if (energieConsommé < 0) {
            ajouterHistorique("Echec de consommation d'énergie: la valeur doit être positive.");
            throw new IllegalArgumentException("La quantité d'énergie à consommer doit être positive.");
        }
        if (energieConsommé > energie) {
            energie=0;
            ajouterHistorique("Le robot a consommé toute son énergie. Il s'arrete automatiquement.");
            this.arreter();
            throw new EnergieInsuffisanteException("Energie insuffisante pour Effectuer cette opération .le robot est maintenant éteint.");
        }
        if(modeeco) {
            energieConsommé = (int) (energieConsommé * 0.8); 
            ajouterHistorique("Mode écologique activé. Consommation d'énergie réduite de 20 %.");
        }
        energie -= energieConsommé;
        ajouterHistorique("Le robot a consommé " + energieConsommé + " % d'énergie. Energie restante: " + energie + " %.");
       
    } 
    public void recharger(int quantite) throws IllegalArgumentException{
        if (quantite < 0) {
            ajouterHistorique("Echec de recharge: la valeur doit être positive.");
            throw new IllegalArgumentException("La quantité d'énergie à recharger doit être positive.");
        }
        if (energie + quantite > 100) {
                   JFrame fen = new JFrame();
                   
             JOptionPane.showMessageDialog(fen, "Robot rechargé à 100%", "Succès", JOptionPane.INFORMATION_MESSAGE);
            ajouterHistorique("Le robot est complètement rechargé à 100 % d'énergie.");
        } else {
            energie += quantite;
            JFrame fen=new JFrame();
             JOptionPane.showMessageDialog(fen, "Robot rechargé de " + quantite + "%", "Succès", JOptionPane.INFORMATION_MESSAGE);
            ajouterHistorique("Le robot a été rechargé de " + quantite + " % d'énergie. Energie actuelle: " + energie + " %.");
        }
    }
    public abstract void deplacer(int x, int y) throws RobotException;
    public abstract void effectuerTache() throws RobotException;
    public String getHistorique(){
        StringBuilder historique = new StringBuilder("Historique des actions du robot " + id + ":\n");
        for (String action : historiqueActions) {
            historique.append(action).append("\n");
        }
        if (historique.isEmpty()){
            return "Aucun Historique";

        }
        return historique.toString();

    }
    @Override
    public String toString(){
        return "RobotIndustriel [ID :"+id+" , Position : (" + x +","+y+"), Energie : "+energie+"%, Heures : "+heuresUtilisation+"]";
    }


    public boolean isModeeco() {
        return modeeco;
    }
    public void setModeeco(boolean modeeco) {
        this.modeeco = modeeco;
    }
    public void activerModeEco() throws RobotException{
        if (modeeco) {
            ajouterHistorique("Echec d'activation du mode écologique: le mode écologique est déjà activé.");
            throw new RobotException("Echec d'activation du mode écologique: le mode écologique est déjà activé.");
        }
        modeeco = true;
        ajouterHistorique("Mode écologique activé.");
    }
    public void desactiverModeEco() throws RobotException{
        if (!modeeco) {
            ajouterHistorique("Echec de désactivation du mode éco: le mode éco est déjà désactivé.");
            throw new RobotException("Echec de désactivation du mode éco: le mode éco est déjà désactivé.");
        }
        modeeco = false;
        ajouterHistorique("Mode éco désactivé.");
    }
    public int getPuissance() {
        return puissance;
    }
    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }
    public double co2degagé() {
        
        double consommation_electrique= this.puissance * this.heuresUtilisation/1000.0;
        ajouterHistorique("co2 degagé jusqu'à ce moment là est : "+consommation_electrique*475+" g");
        return consommation_electrique * 475; //En Tunisie, le facteur d'émission du réseau électrique (CO₂ par kWh) est entre 450-500 CO₂/kWh
        
    }
    
    
    




    


}