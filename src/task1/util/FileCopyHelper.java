package task1.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public final class FileCopyHelper {

    private FileCopyHelper() {
    }

    public static Path resolveTarget(Path source, Path target) {
        if (Files.exists(target) && Files.isDirectory(target)) {
            Path resolved = target.resolve(source.getFileName());
            System.out.println("Целевой путь — папка. Файл будет создан: " + resolved);
            return resolved;
        }
        return target;
    }

    public static boolean validateSource(Path source) {
        if (!Files.exists(source)) {
            System.out.println("Ошибка: исходный файл не существует");
            return false;
        }
        if (!Files.isRegularFile(source)) {
            System.out.println("Ошибка: исходный путь ведёт к папке, а не к файлу");
            return false;
        }
        return true;
    }

    public static boolean confirmOverwrite(Path target, Scanner scanner) {
        if (!Files.exists(target)) {
            return true;
        }
        System.out.print("Файл уже существует. Перезаписать? (д/н): ");
        String answer = scanner.nextLine();
        if (!answer.equalsIgnoreCase("д")) {
            System.out.println("Копирование отменено.");
            return false;
        }
        return true;
    }
}