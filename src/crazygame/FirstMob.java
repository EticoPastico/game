package crazygame;

import java.awt.*;

public class FirstMob extends Mobs {
	
	private int spawningCount = 0;
	private int dyingCount = 0;
    
	public FirstMob(int x, int y) {
        super(x, y);
        hitpoints = 1;
    }

    @Override
    public void update() {
    	cameraY += 2;
    	
    	if (state == GameUtils.MobSpawningState) {
    		
    	}
    	
    	if (state == GameUtils.MobAliveState) {
    		
    	}
    	if (state == GameUtils.MobDyingState) {
    		
    	}
    }

    @Override
    public void render(Graphics2D g) {

    	if (state == GameUtils.MobSpawningState) {
    		spawningCount++;
    		if (1 <= spawningCount && spawningCount < 10) {
    			g.setColor(Color.GREEN);
                g.fillRect(x, y + cameraY, width, height);
    		}
    		if (10 <= spawningCount && spawningCount < 20) {
    			g.setColor(Color.BLUE);
                g.fillRect(x, y + cameraY, width, height);
    		}
    		if (20 <= spawningCount && spawningCount < 30) {
    			g.setColor(Color.YELLOW);
                g.fillRect(x, y + cameraY, width, height);
    		}
    		if (spawningCount >= 30) {
    			this.state = GameUtils.MobAliveState;
    		}
    	}
    	
    	if (state == GameUtils.MobAliveState) {
        	g.setColor(Color.RED);
            g.fillRect(x, y + cameraY, width, height);
    	}
    	if (state == GameUtils.MobDyingState) {
    		dyingCount++;
    		g.setColor(Color.WHITE);
            g.fillRect(x, y + cameraY, width, height);
    		if (dyingCount >= 15) {
    			this.state = GameUtils.MobGoneState;
    		}
    	}
    }
}