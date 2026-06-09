package task2.app;

import task2.body.Cone;
import task2.body.Cube;
import task2.body.Cylinder;
import task2.figure.Square;
import task2.figure.Trapezoid;
import task2.figure.Triangle;

public class Main {
    public static void main(String[] args) {
        Square square = new Square(5);
        square.print();

        Triangle triangle = new Triangle(3, 4, 5);
        triangle.print();

        Trapezoid trapezoid = new Trapezoid(10, 6, 5, 5, 4);
        trapezoid.print();

        Cube cube = new Cube(3);
        cube.print();

        Cylinder cylinder = new Cylinder(2, 10);
        cylinder.print();

        Cone cone = new Cone(3, 12);
        cone.print();
    }
}