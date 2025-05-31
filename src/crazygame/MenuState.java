package crazygame;

import java.awt.*;
import java.awt.event.*;

public class MenuState implements GameState, KeyListener {

    private Window window;

    public MenuState(Window window) {
        this.window = window;
        window.addKeyListener(this);  // Add KeyListener to Window
    }

    @Override
    public void update() {
        // No updates needed for MenuState
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawString("Press Enter to Start", 300, 300);
    }

    // Handle key press in MenuState
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Switch to the PlayingState when Enter is pressed
            window.setCurrentState(new LobbyState(window));
        }
    }

    // Other KeyListener methods (not used)
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

	@Override
	public void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
