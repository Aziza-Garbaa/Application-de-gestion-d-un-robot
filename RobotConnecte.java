

public abstract class RobotConnecte extends Robot implements Connectable {
  private boolean connecte;
  private String reseauConnecte;
  public RobotConnecte ( String id, int x, int y, int energie, int heuresUtilisation, boolean EnMarche,boolean connecte,String reseauConnecte) {
    super(id,x,y,energie,heuresUtilisation,EnMarche);
    this.connecte=connecte;
    this.reseauConnecte=reseauConnecte;

  }  
  public RobotConnecte ( String id, int x, int y, int energie, int heuresUtilisation, boolean EnMarche) {
    super(id,x,y,energie,heuresUtilisation,EnMarche);
    this.connecte=false;
    this.reseauConnecte=null;

  }  
  public boolean isConnecte() {
    return connecte;
  }
  public void setConnecte(boolean connecte) {
    this.connecte = connecte;
  }
    public String getReseauConnecte() {
        return reseauConnecte;
    }
    public void setReseauConnecte(String reseauConnecte) {
        this.reseauConnecte = reseauConnecte;
    }
  @Override
  public void connecter(String reseau)throws RobotException {
    
    verifierMaintenance();
    if(verifierEnergie(5)){
        
    this.reseauConnecte=reseau;
    this.connecte=true;
    this.consommerEnergie(5);
    this.ajouterHistorique("le robot est maintenant connecté au Reseau: "+reseau);

    
  }}
  @Override 
  public void deconnecter() {
      if(!connecte){
        ajouterHistorique("Echec de deconnexion: le robot est deja non connecté à un reseau");
      }
      String res=reseauConnecte;
      this.reseauConnecte=null;
      this.connecte=false;
      ajouterHistorique("le Robot est deconnecté du reseau:"+res);
      
  }
  @Override
  public void envoyerDonnees(String donnees) throws RobotException{
    verifierMaintenance();
    if(!connecte){
        ajouterHistorique("Echec d'envoie de données:Le robot n'est pas connecté à un reseau");
        throw new RobotException("Il faut connecter d'abord à un reseau");
    }
    if(verifierEnergie(3)){

        consommerEnergie(3);
        ajouterHistorique("Envoie de donnée:"+donnees+" au reseau :"+reseauConnecte);
    }

  }

    
    
}
