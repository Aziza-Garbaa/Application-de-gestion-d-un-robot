import java.awt.*;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Test {
    
    private static Scanner scanner = new Scanner(System.in);
    
    
  public static void main(String[] args) {
    JFrame fenetre = new JFrame();
    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fenetre.setSize(1000, 1000);
    fenetre.setTitle("Gestion du Robot");
    fenetre.setLocationRelativeTo(null);

    Container fen = fenetre.getContentPane();
    JTabbedPane onglets = new JTabbedPane();
    fen.add(onglets);

    JButton ad = new JButton("Nouveau robot");
    ad.setPreferredSize(new Dimension(150, 40));

    // Création du panneau principal avec BorderLayout
    JPanel pan1 = new JPanel(new BorderLayout());

    // Label supérieur
    pan1.add(new JLabel("Bienvenue dans l'application de gestion du robot", SwingConstants.CENTER), BorderLayout.NORTH);

    // Crée un panneau central vertical (BoxLayout)
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Ajoute des marges autour du panneau
    centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

    JLabel instruction = new JLabel("Cliquez sur le bouton ci-dessous pour créer un nouveau robot");
    instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
    centerPanel.add(instruction);

    centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // espace vertical

    JPanel boutonPanel = new JPanel();
    boutonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    boutonPanel.add(ad);

    centerPanel.add(boutonPanel);

    pan1.add(centerPanel, BorderLayout.CENTER);

    onglets.addTab("Home", pan1);

    // Action du bouton
    ad.addActionListener(e -> {
        RobotLivraison robot;
    String name = JOptionPane.showInputDialog(fen, "Entrez L'iD du robot:");
    while (name != null && name.trim().isEmpty()) {
        JOptionPane.showMessageDialog(fen, "Nom invalide. Entrez le nom du robot.", "Erreur", JOptionPane.ERROR_MESSAGE);
        name = JOptionPane.showInputDialog(fen, "Entrez le nom du robot:");
    }

    if (name != null) {
        int puissance = Integer.parseInt(JOptionPane.showInputDialog(fen, "Entrez la puissance de robot entre 1 et 10 KWh"));
        while (puissance < 1 || puissance > 10) {
            JOptionPane.showMessageDialog(fen, "Vous devez entrer une puissance entre 1 et 10 KWh", "Erreur", JOptionPane.ERROR_MESSAGE);
            puissance = Integer.parseInt(JOptionPane.showInputDialog(fen, "Entrez la puissance de robot entre 1 et 10 KWh"));
        }

        robot = new RobotLivraison(name, 0, 0, puissance);
        new Thread(robot).start();
         // SPLIT PRINCIPAL : GAUCHE (peut être vide) + DROITE
        GridPanel grille = new GridPanel();
        grille.setPreferredSize(grille.getPreferredSize());
JPanel gauche = new JPanel(new BorderLayout());
    

        // PANEL PRINCIPAL DU ROBOT
        JPanel robotPanel = new JPanel(new BorderLayout());
        robotPanel.add(new JLabel("Espace du robot : " + name, SwingConstants.CENTER), BorderLayout.NORTH);

        // PANEL DROITE = CONTIENDRA dr
        JPanel droite = new JPanel(new BorderLayout());

        // BOUTONS EN HAUT
        JPanel haut = new JPanel(new GridLayout(6, 2, 5, 5));
        String[] nomsBoutons = {"Démarrer", "Arrêter", "Charger", "Livrer", "Connecter", "Déconnecter", "Envoyer", "Recharger", "Tâche", "Mode Éco", "Mode Normal"};
       JButton demarrer = new JButton("Démarrer");
        JButton arreter = new JButton("Arrêter");
        JButton charger = new JButton("Charger");
        JButton livrer = new JButton("Livrer");
        JButton connecter = new JButton("Connecter");
        JButton deconnecter = new JButton("Déconnecter");
        JButton envoyer = new JButton("Envoyer");
        JButton recharger = new JButton("Recharger");
        JButton tache = new JButton("Tâche");
        JButton modeEco = new JButton("Mode Éco");
        JButton modeNormal = new JButton("Mode Normal");
        while (haut.getComponentCount() < 12) {
            haut.add(new JLabel(""));
        }
        
        // TEXTAREA EN BAS
        JTextArea historique = new JTextArea();
      

        haut.add(demarrer);
        demarrer.addActionListener(event -> {
            try {
                robot.demarrer();
                grille.setRobotPosition(0, 0);

                JOptionPane.showMessageDialog(fen, "Robot démarré avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                mettreAJourHistorique(historique,robot);
            } catch (RobotException ex) {
                JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        haut.add(arreter); 
        arreter.addActionListener(event -> {
            try {
                robot.arreter();
                JOptionPane.showMessageDialog(fen, "Robot arrêté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                 mettreAJourHistorique(historique,robot);
            } catch (RobotException ex) {
                JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }); 
        haut.add(charger);
        charger.addActionListener(event -> {
            String destination = JOptionPane.showInputDialog(fen, "Entrez la destination:");
            if (destination != null && !destination.trim().isEmpty()) {
                try {
                    robot.chargerColis(destination);
                     mettreAJourHistorique(historique,robot);
                } catch (RobotException ex) {
                    JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        haut.add(livrer);
        livrer.addActionListener(event -> {
            try {
                robot.FaireLivraison();
                grille.setRobotPosition(robot.getX(), robot.getY());

                JOptionPane.showMessageDialog(fen, "Livraison effectuée aux coordonnées (" + robot.getX() + "," + robot.getY() + ")", "Succès", JOptionPane.INFORMATION_MESSAGE);
                 mettreAJourHistorique(historique,robot);
            } catch (RobotException ex) {
                JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        haut.add(connecter);
        connecter.addActionListener(event -> {
            String reseau = JOptionPane.showInputDialog(fen, "Entrez le nom du réseau:");
            if (reseau != null && !reseau.trim().isEmpty()) {
                try {
                    robot.connecter(reseau);
                    JOptionPane.showMessageDialog(fen, "Robot connecté au réseau " + reseau, "Succès", JOptionPane.INFORMATION_MESSAGE);
                     mettreAJourHistorique(historique,robot);
                } catch (RobotException ex) {
                    JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        haut.add(deconnecter);
        deconnecter.addActionListener(event -> {
           
                robot.deconnecter();
                JOptionPane.showMessageDialog(fen, "Robot déconnecté.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                 mettreAJourHistorique(historique,robot);
           
        });
        haut.add(envoyer);
        envoyer.addActionListener(event -> {
            String donnees = JOptionPane.showInputDialog(fen, "Entrez les données à envoyer:");
            if (donnees != null && !donnees.trim().isEmpty()) {
                try {
                    robot.envoyerDonnees(donnees);
                    JOptionPane.showMessageDialog(fen, "Données envoyées avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                     mettreAJourHistorique(historique,robot);
                } catch (RobotException ex) {
                    JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        haut.add(recharger);
        recharger.addActionListener(event -> {
            String quantiteStr = JOptionPane.showInputDialog(fen, "Entrez la quantité d'énergie à recharger (%):");
            if (quantiteStr != null && !quantiteStr.trim().isEmpty()) {
                try {
                    int quantite = Integer.parseInt(quantiteStr);
                    robot.recharger(quantite);
                     mettreAJourHistorique(historique,robot);
                   
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(fen, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        haut.add(tache);
        tache.addActionListener(event -> {
            try {
                robot.effectuerTache();
                grille.setRobotPosition(robot.getX(), robot.getY());

                 mettreAJourHistorique(historique,robot);
            } catch (RobotException ex) {
                JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        haut.add(modeEco);
        modeEco.addActionListener(event -> {
            try {
                robot.activerModeEco();
                JOptionPane.showMessageDialog(fen, "Mode écologique activé.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                 mettreAJourHistorique(historique,robot);
            } catch (RobotException ex) {
                JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        haut.add(modeNormal);
        modeNormal.addActionListener(event -> {
            try {
                robot.desactiverModeEco();
                JOptionPane.showMessageDialog(fen, "Mode normal activé.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                 mettreAJourHistorique(historique,robot);
            } catch (RobotException ex) {
                JOptionPane.showMessageDialog(fen, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });



        historique.setEditable(false);
        JScrollPane scroll = new JScrollPane(historique);

        // SPLIT VERTICAL = BOUTONS EN HAUT + TEXTAREA EN BAS
        JSplitPane splitVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, haut, scroll);
        splitVertical.setDividerLocation(250);
        splitVertical.setResizeWeight(0.7);
        droite.add(splitVertical, BorderLayout.CENTER);
        

       
gauche.add(grille, BorderLayout.CENTER);
 // ou une carte par exemple
        JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gauche, droite);
        splitPrincipal.setDividerLocation(300);
       // splitPrincipal.setResizeWeight(0.3);
        splitPrincipal.setResizeWeight(0.5);
SwingUtilities.invokeLater(() -> {
    splitPrincipal.setDividerLocation(0.5);
});


        robotPanel.add(splitPrincipal, BorderLayout.CENTER);

        // ÉCOUTEUR SUR HISTORIQUE
       /*   historique.getDocument().addDocumentListener(new DocumentListener() {
           @Override public void insertUpdate(DocumentEvent e) {
            
           }
            @Override public void removeUpdate(DocumentEvent e) {
                historique.append(robot.getHistorique() + "\n");
            }
            @Override public void changedUpdate(DocumentEvent e) {
                historique.append(robot.getHistorique() + "\n");
            }
        });*/

        onglets.addTab(name, robotPanel);
        onglets.setSelectedIndex(onglets.getTabCount() - 1);
    }
});


    fenetre.setVisible(true);



      
}
  private static void mettreAJourHistorique(JTextArea historique,RobotLivraison robot) {
    historique.setText(robot.getHistorique());
    return;
}
}