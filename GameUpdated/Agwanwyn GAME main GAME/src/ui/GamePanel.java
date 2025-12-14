package ui;

import model.Block;
import model.Crane;
import model.Warehouse;
import controller.GameController;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel {
	
	private GameController controller;


    private BufferedImage background; 

    public GamePanel(GameController controller) {
        this.controller = controller;


        setPreferredSize(new Dimension(600, 400));
        
        try {
        	 background = ImageIO.read(
        		        getClass().getClassLoader()
        		            .getResourceAsStream("images\\background.png")
            );
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Фон не найден, используется цвет.");
            background = null;
            setBackground(new Color(210, 230, 255));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        }
        
        

        // ======= РИСУЕМ ПЛОЩАДКУ =======
        g.setColor(new Color(170, 140, 90));
        g.fillRect(0, 340, 600, 60);
        drawTaskText(g);

     // ======= СКЛАД =======
        g.setColor(new Color(0, 0, 128));
        g.fillRect(0, 140, 600, 30);

        // ======= КРАН (ТЕПЕРЬ НАД СКЛАДОМ) =======
        drawCrane(g);

        Crane crane = controller.getCrane();


        // Если кран несёт блок — рисуем его под крюком
        if (crane.getCarriedBlock() != null) {
            drawBlock(g, crane.getX() + 10, crane.isHookDown() ? 260 : 120,
                    crane.getCarriedBlock().getLetter(), crane.getCarriedBlock().getColor());
        }
        
        Warehouse warehouse = controller.getWarehouse();
        Block placedBlock = controller.getPlacedBlock();
        // ======= ПЕРВАЯ БУКВА НА ПЛОЩАДКЕ =======
        drawBlock(g, placedBlock.getX(), placedBlock.getY(), placedBlock.getLetter(), placedBlock.getColor());

        // ======= БЛОК НА СКЛАДЕ =======
        for (Block b : warehouse.getBlocks()) {
            drawBlock(g, b.getX(), b.getY(), b.getLetter(), b.getColor());
        }

        Block placed = controller.getSecondPlacedBlock();

        if (placed != null) {
            drawBlock(
                g,
                placed.getX(),
                placed.getY(),
                placed.getLetter(),
                placed.getColor()
            );
        }



    }

    private void drawBlock(Graphics g, int x, int y, String letter, Color color) {
    	g.setColor(color);
        g.fillRect(x, y, 40, 40);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 40, 40);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(letter, x + 12, y + 28);
    }
    
    private void drawCrane(Graphics g) {
    	Crane crane = controller.getCrane();
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(255, 215, 0)); // жёлтый кран

        int craneX = crane.getX();

        // ===== БАШНЯ =====
        g2.fillRect(craneX + 45, 20, 10, 120);
        g2.setColor(Color.BLACK);
        g2.drawRect(craneX + 45, 20, 10, 120);
        // ===== СТРЕЛА =====
        g2.setColor(new Color(255, 215, 0));
        g2.fillRect(craneX - 60, 40, 180, 8);
        g2.setColor(Color.BLACK);
        g2.drawRect(craneX - 60, 40, 180, 8);
        // ===== КАБИНА =====
        g2.setColor(Color.BLACK);
        g2.fillRect(craneX + 60, 28, 28, 20);

        // ===== ТЕЛЕЖКА =====
        g2.setColor(Color.BLACK);
        g2.fillRect(craneX, 40, 10, 8);

        // ===== ТРОС =====
        int hookBottom = crane.isHookDown() ? 260 : 80;
        g2.drawLine(craneX + 5, 48, craneX + 5, hookBottom);

        // ===== КРЮК =====
        g2.drawArc(craneX, hookBottom, 10, 12, 0, -180);
    }
    
    
    private void drawTaskText(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        String text = controller.getCommandPhrase();

        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.BLACK);

        g2.drawString(text, 200, 370);
    }



    public void refresh() {
        repaint();
    }
    
    
}
