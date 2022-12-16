package model;

public abstract class Entity {
    protected int x, y, WIDTH, HEIGHT;

    public abstract int getX();
    public abstract void setX(int x);
    public abstract int getY();
    public abstract void setY(int y);

    public abstract void setWIDTH(int WIDTH);

    public abstract int getWIDTH();

    public abstract void setHEIGHT(int HEIGHT);
    public abstract int getHEIGHT();
}
