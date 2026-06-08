package task1;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Nio2FilesCopy implements FileCopy {

    @Override
    public void copyFile(String sourcePath, String targetPath, Scanner scanner) {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);

        if (Files.exists(target) && Files.isDirectory(target)) {
            target = target.resolve(source.getFileName());
            System.out.println("Целевой путь — папка. Файл будет создан: " + target);
        }

        try {
            if (!Files.exists(source)) {
                System.out.println("Ошибка: исходный файл не существует");
                return;
            }
            if (!Files.isRegularFile(source)) {
                System.out.println("Ошибка: исходный путь ведёт к папке, а не к файлу");
                return;
            }

            if (Files.exists(target)) {
                System.out.print("Файл уже существует. Перезаписать? (д/н): ");
                String answer = scanner.nextLine();
                if (!answer.equalsIgnoreCase("д")) {
                    System.out.println("Копирование отменено.");
                    return;
                }
            }

            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Копирование завершено успешно (NIO2 Files)!");

        } catch (NoSuchFileException e) {
            System.out.println("Ошибка: файл не найден - " + e.getMessage());
        } catch (FileAlreadyExistsException e) {
            System.out.println("Ошибка: файл уже существует - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}