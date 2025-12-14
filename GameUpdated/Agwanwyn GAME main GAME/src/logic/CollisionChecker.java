package logic;

import model.Block;
import model.Crane;

public class CollisionChecker {

    public static boolean canGrab(Crane crane, Block block) {
        boolean aligned = Math.abs(crane.getX() - block.getX()) < block.getSize();
        boolean hookReached = crane.getHookY() >= block.getY();
        return aligned && hookReached;
    }
}
