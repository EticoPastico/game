package crazygame;

import java.awt.Rectangle;

public final class GameUtils {

	public static final int LobbyState = 1;
	public static final int PlayingState = 2;
	
	public static final int MobSpawningState = 1;
	public static final int MobAliveState = 2;
	public static final int MobDyingState = 3;
	public static final int MobGoneState = 4;
	
    public static boolean collides(Player player, Mobs mob) {
        Rectangle playerRect = new Rectangle(player.x, player.y + player.cameraY, player.width, player.height);
        Rectangle mobRect = new Rectangle(mob.x, mob.y + mob.cameraY, mob.width, mob.height);
        return playerRect.intersects(mobRect);
    }
	
    public static boolean collides(Player player, Rectangle rect) {
        Rectangle playerRect = new Rectangle(player.x, player.y + player.cameraY, player.width, player.height);
        return playerRect.intersects(rect);
    }
    
    public static boolean collides(Mobs mob, Rectangle rect) {
        Rectangle playerRect = new Rectangle(mob.x, mob.y + mob.cameraY, mob.width, mob.height);
        return playerRect.intersects(rect);
    }
}
