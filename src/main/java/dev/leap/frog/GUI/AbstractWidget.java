package dev.leap.frog.GUI;

public abstract class AbstractWidget {

    // Setters.
    public void setX(int x) {}
    public void setY(int y) {}

    public void setWidth(int width) {}
    public void setHeight(int height) {}

    // Getters.
    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    // Binding.
    public boolean isBinding() {
        return false;
    }

    // Motion.
    public boolean motionPass(int mx, int my) {
        return false;
    }

    // Keyboard.
    public void bind(char char_, int key) {}

    // Can.
    public void doesCan(boolean value) {}

    // Mouse click.
    public void mouse(int mx, int my, int mouse) {}

    // Release.
    public void release(int mx, int my, int mouse) {}

    // Render abstract.
    public void render(int master_y, int separe, int x, int y) {}

}
