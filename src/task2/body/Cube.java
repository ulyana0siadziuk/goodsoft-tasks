package task2.body;

public class Cube extends Body {

    private final double side;

    public Cube(double side) {
        this.side = side;
    }

    @Override
    public double getVolume() {
        return side * side * side;
    }
}