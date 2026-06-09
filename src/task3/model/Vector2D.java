package task3.model;

/**
 * Двумерный вектор с координатами {@code x} и {@code y}.
 */
public class Vector2D {

    private final double x;
    private final double y;

    /**
     * Создаёт вектор с заданными координатами.
     *
     * @param x координата по оси X
     * @param y координата по оси Y
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает координату по оси X.
     *
     * @return значение {@code x}
     */
    public double getX() {
        return x;
    }

    /**
     * Возвращает координату по оси Y.
     *
     * @return значение {@code y}
     */
    public double getY() {
        return y;
    }

    /**
     * Возвращает строковое представление вектора в формате {@code {x, y}}.
     *
     * @return строка вида {@code {1.0, 2.0}}
     */
    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
