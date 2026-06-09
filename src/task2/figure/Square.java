package task2.figure;

public class Square extends Figure {

    private final double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double getPerimeter() {
        return 4 * side;
    }

    @Override
    public String getName() {
        return "квадрат";
    }
}