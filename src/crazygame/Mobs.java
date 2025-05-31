package crazygame;

import java.awt.*;

public abstract class Mobs {
    protected int x, y;
    protected int width = 40, height = 40;
    protected int speed = 2;
    protected int hitpoints;
    protected int cameraY = 0;
    protected int state;

    public Mobs(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = GameUtils.MobSpawningState;
    }

    public abstract void update();  // different behavior per mob
    public abstract void render(Graphics2D g);

	public int getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return this.x;
	}
}
