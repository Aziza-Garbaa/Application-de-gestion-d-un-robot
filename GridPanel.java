import javax.swing.*;
import java.awt.*;
import java.io.File;

class GridPanel extends JPanel {
    private int rows = 100;
    private int cols = 100;
    private int robotX = 0;
    private int robotY = 0;
    private Image robotImage;

    public GridPanel() {
        // Charge l'image du robot depuis le fichier "robot.png"
        File f = new File("robot.png");
System.out.println("Fichier robot.png trouvé ? " + f.exists() + " - Chemin : " + f.getAbsolutePath());

        ImageIcon icon = new ImageIcon("robot.png");
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            robotImage = new ImageIcon(getClass().getResource("/robot.png")).getImage();

            System.out.println("✅ Image robot.png chargée avec succès.");
        } else {
            System.out.println("❌ Échec de chargement de robot.png");
            robotImage = null;
        }
    }

    public void setRobotPosition(int x, int y) {
        this.robotX = x;
        this.robotY = y;
        repaint();  // Redessine la grille avec la nouvelle position
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        // Dessiner la grille
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }

        // Dessiner le robot à la position actuelle
        int robotPixelX = robotX * cellWidth;
        int robotPixelY = robotY * cellHeight;

        if (robotImage != null) {
            g.drawImage(robotImage, robotPixelX, robotPixelY, cellWidth, cellHeight, this);
        } else {
            g.setColor(Color.RED); // Fallback si l'image n'est pas chargée
            g.fillOval(robotPixelX, robotPixelY, cellWidth, cellHeight);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 1000);
    }
}
