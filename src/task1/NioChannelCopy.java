package task1;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class NioChannelCopy implements FileCopy {

    @Override
    public void copyFile(String sourcePath, String targetPath, Scanner scanner) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        if (targetFile.exists() && targetFile.isDirectory()) {
            targetFile = new File(targetFile, sourceFile.getName());
            System.out.println("Целевой путь — папка. Файл будет создан: " + targetFile.getPath());
        }

        try {
            if (!sourceFile.exists()) {
                System.out.println("Ошибка: исходный файл не существует");
                return;
            }
            if (!sourceFile.isFile()) {
                System.out.println("Ошибка: исходный путь ведёт к папке, а не к файлу");
                return;
            }

            if (targetFile.exists()) {
                System.out.print("Файл уже существует. Перезаписать? (д/н): ");
                String answer = scanner.nextLine();
                if (!answer.equalsIgnoreCase("д")) {
                    System.out.println("Копирование отменено.");
                    return;
                }
            }

            try (FileChannel sourceChannel = new FileInputStream(sourceFile).getChannel();
                 FileChannel targetChannel = new FileOutputStream(targetFile).getChannel()) {

                sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);

                System.out.println("Копирование завершено успешно (NIO Channels)!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}