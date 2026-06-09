package task2.app;

import task2.body.Cone;
import task2.body.Cube;
import task2.body.Cylinder;
import task2.figure.Square;
import task2.figure.Trapezoid;
import task2.figure.Triangle;
import task2.body.Body;
import task2.figure.Figure;

public class Main {
    public static void main(String[] args) {
        Figure square = new Square(5);
        square.print();

        Figure triangle = new Triangle(3, 4, 5);
        triangle.print();

        Figure trapezoid = new Trapezoid(10, 6, 5, 5);
        trapezoid.print();

        Body cube = new Cube(3);
        cube.print();

        Body cylinder = new Cylinder(2, 10);
        cylinder.print();

        Body cone = new Cone(3, 12);
        cone.print();
    }
}