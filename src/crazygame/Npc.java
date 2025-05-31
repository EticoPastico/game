package crazygame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Npc {
    protected int x, y;
    protected int width = 40, height = 40;
    protected Rectangle npc_hitbox = new Rectangle(x, y, width, height);

    public Npc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
    	npc_hitbox = new Rectangle(x, y, width, height);
    }
    public void rendernpc(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

}
