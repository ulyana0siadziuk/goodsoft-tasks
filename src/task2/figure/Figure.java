package task2.figure;

public abstract class Figure {

    public abstract double getPerimeter();

    public abstract String getName();

    public void print () {
        System.out.println("это фигура - " + getName() + " с периметром " + getPerimeter());
    }
}
