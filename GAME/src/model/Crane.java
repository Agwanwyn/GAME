package model;

public class Crane {
    private int x;               // положение по горизонтали
    private int hookY;           // высота крюка
    private boolean hookDown;    // опускается ли крюк
    private Block carriedBlock;  // подцепленный блок

    public Crane(int startX) {
        this.x = startX;
        this.hookY = 0;
        this.hookDown = false;
    }

    public int getX() { return x; }
    public int getHookY() { return hookY; }
    public boolean isHookDown() { return hookDown; }
    public Block getCarriedBlock() { return carriedBlock; }

    public void moveLeft() { x -= 10; }
    public void moveRight() { x += 10; }

    public void lowerHook() { hookDown = true; hookY += 100; }
    public void raiseHook() { hookDown = false; hookY = 0; }

    public void pickBlock(Block b) { carriedBlock = b; }

    public Block dropBlock() {
        Block temp = carriedBlock;
        carriedBlock = null;
        return temp;
    }
    
    public void setX(int x) {
        this.x = x;
    }
}
