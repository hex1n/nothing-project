package com.hexin.demo;

import junit.framework.TestCase;

/**
 * @Author hex1n
 * @Date 2022/1/6 20:49
 * @Description
 */
public class TestFrame extends TestCase {
    public TestFrame(String name) {
        super(name);
    }

    public void testScoreNoThrows() {
        Frame f = new Frame();
        assertEquals(0, f.getScore());
    }

    public void testAddOneThrow() {
        Frame f = new Frame();
        f.add(5);
        assertEquals(5, f.getScore());
    }

    public void testoneThrows() {
        Game g = new Game();
        g.add(5);
        assertEquals(5,g.score());
    }

}

class Frame {

    private int itsScore = 0;

    public int getScore() {
        return itsScore;
    }

    public void add(int pins) {
        itsScore += pins;
    }
}

class Game {
    private int itsScore = 0;

    public int score() {
        return itsScore;
    }

    public void add(int pins) {
        itsScore += pins;
    }
}
