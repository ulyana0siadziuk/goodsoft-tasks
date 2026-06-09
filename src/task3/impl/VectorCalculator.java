package task3.impl;

import task3.model.Vector2D;
import task3.operation.Calculator;

/**
 * Реализация {@link Calculator} для двумерных векторов {@link Vector2D}.
 * Все операции выполняются поэлементно над координатами x и y.
 */
public class VectorCalculator implements Calculator<Vector2D> {

    /**
     * Складывает два вектора поэлементно.
     *
     * @param a первый вектор
     * @param b второй вектор
     * @return новый вектор {@code {a.x + b.x, a.y + b.y}}
     */
    @Override
    public Vector2D add(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() + b.getX(), a.getY() + b.getY());
    }

    /**
     * Вычитает второй вектор из первого поэлементно.
     *
     * @param a уменьшаемый вектор
     * @param b вычитаемый вектор
     * @return новый вектор {@code {a.x - b.x, a.y - b.y}}
     */
    @Override
    public Vector2D subtract(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() - b.getX(), a.getY() - b.getY());
    }

    /**
     * Умножает два вектора поэлементно.
     *
     * @param a первый вектор
     * @param b второй вектор
     * @return новый вектор {@code {a.x * b.x, a.y * b.y}}
     */
    @Override
    public Vector2D multiply(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() * b.getX(), a.getY() * b.getY());
    }

    /**
     * Делит первый вектор на второй поэлементно.
     *
     * @param a делимый вектор
     * @param b делитель
     * @return новый вектор {@code {a.x / b.x, a.y / b.y}}
     */
    @Override
    public Vector2D divide(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX() / b.getX(), a.getY() / b.getY());
    }
}
