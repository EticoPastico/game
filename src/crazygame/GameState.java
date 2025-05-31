package crazygame;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public interface GameState {
	
	public void onMousePressed(MouseEvent e); 
    void update();
    void render(Graphics2D g);
}


