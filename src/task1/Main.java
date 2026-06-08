package task1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Копирование файла");

        System.out.println("Выберите способ копирования:");
        System.out.println("1 - Классический IO");
        System.out.println("2 - NIO Channels");
        System.out.println("3 - NIO2 Files");

        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введите путь к файлу, который нужно скопировать: ");
        String sourcePath = scanner.nextLine();

        System.out.print("Введите путь куда скопировать файл: ");
        String targetPath = scanner.nextLine();
        FileCopy copier;
        switch (choice) {
            case 1:
                copier = new ClassicIOCopy();
                System.out.println("Выбран способ: Классический IO");
                break;
            case 2:
                copier = new NioChannelCopy();
                System.out.println("Выбран способ: NIO Channels");
                break;
            case 3:
                copier = new Nio2FilesCopy();
                System.out.println("Выбран способ: NIO2 Files");
                break;
            default:
                System.out.println("Неверный выбор. Завершение программы.");
                scanner.close();
                return;
        }

        copier.copyFile(sourcePath, targetPath, scanner);

        scanner.close();
    }
}