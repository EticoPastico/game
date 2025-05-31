package crazygame;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LobbyState implements GameState, KeyListener {
	
	private Window window;
    private final Player player = new Player(375, 1100, 5, 0, GameUtils.LobbyState);
    private final Npc npc = new Npc(700, 1100);
    
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean jumping = false;
    private boolean canTalk = false;
	
    public LobbyState(Window window) {
        this.window = window;
        window.addKeyListener(this);  // Add KeyListener to Window
    }
    
	@Override
    public void keyPressed(KeyEvent e) {


        // Add more controls for player movement or actions here
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        	leftPressed = true;
            // Logic for moving player left (you could update player position)
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	rightPressed = true;
            // Logic for moving player right
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
        	if (player.jumped ==false) { 
        	player.jumped = true;
        	jumping = true;
        	}
            // Logic for moving player left (you could update player position)
        }
        
        if (e.getKeyCode() == KeyEvent.VK_E && canTalk) {
        	canTalk = false;
        	System.out.println("you talked!");
        	Player newplayer = new Player(player.x, player.y, player.hitpoints, -100, GameUtils.PlayingState);
        	window.setCurrentState(new PlayingState(window, newplayer));
        }
    }

	@Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
	
    
    @Override
	public void update() {
    	player.update(leftPressed, rightPressed, jumping);
    	npc.update();
        jumping = false;
		
        if (GameUtils.collides(player, npc.npc_hitbox)) {
        	canTalk = true;
        } else canTalk = false;
	}

    
    
	@Override
	public void render(Graphics2D g) {
		player.renderplayer_in_lobby(g);
		npc.rendernpc(g);
        if (GameUtils.collides(player, npc.npc_hitbox)) {
        	g.drawString("Press e to talk", npc.x, npc.y -20);
        } 
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
