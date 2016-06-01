package worldoffun_guajardo;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Guajardo_Dank_World_Lv1 extends BasicGameState {

    public static Player player;
    public static ItemWin antidote;

    public Item healthpotion, healthpotion1;
    public Item1 speedpotion, speedpotion1;
    public Orb magic8ball, orb1;
    public Enemy jackson;
    public Traps trap1;

    
    public ArrayList<Traps> traps = new ArrayList();
    public ArrayList<Item> stuff = new ArrayList();
    public ArrayList<Item1> stuff1 = new ArrayList();
    public ArrayList<ItemWin> stuffwin = new ArrayList();
    public ArrayList<Enemy> enemiez = new ArrayList();
    
    private boolean[][] hostiles;
    private static TiledMap grassMap;
    private static AppGameContainer app;
    private static Camera camera;
    public static int counter = 0;
    private static final int SIZE = 64;
    private static final int SCREEN_WIDTH = 1000;
    private static final int SCREEN_HEIGHT = 750;

    public Guajardo_Dank_World_Lv1(int xSize, int ySize) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {

        gc.setTargetFrameRate(60);
        gc.setShowFPS(false);
        grassMap = new TiledMap("res/Guajardo_Dank_WorldLv1.tmx");
        System.out.println("Tile map is this wide: " + grassMap.getWidth());
        camera = new Camera(gc, grassMap);

        player = new Player();
        Blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];

        System.out.println("The grassmap is " + grassMap.getWidth() + "by "
                + grassMap.getHeight());

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
               
                int tileID = grassMap.getTileId(xAxis, yAxis, 1);
                String value = grassMap.getTileProperty(tileID,
                        "blocked", "false");
                if ("true".equals(value)) {
                  
                    Blocked.blocked[xAxis][yAxis] = true;
                }
            }
        }
        
        hostiles = new boolean[grassMap.getWidth()][grassMap.getHeight()];

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 7 == 0 && xBlock % 15 == 0) {
                        Item i = new Item(xAxis * SIZE, yAxis * SIZE);
                        stuff.add(i);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (xBlock % 9 == 0 && yBlock % 25 == 0) {
                        Item1 h = new Item1(xAxis * SIZE, yAxis * SIZE);
                        stuff1.add(h);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 7 == 0 && xBlock % 15 == 0) {
                        Enemy e = new Enemy(xAxis * SIZE, yAxis * SIZE);
                        enemiez.add(e);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }
        
               for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!Blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 5 == 0 && xBlock % 11 == 0) {
                        
                        Traps i = new Traps(xAxis * SIZE, yAxis * SIZE);
                        traps.add(i);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }
               
        
        trap1 = new Traps(160, 150);
        traps.add(trap1);
        
        healthpotion = new Item(160, 134);
        healthpotion1 = new Item(450, 400);
        stuff.add(healthpotion);
        //stuff.add(healthpotion1);
        jackson = new Enemy((int) player.x + 142, (int) player.y + 142);
        orb1 = new Orb((int) player.x, (int) player.y);
      
        speedpotion = new Item1(160, 254);
        speedpotion1 = new Item1(450, 100);
        stuff1.add(speedpotion);
        stuff1.add(speedpotion1);
        antidote = new ItemWin(4453, 4506);
        stuffwin.add(antidote);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {
        camera.centerOn((int) player.x, (int) player.y);
        camera.drawMap();
        camera.translateGraphics();
        
        player.sprite.draw((int) player.x, (int) player.y);
        
        //g.drawString("x: " + (int)player.x + "y: " +(int)player.y , player.x, player.y - 10);
        
        g.drawString("Health: " + player.health / 1000, camera.cameraX + 10,
                camera.cameraY + 10);
        g.drawString("speed: " + (int) (player.speed * 10), camera.cameraX + 10,
                camera.cameraY + 25);
        g.drawString("time passed: " + counter / 1000, camera.cameraX + 600, camera.cameraY);

        for (Item i : stuff) {
            if (i.isvisible) {
                i.currentImage.draw(i.x, i.y);
                
            }
        }

        for (Item1 h : stuff1) {
            if (h.isvisible) {
                h.currentImage.draw(h.x, h.y);
                
            }
        }
        
            for (Traps h : traps) {
            if (h.isvisible) {
                h.currentImage.draw(h.x, h.y);
                
            }
        }

        for (ItemWin w : stuffwin) {
            if (w.isvisible) {
                w.currentImage.draw(w.x, w.y);
                
            }
        }

        if (orb1.isIsVisible()) {
            orb1.orbpic.draw(orb1.getX(), orb1.getY());
        }

        for (Enemy e : enemiez) {
            if (e.isVisible) {
                e.currentanime.draw(e.Bx, e.By);
                
            }
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {

        counter += delta;
        Input input = gc.getInput();
        float fdelta = delta * player.speed;
        player.setpdelta(fdelta);
        double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);
        float projectedright = player.x + fdelta + SIZE;
        boolean cangoright = projectedright < rightlimit;
        if (input.isKeyDown(Input.KEY_UP)) {
            player.setDirection(0);
            player.sprite = player.up;
            float fdsc = (float) (fdelta - (SIZE * .15));
            if (!(isBlocked(player.x, player.y - fdelta) || isBlocked(
                    (float) (player.x + SIZE + 1.5), player.y - fdelta))) {
                player.sprite.update(delta);
                player.y -= fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_DOWN)) {
            player.setDirection(2);
            player.sprite = player.down;
            if (!isBlocked(player.x, player.y + SIZE + fdelta)
                    || !isBlocked(player.x + SIZE - 1, player.y + SIZE + fdelta)) {
                player.sprite.update(delta);
                player.y += fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_LEFT)) {
            player.setDirection(3);
            player.sprite = player.left;
            if (!(isBlocked(player.x - fdelta, player.y) || isBlocked(player.x
                    - fdelta, player.y + SIZE - 1))) {
                player.sprite.update(delta);
                player.x -= fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            player.setDirection(1);
            player.sprite = player.right;
            if (cangoright
                    && (!(isBlocked(player.x + SIZE + fdelta,
                            player.y) || isBlocked(player.x + SIZE + fdelta, player.y
                            + SIZE - 1)))) {
                player.sprite.update(delta);
                player.x += fdelta;
            } 
        } else if (input.isKeyPressed(Input.KEY_SPACE)) {
            orb1.setDirection(player.getDirection());
            orb1.settimeExists(20);
            orb1.setX((int) player.x);
            orb1.setY((int) player.y);
            orb1.hitbox.setX(orb1.getX());
            orb1.hitbox.setY(orb1.getY());
            orb1.setIsVisible(!orb1.isIsVisible());
        }

        player.rect.setLocation(player.getPlayershitboxX(),
                player.getPlayershitboxY());
         
        for (Traps i : traps) {

            if (player.rect.intersects(i.hitbox)) {
                if (i.isvisible) {

                    player.health -= 1000;
                }
            }
        }

        for (Item i : stuff) {

            if (player.rect.intersects(i.hitbox)) {
                if (i.isvisible) {

                    player.health += 10000;
                    i.isvisible = false;
                }
            }
        }

        for (Item1 h : stuff1) {
            if (player.rect.intersects(h.hitbox)) {
                if (h.isvisible) {
                    player.speed += .1f;
                    h.isvisible = false;
                }
            }
        }

        for (ItemWin w : stuffwin) {

            if (player.rect.intersects(w.hitbox)) {
                if (w.isvisible) {
                    w.isvisible = false;
                    makevisible();
                    player.x = 145;
                    player.y = 377;
                    sbg.enterState(4, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                }

            }
        }

        for (Enemy e : enemiez) {

            if (e.isVisible) {

                if (e.rect.intersects(player.rect)) {
                    System.out.println("ouch");
                    player.health -= 250;
                }

                if (orb1.hitbox.intersects(e.rect)) {

                    e.isVisible = false;
                }
            }
        }

        if (orb1.isIsVisible()) {
            if (orb1.gettimeExists() > 0) {
                if (orb1.getDirection() == 0) {
                    orb1.setX((int) player.x);
                    orb1.setY(orb1.getY() - 5);
                } else if (orb1.getDirection() == 2) {
                    orb1.setX((int) player.x);
                    orb1.setY(orb1.getY() + 5);
                } else if (orb1.getDirection() == 3) {
                    orb1.setX(orb1.getX() - 5);
                    orb1.setY(orb1.getY());
                } else if (orb1.getDirection() == 1) {
                    orb1.setX(orb1.getX() + 5);
                    orb1.setY(orb1.getY());
                }
                orb1.hitbox.setX(orb1.getX());
                orb1.hitbox.setY(orb1.getY());
                orb1.countdown();
            } else {
                orb1.setIsVisible(false);
            }
        }

        player.health -= counter / 1000;
        if (player.health <= 0) {
            makevisible();
            sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        for (Enemy e : enemiez) {
            if (e.isVisible) {
                e.move();
            }
        }

    }

    public int getID() {
        return 1;
    }

    public void makevisible() {
        for (Item1 h : stuff1) {
            h.isvisible = true;
        }

        for (Item i : stuff) {
            i.isvisible = true;
        }
    }

    private boolean isBlocked(float tx, float ty) {
        int xBlock = (int) tx / SIZE;
        int yBlock = (int) ty / SIZE;
        return Blocked.blocked[xBlock][yBlock];
    }
}
