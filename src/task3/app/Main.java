package task3.app;

import task3.impl.DoubleCalculator;
import task3.impl.IntegerCalculator;
import task3.impl.StringCalculator;
import task3.impl.VectorCalculator;
import task3.model.Vector2D;
import task3.operation.Calculator;

public class Main {
    public static void main(String[] args) {
        Calculator<Integer> intCalc = new IntegerCalculator();
        System.out.println("5 + 3 = " + intCalc.add(5, 3));
        System.out.println("10 * 3 = " + intCalc.multiply(10, 3));
        try {
            System.out.println("50 / 20 = " + intCalc.divide(50, 20));
        } catch (ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        Calculator<Double> doubleCalc = new DoubleCalculator();
        System.out.println("2.0 + 2.5 = " + doubleCalc.add(2.0, 2.5));
        System.out.println("2.5 - 2.0 = " + doubleCalc.subtract(2.5, 2.0));
        System.out.println("5.0 / 2.5 = " + doubleCalc.divide(5.0, 2.5));
        try {
            System.out.println("5.0 / 0.0 = " + doubleCalc.divide(5.0, 0.0));
        } catch (ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        Calculator<String> strCalc = new StringCalculator();
        System.out.println("Сложение: " + strCalc.add("Компьютер ", "мышка"));
        System.out.println("Вычитание: " + strCalc.subtract("Компьютер и мышка", "и"));
        System.out.println("Умножение: " + strCalc.multiply("Кот", "ффф"));
        System.out.println("Деление: " + strCalc.divide("Компьютер и мышка", " и "));

        Calculator<Vector2D> vecCalc = new VectorCalculator();
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(2, -4);
        System.out.println("Сложение: " + vecCalc.add(v1, v2));
        System.out.println("Вычитание: " + vecCalc.subtract(v1, v2));
        System.out.println("Умножение: " + vecCalc.multiply(v1, v2));
        System.out.println("Деление: " + vecCalc.divide(v1, v2));

    }
}