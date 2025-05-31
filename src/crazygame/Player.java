
package crazygame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Path2D;

public class Player {
	private int gamestate;

	protected int x;
	protected int y;
	protected final int width = 50, height = 50;
	protected final int speed = 5;
	protected final int max_fall_speed = 10;
	private int fall_velocity;
	boolean jumped = false;
	protected int cameraY = 0;
	protected int hitpoints;
	
	private static final Image playerSprite = Toolkit.getDefaultToolkit().getImage("assets/player.png");

	protected boolean facing_left = false;
	protected boolean facing_right = true;

	public Player(int startX, int startY, int hitpoints, int fall_velocity, int gamestate) {
		this.x = startX;
		this.y = startY;
		this.hitpoints= hitpoints;
		this.fall_velocity = fall_velocity;
		this.gamestate = gamestate;
	}

	public boolean on_ground_check() {
		if (gamestate == GameUtils.LobbyState && y >= 1100) {
			return true;
		}
		return false;
	}



	public void update(boolean left, boolean right, boolean jumping) {
		if (this.on_ground_check() == true) {
			fall_velocity=0;
			y = 1100;

			if (!jumping)
				jumped = false;

		} else if (fall_velocity < max_fall_speed) fall_velocity += 1;


		if (jumping) {
			fall_velocity = -40;
		}

		if (left) x -= speed;
		if (right) x += speed;
		y += fall_velocity/3; 

		if (gamestate == GameUtils.PlayingState)
			cameraY += 2;

	}


	public void renderplayer(Graphics2D g, Point mouseClickPosition, Boolean attacking) {

		g.drawImage(playerSprite, x, y + cameraY, width, height, null);
		if (attacking && mouseClickPosition != null) {
		    int centerX = x + width / 2;
		    int centerY = y + cameraY + height / 2;

		    double dx = mouseClickPosition.x - centerX;
		    double dy = mouseClickPosition.y - centerY;
		    double angle = Math.atan2(dy, dx);

		    int attackLength = 120;

		    double x1 = centerX;
		    double y1 = centerY;

		    double x2 = centerX + Math.cos(angle + Math.toRadians(5)) * attackLength;
		    double y2 = centerY + Math.sin(angle + Math.toRadians(5)) * attackLength;

		    double x3 = centerX + Math.cos(angle - Math.toRadians(5)) * attackLength;
		    double y3 = centerY + Math.sin(angle - Math.toRadians(5)) * attackLength;

		    Path2D triangle = new Path2D.Double();
		    triangle.moveTo(x1, y1);
		    triangle.lineTo(x2, y2);
		    triangle.lineTo(x3, y3);
		    triangle.closePath();

		    g.setColor(Color.RED);
		    g.fill(triangle);
		}
	}


	public void renderplayer_in_lobby(Graphics2D g) {
		g.drawImage(playerSprite, x, y + cameraY, width, height, null);
	}
}
