package model;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private final List<Block> blocks = new ArrayList<>();

    public void addBlock(Block b) {
        blocks.add(b);
    }

    public void removeBlock(Block b) {
        blocks.remove(b);
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
