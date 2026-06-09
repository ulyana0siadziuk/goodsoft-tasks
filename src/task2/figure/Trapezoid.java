package task2.figure;

public class Trapezoid extends Figure {

    private final double base1;
    private final double base2;
    private final double side1;
    private final double side2;

    public Trapezoid(double base1, double base2, double side1, double side2) {
        this.base1 = base1;
        this.base2 = base2;
        this.side1 = side1;
        this.side2 = side2;
    }

    @Override
    public double getPerimeter() {
        return base1 + base2 + side1 + side2;
    }

    @Override
    public String getName() {
        return "трапеция";
    }
}