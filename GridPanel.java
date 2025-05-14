import javax.swing.*;
import java.awt.*;
import java.io.File;//bibliotheque pour manipuler les fichier

class GridPanel extends JPanel {
private int rows = 100;
private int cols = 100;
private int robotX = 0;
private int robotY = 0;
private Image robotImage;
public GridPanel() {
    
    File f = new File("robot.png");
    System.out.println("Fichier robot.png trouvé ? " + f.exists() + " - Chemin : " + f.getAbsolutePath());//verification de l'existence de l'image stocké dans l'objet file au cas où

    ImageIcon icon = new ImageIcon("robot.png");//charger l'image 
    if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {//verifier si l'image est chargé correctement en effet MediaTracker.COMPLETE est une constante qui represente chargement terminé avec succes
        robotImage = new ImageIcon(getClass().getResource("/robot.png")).getImage();//recupère l'image depuis  le ressource de class path

        System.out.println("✅ Image robot.png chargée avec succès.");
    } else {
        System.out.println("❌ Échec de chargement de robot.png");
        robotImage = null;
    }
}

public void setRobotPosition(int x, int y) {
    this.robotX = x;//mettre a jour de position de robot
    this.robotY = y;
    repaint();  // redessiner le panneau apres mise a jour
}

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);//essentiel pour le nettoyage d'ancien panneau
    int cellWidth = getWidth() / cols;
    int cellHeight = getHeight() / rows;
    //definir les dimensions de chaque cellule

    g.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);//dessine un rectangle pour chaque cellule
        }
    }

    
    int robotPixelX = robotX * cellWidth;//la position en pixels
    int robotPixelY = robotY * cellHeight;

    if (robotImage != null) {
        g.drawImage(robotImage, robotPixelX, robotPixelY, cellWidth, cellHeight, this);//affichage de l'image à la position calculé en pixels
    } else {
        g.setColor(Color.RED); //en cas de probème l'image n'est pas chargé le robot representé par une boule rouge
        g.fillOval(robotPixelX, robotPixelY, cellWidth, cellHeight);
    }
}

@Override
public Dimension getPreferredSize() {
    return new Dimension(500, 1000);//dimension de panneau
}
}
