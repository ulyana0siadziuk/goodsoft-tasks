package task1.copy.nio2;

import task1.copy.FileCopy;
import task1.util.FileCopyHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Nio2FilesCopy implements FileCopy {

    @Override
    public void copyFile(String sourcePath, String targetPath, Scanner scanner) {
        Path source = Paths.get(sourcePath);
        Path target = FileCopyHelper.resolveTarget(source, Paths.get(targetPath));

        if (!FileCopyHelper.validateSource(source)) {
            return;
        }
        if (!FileCopyHelper.confirmOverwrite(target, scanner)) {
            return;
        }

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Копирование завершено успешно (NIO2 Files)!");

        } catch (NoSuchFileException e) {
            System.out.println("Ошибка: файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}