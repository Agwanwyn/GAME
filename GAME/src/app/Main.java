package app;

import controller.GameController;
import logic.CsvLoader;
import model.SyllableTask;
import ui.GamePanel;

import java.util.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args) {

        List<SyllableTask> tasks = CsvLoader.loadTasks("D:\\AIP\\work\\GAME\\resources\\letters.csv");

        GameController controller = new GameController(tasks);

        System.out.println(controller.getCommandPhrase());

        GamePanel panel = new GamePanel(controller);

        
        JFrame frame = new JFrame("Стройка слогов");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        controller.moveCraneLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        controller.moveCraneRight();
                        break;
                    case KeyEvent.VK_SPACE:
                        controller.handleSpace();
                        break;
                }
                panel.refresh();
            }
        });
    }
}


