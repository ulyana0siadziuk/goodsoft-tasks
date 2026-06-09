package task3.impl;

import task3.operation.Calculator;

/**
 * Реализация {@link Calculator} для дробных чисел ({@link Double}).
 */
public class DoubleCalculator implements Calculator<Double> {

    /**
     * Складывает два дробных числа.
     *
     * @param a первое слагаемое
     * @param b второе слагаемое
     * @return сумма {@code a} и {@code b}
     */
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    /**
     * Вычитает второе дробное число из первого.
     *
     * @param a уменьшаемое
     * @param b вычитаемое
     * @return разность {@code a} и {@code b}
     */
    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    /**
     * Умножает два дробных числа.
     *
     * @param a первый множитель
     * @param b второй множитель
     * @return произведение {@code a} и {@code b}
     */
    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    /**
     * Делит первое дробное число на второе.
     * При делении на {@code 0.0} результатом будет {@code Infinity} или {@code NaN}.
     *
     * @param a делимое
     * @param b делитель
     * @return частное {@code a} и {@code b}
     */
    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }
}
