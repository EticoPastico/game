package crazygame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayingState implements GameState, KeyListener {

	private Window window;
	private final Player player;

	private boolean leftPressed = false;
	private boolean rightPressed = false;

	private boolean jumping = false;
	private boolean attacking = false;
	private int spawnTimer = 0;
	private Point mouseClickPosition;





	private final List<Mobs> mobs = new ArrayList<>();



	public PlayingState(Window window, Player player) {
		this.window = window;
		this.player = player;
		window.addKeyListener(this);  // Add KeyListener to Window
	}


	@Override
	public void update() {

		spawnTimer++;
		if (spawnTimer >= 60) {
			spawnTimer = 0;
			mobs.add(new FirstMob((int) (Math.random() * 700), 0));  // Random X
		}

		if (attacking && mouseClickPosition != null) {
			int centerX = player.x + player.width / 2;
			int centerY = player.y + player.cameraY + player.height / 2;

			double dx = mouseClickPosition.x - centerX;
			double dy = mouseClickPosition.y - centerY;
			double angle = Math.atan2(dy, dx);

			int attackLength = 120;
			int attackWidth = 20;

			// Define 3 triangle points
			double x1 = centerX;
			double y1 = centerY;

			double x2 = centerX + Math.cos(angle + Math.toRadians(5)) * attackLength;
			double y2 = centerY + Math.sin(angle + Math.toRadians(5)) * attackLength;

			double x3 = centerX + Math.cos(angle - Math.toRadians(5)) * attackLength;
			double y3 = centerY + Math.sin(angle - Math.toRadians(5)) * attackLength;

			// Create triangle as a Path2D
			Path2D triangle = new Path2D.Double();
			triangle.moveTo(x1, y1);
			triangle.lineTo(x2, y2);
			triangle.lineTo(x3, y3);
			triangle.closePath();

			for (Mobs mob : mobs) {
				Rectangle mobBounds = new Rectangle(mob.getX(), mob.getY() + mob.cameraY, mob.width, mob.height);
				if (triangle.intersects(mobBounds)) {
					player.jumped = false; // hit confirmed
					mob.hitpoints--;
				}
			}
		}

		Iterator<Mobs> mobIterator = mobs.iterator();
		while (mobIterator.hasNext()) {
			Mobs mob = mobIterator.next();

			if (mob.getY() + mob.cameraY > 1200) {
				mob.state = GameUtils.MobGoneState;
			}

			if (mob.hitpoints <= 0 && !(mob.state == GameUtils.MobGoneState)) {
				mob.state = GameUtils.MobDyingState;
			}
			
			if (mob.state == GameUtils.MobGoneState) {
				mobIterator.remove();
			}
			
			if (GameUtils.collides(player, mob)) {
				player.hitpoints -= 1;
			}
			mob.update();
		}
		player.update(leftPressed, rightPressed, jumping);
		jumping = false;
	}

	@Override
	public void render(Graphics2D g) {
		player.renderplayer(g, mouseClickPosition, attacking);
		for (Mobs mob : mobs) {
			mob.render(g);
		}

		attacking = false;
	}

	// Handle key press in PlayingState
	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			attacking = true;
		}

		// Add more controls for player movement or actions here
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = true;
			player.facing_left = true;
			player.facing_right = false;
			// Logic for moving player left (you could update player position)
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = true;
			player.facing_left = false;
			player.facing_right = true;
			// Logic for moving player right
		}
		if (player.jumped ==false && e.getKeyCode() == KeyEvent.VK_UP) {
			player.jumped = true;
			jumping = true;
			// Logic for moving player left (you could update player position)
		}
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		attacking = true;
		mouseClickPosition = e.getPoint();

	}

	// Other KeyListener methods (not used)
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
		if (e.getKeyCode() == KeyEvent.VK_SPACE) attacking = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}



}