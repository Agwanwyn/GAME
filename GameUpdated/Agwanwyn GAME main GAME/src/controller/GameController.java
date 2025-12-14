package controller;

import model.Block;
import model.Crane;
import model.Warehouse;
import model.SyllableTask;
import logic.CollisionChecker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.Color;
import javax.swing.Timer;



public class GameController {

    private Crane crane;
    private Block placedBlock;    
    private SyllableTask task;
    private Warehouse warehouse;
    private Block secondPlacedBlock;
    private List<SyllableTask> tasks;
    private int taskIndex = 0;

    private static final Color[] COLORS = {
    		new Color(255, 165, 0),
    		new Color(30, 144, 255),
    		new Color(255, 160, 122),
    		new Color(50, 205, 50),
    		new Color(147, 112, 219),
    		new Color(255, 215, 0),
    		new Color(0, 139, 139)
    	};

    public GameController(List<SyllableTask> tasks) {
        this.tasks = tasks;
        this.task = tasks.get((int)(Math.random()*49));
        initTask();

        crane = new Crane(70); 
        warehouse = new Warehouse();
        
        placedBlock = new Block(task.getFirstLetter(), 250, 300, COLORS[(int)(Math.random() * COLORS.length)]);
        createWarehouseBlocks();

        
        System.out.println("Первая буква на площадке: " + placedBlock.getLetter());
    }

    public String getCommandPhrase() {
        return task.getCommand();
    }

    public void moveCraneLeft() {
        crane.moveLeft();
        System.out.println("Кран X: " + crane.getX());
    }

    public void moveCraneRight() {
        crane.moveRight();
        System.out.println("Кран X: " + crane.getX());
    }

    public Crane getCrane() { return crane; }
    public Block getPlacedBlock() { return placedBlock; }
    

    
    public void handleSpace() {
    	if (!crane.isHookDown() && crane.getCarriedBlock() == null) {
            crane.lowerHook();
            System.out.println("Крюк опускается…");

            for (Block b : warehouse.getBlocks()) {
                if (CollisionChecker.canGrab(crane, b)) {
                    crane.pickBlock(b);
                    warehouse.removeBlock(b);
                    System.out.println("БЛОК ПОДЦЕПЛЕН: " + b.getLetter());
                    break;
                }
            }
            return;
        }

        if (crane.isHookDown() && crane.getCarriedBlock() == null) {
            crane.raiseHook();
            System.out.println("Крюк поднят.");
            return;
        }

        if (crane.getCarriedBlock() != null) {

            crane.raiseHook();
            Block placed = crane.dropBlock();

            String collected = placedBlock.getLetter() + placed.getLetter();
            String correct = task.getFirstLetter() + task.getSecondLetter();

            if (collected.equals(correct)) {

                placed.setPosition(
                        placedBlock.getX() + 60,
                        placedBlock.getY()
                );

                secondPlacedBlock = placed;   // ✅ СОХРАНЯЕМ
                System.out.println("✔ СЛОГ СОБРАН ПРАВИЛЬНО: " + collected);
                scheduleNextTask();
            } else {
                System.out.println("✖ ОШИБКА: получено '" + collected +
                                   "', ожидалось '" + correct + "'");
                crane.setX(70);
                // ❌ НЕ сохраняем блок
            }

            return;
        }
        
    }
    
    private void scheduleNextTask() {
        Timer timer = new Timer(1000, e -> nextTask());
        timer.setRepeats(false);
        timer.start();
    }

    private void createWarehouseBlocks() {

        List<String> vowels = new ArrayList<>(
                List.of("А", "О", "У", "И", "Э", "Ы", "Е", "Ё", "Ю", "Я")
        );

        String correct = task.getSecondLetter();

        // убираем правильную, чтобы не было повторов
        vowels.remove(correct);

        Collections.shuffle(vowels);

        int blocksCount = 5; // всего блоков на складе
        int xStart = 120;
        int y = 100;
        int spacing = 80;

        // список букв для склада
        List<String> letters = new ArrayList<>();
        letters.add(correct);

        for (int i = 0; i < blocksCount - 1; i++) {
            letters.add(vowels.get(i));
            
        }

        // перемешиваем порядок
        Collections.shuffle(letters);

        for (int i = 0; i < letters.size(); i++) {
        	Color color = COLORS[(int)(Math.random() * COLORS.length)];
            Block b = new Block(
                    letters.get(i),
                    xStart + i * spacing,
                    y, 
                    color
            );
            warehouse.addBlock(b);
        }
    }
    
    private void initTask() {
        crane = new Crane(70);
        warehouse = new Warehouse();

        placedBlock = new Block(
                task.getFirstLetter(),
                250,
                300,
                COLORS[(int)(Math.random() * COLORS.length)]
        );

        secondPlacedBlock = null;

        createWarehouseBlocks();

        System.out.println("НОВОЕ ЗАДАНИЕ: " + task.getCommand());
    }

    private void nextTask() {
        taskIndex = (taskIndex + 1) % tasks.size();
        task = tasks.get(taskIndex);
        initTask();
    }

    
    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Block getSecondPlacedBlock() {
        return secondPlacedBlock;
    }
}
