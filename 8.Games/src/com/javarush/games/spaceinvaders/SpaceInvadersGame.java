package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.EnemyFleet;
import com.javarush.games.spaceinvaders.gameobjects.PlayerShip;
import com.javarush.games.spaceinvaders.gameobjects.Star;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private List<Star> stars;
    private EnemyFleet enemyFleet;
    public static final int COMPLEXITY = 5;
    private List<Bullet> enemyBullets;
    private PlayerShip playerShip;
    private boolean isGameStopped;
    private int animationsCount;
    private List<Bullet> playerBullets;
    private static final int PLAYER_BULLETS_MAX = 1;
    private int score;

    private void drawField() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellValueEx(x, y, Color.BLACK, "");
            }
        }
        for (Star star : stars) {
            star.draw(this);
        }
    }


    private void drawScene() {
        drawField();
        playerShip.draw(this);
        enemyFleet.draw(this);
        for (Bullet bullet : enemyBullets) {
            bullet.draw(this);
        }
        for (Bullet bullet : playerBullets) {
            bullet.draw(this);
        }
    }

    private void createGame() {
        score = 0;
        createStars();
        enemyFleet = new EnemyFleet();
        enemyBullets = new ArrayList<>();
        playerShip = new PlayerShip();
        isGameStopped = false;
        animationsCount = 0;
        playerBullets = new ArrayList<>();
        drawScene();
        setTurnTimer(40);
    }


    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createStars() {
        stars = new ArrayList<Star>();
        for (int i = 0; i < 8; i++) {
            stars.add(new Star(i * Math.random() * 5, i * Math.random() * 4));
        }

    }

    @Override
    public void onTurn(int step) {
        moveSpaceObjects();
        check();
        Bullet fireShot = enemyFleet.fire(this);
        if (fireShot != null) enemyBullets.add(fireShot);
        setScore(score);
        drawScene();
    }

    private void moveSpaceObjects() {
        enemyFleet.move();
        playerShip.move();
        for (Bullet bullet : enemyBullets) {
            bullet.move();
        }
        for (Bullet bullet : playerBullets) {
            bullet.move();
        }
    }


    private void removeDeadBullets() {
        enemyBullets.removeIf(bullet -> !bullet.isAlive || bullet.y >= HEIGHT - 1);
        playerBullets.removeIf(bullet -> !bullet.isAlive || bullet.y + bullet.height < 0);
    }

/*
11. В методе check() значение поля score должно быть увеличено на результат,
 который вернул вызов метода verifyHit(List<Bullet>) у объекта enemyFleet.
 */
    private void check() {
        playerShip.verifyHit(enemyBullets);
        score += enemyFleet.verifyHit(playerBullets);
        enemyFleet.deleteHiddenShips();
        removeDeadBullets();
        if (!playerShip.isAlive) {
            stopGameWithDelay();
        }

        if (enemyFleet.getBottomBorder() >= playerShip.y) {
            playerShip.kill();
        }

        if (enemyFleet.getShipsCount() == 0) {
            playerShip.win();
            stopGameWithDelay();
        }

    }

    private void stopGame(boolean isWin) {
        isGameStopped = true;
        stopTurnTimer();
        if (isWin) {
            showMessageDialog(Color.WHITE, "WIN!", Color.GREEN, 50);
        } else {
            showMessageDialog(Color.WHITE, "LOOSE!", Color.RED, 50);

        }
    }

    private void stopGameWithDelay() {
        animationsCount++;
        if (animationsCount >= 10) {
            stopGame(playerShip.isAlive);

        }
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped) createGame();
        if (key == Key.LEFT) playerShip.setDirection(Direction.LEFT);
        if (key == Key.RIGHT) playerShip.setDirection(Direction.RIGHT);
        if (key == Key.SPACE) {
            Bullet bullet = playerShip.fire();
            if (bullet != null && playerBullets.size() < PLAYER_BULLETS_MAX) {
                playerBullets.add(bullet);
            }
        }

    }


    @Override
    public void onKeyReleased(Key key) {
        if (key == Key.LEFT && playerShip.getDirection() == Direction.LEFT) {
            playerShip.setDirection(Direction.UP);
        }
        if (key == Key.RIGHT && playerShip.getDirection() == Direction.RIGHT) {
            playerShip.setDirection(Direction.UP);
        }
    }

    @Override
    public void setCellValueEx(int x, int y, Color cellColor, String value) {
        if (x > WIDTH - 1 || x < 0 || y < 0 || y > HEIGHT - 1) {
            return;
        }
        super.setCellValueEx(x, y, cellColor, value);


    }
}
