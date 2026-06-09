package task3.impl;

import task3.model.Vector2D;
import task3.operation.Calculator;

public class VectorCalculator implements Calculator<Vector2D> {

    @Override
    public Vector2D add(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() + b.getX(), a.getY() + b.getY());
    }

    @Override
    public Vector2D subtract(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() - b.getX(), a.getY() - b.getY());
    }

    @Override
    public Vector2D multiply(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() * b.getX(), a.getY() * b.getY());
    }

    @Override
    public Vector2D divide(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() / b.getX(), a.getY() / b.getY());
    }
}