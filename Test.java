import java.util.Scanner;

public class Test {
    
    private static Scanner scanner = new Scanner(System.in);
    private static RobotLivraison robot;
    
    public static void main(String[] args) {
        System.out.println("=== Test du Robot de Livraison ===");
        System.out.print("Entrez l'ID du robot: ");
        String id = scanner.nextLine();
        robot = new RobotLivraison(id, 0, 0);
        new Thread(robot).start();
        
        boolean quitter = false;
        while (!quitter) {
            afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine();
            
            try {
                switch (choix) {
                    case 1: 
                        robot.demarrer();
                        System.out.println("Robot démarré avec succès.");
                        break;
                    case 2:
                        robot.arreter();
                        System.out.println("Robot arrêté avec succès.");
                        break;
                    case 3:
                        chargerColis();
                        break;
                    case 4:
                        effectuerLivraison();
                        break;
                    case 5:
                        connecterRobot();
                        break;
                    case 6:
                        robot.deconnecter();
                        System.out.println("Robot déconnecté.");
                        break;
                    case 7:
                        envoyerDonnees();
                        break;
                    case 8:
                        rechargerRobot();
                        break;
                    case 9:
                        System.out.println(robot.getHistorique());
                        break;
                    case 10:
                        robot.effectuerTache();
                        break;
                    case 11:
                        robot.activerModeEco();
                        break;
                    case 12:
                        robot.desactiverModeEco();
                        break;
                    case 0:
                        quitter = true;
                        System.out.println("Au revoir!");
                        break;
                    default:
                        System.out.println("Choix invalide!");
                }
            } catch (RobotException e) {
                System.err.println("Erreur: " + e.getMessage());
            }
            
            System.out.println("\nÉtat actuel du robot:");
            System.out.println(robot);
            System.out.println("-----------------------");
        }
        
        scanner.close();
    }
    
    private static void afficherMenu() {
        System.out.println("\n=== Menu de test ===");
        System.out.println("1. Démarrer le robot");
        System.out.println("2. Arrêter le robot");
        System.out.println("3. Charger un colis");
        System.out.println("4. Effectuer une livraison");
        System.out.println("5. Connecter au réseau");
        System.out.println("6. Déconnecter du réseau");
        System.out.println("7. Envoyer des données");
        System.out.println("8. Recharger la batterie");
        System.out.println("9. Afficher l'historique");
        System.out.println("10. Effectuer tâche (interactif)");
        System.out.println("11. Activer mode ecologique");
        System.out.println("12. Désactiver mode ecologique");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }
    
    private static void chargerColis() throws RobotException {
        System.out.print("Entrez la destination: ");
        String destination = scanner.nextLine();
        robot.chargerColis(destination);
        System.out.println("Colis chargé pour " + destination);
    }
    
    private static void effectuerLivraison() throws RobotException {
        
        robot.FaireLivraison();
        System.out.println("Livraison effectuée aux coordonnées (" + robot.getX() + "," + robot.getY() + ")");
    }
    
    private static void connecterRobot() throws RobotException {
        System.out.print("Entrez le nom du réseau: ");
        String reseau = scanner.nextLine();
        robot.connecter(reseau);
        System.out.println("Robot connecté au réseau " + reseau);
    }
    
    private static void envoyerDonnees() throws RobotException {
        System.out.print("Entrez les données à envoyer: ");
        String donnees = scanner.nextLine();
        robot.envoyerDonnees(donnees);
        System.out.println("Données envoyées avec succès");
    }
    
    private static void rechargerRobot() {
        System.out.print("Entrez la quantité d'énergie à recharger (%): ");
        int quantite = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne
        robot.recharger(quantite);
        System.out.println("Robot rechargé de " + quantite + "%");
    }

}
    
