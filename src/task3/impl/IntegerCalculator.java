package task3.impl;

import task3.operation.Calculator;

/**
 * Реализация {@link Calculator} для целых чисел ({@link Integer}).
 * Деление выполняется как целочисленное.
 */
public class IntegerCalculator implements Calculator<Integer> {

    /**
     * Складывает два целых числа.
     *
     * @param a первое слагаемое
     * @param b второе слагаемое
     * @return сумма {@code a} и {@code b}
     */
    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    /**
     * Вычитает второе целое число из первого.
     *
     * @param a уменьшаемое
     * @param b вычитаемое
     * @return разность {@code a} и {@code b}
     */
    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    /**
     * Умножает два целых числа.
     *
     * @param a первый множитель
     * @param b второй множитель
     * @return произведение {@code a} и {@code b}
     */
    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    /**
     * Делит первое целое число на второе (целочисленное деление).
     * Дробная часть отбрасывается.
     *
     * @param a делимое
     * @param b делитель
     * @return частное {@code a} и {@code b}
     * @throws ArithmeticException если {@code b} равно 0
     */
    @Override
    public Integer divide(Integer a, Integer b) {
        return a / b;
    }
}
