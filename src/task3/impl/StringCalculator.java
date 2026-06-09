package task3.impl;

import task3.operation.Calculator;

/**
 * Реализация {@link Calculator} для строк.
 * Операции интерпретируются как строковые, а не математические.
 */
public class StringCalculator implements Calculator<String> {

    /**
     * Складывает две строки (конкатенация).
     *
     * @param a первая строка
     * @param b вторая строка
     * @return строка {@code a}, за которой следует строка {@code b}
     */
    @Override
    public String add(String a, String b) {
        return a + b;
    }

    /**
     * Вычитание строк: удаляет все вхождения подстроки {@code b} из строки {@code a}.
     *
     * @param a исходная строка
     * @param b подстрока для удаления
     * @return строка {@code a} без вхождений {@code b}
     */
    @Override
    public String subtract(String a, String b) {
        return a.replace(b, "");
    }

    /**
     * Умножение строк: повторяет строку {@code a} столько раз,
     * сколько символов содержит строка {@code b}.
     *
     * @param a строка для повторения
     * @param b строка, длина которой задаёт число повторений
     * @return результат повторения строки {@code a}
     */
    @Override
    public String multiply(String a, String b) {
        StringBuilder result = new StringBuilder();
        int times = b.length();
        for (int i = 0; i < times; i++) {
            result.append(a);
        }
        return result.toString();
    }

    /**
     * Деление строк: возвращает часть строки {@code a} до первого вхождения {@code b}.
     * Если {@code b} не найдена в {@code a}, возвращается исходная строка {@code a}.
     *
     * @param a исходная строка
     * @param b разделитель (ищется в {@code a})
     * @return подстрока {@code a} от начала до первого вхождения {@code b}
     */
    @Override
    public String divide(String a, String b) {
        int index = a.indexOf(b);
        if (index == -1) {
            return a;
        }
        return a.substring(0, index);
    }
}
