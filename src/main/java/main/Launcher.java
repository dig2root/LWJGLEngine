package main;

import engine.EngineManager;
import engine.WindowManager;

public class Launcher {

    private static WindowManager window;
    private static TestGameDAE game;

    public static void main(String args[]) throws Exception {
        window = new WindowManager("Loading...", 1080, 720, false);
        game = new TestGameDAE();

        EngineManager engine = new EngineManager();
        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }

    public static TestGameDAE getGame() {
        return game;
    }
}
