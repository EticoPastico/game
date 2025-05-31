package crazygame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class Window extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
	private Frame frame;
    private BufferStrategy bufferStrategy;
    private boolean running = true;

    private GameState currentState;
    
    

    public Window() {
        frame = new Frame("Smooth AWT Window");
        frame.setSize(800, 1400);
        frame.setLayout(null);
        this.setBounds(0, 0, 800, 1400);
        frame.add(this);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                running = false;
                System.exit(0);
            }
        });


        this.setFocusable(true);

        this.createBufferStrategy(2);
        bufferStrategy = this.getBufferStrategy();

        currentState = new MenuState(this);

        new Thread(this).start();
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentState != null) {
                    currentState.onMousePressed(e); // Pass the event to the state
                }
            }
        });
    }

    @Override
    public void run() {
        final int targetFPS = 60;
        final long optimalTime = 1_000_000_000 / targetFPS;

        while (running) {
            long startTime = System.nanoTime();

            // Update current state
            if (currentState != null)
                currentState.update();

            // Render current state
            render();

            long elapsed = System.nanoTime() - startTime;
            long sleepTime = (optimalTime - elapsed) / 1_000_000;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (currentState != null)
            currentState.render(g);

        g.dispose();
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
    }
    
    public void setCurrentState(GameState state) {
        this.currentState = state;  // Change the current state
    }

    public static void main(String[] args) {
        new Window();
    }
}
