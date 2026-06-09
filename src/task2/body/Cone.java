package task2.body;

public class Cone extends Body {

    private final double radius;
    private final double height;

    public Cone(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double getVolume() {
        return (1.0 / 3) * Math.PI * radius * radius * height;
    }
}