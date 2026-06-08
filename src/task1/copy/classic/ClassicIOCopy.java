package task1.copy.classic;

import task1.copy.FileCopy;
import task1.util.FileCopyHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ClassicIOCopy implements FileCopy {

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

        File sourceFile = source.toFile();
        File targetFile = target.toFile();

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("Копирование завершено успешно (Классический IO)!");

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}