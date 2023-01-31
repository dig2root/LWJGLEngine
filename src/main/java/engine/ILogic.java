package engine;

public interface ILogic {

    void init() throws Exception;

    void input();

    void update(MouseManager mouseManager);

    void render();

    void cleanUp();
}
