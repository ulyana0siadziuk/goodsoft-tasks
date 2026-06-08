package task1;
import java.io.*;
import java.util.Scanner;

public class ClassicIOCopy implements FileCopy {

    @Override
    public void copyFile(String sourcePath, String targetPath, Scanner scanner) {
        File sourceFile  = new File(sourcePath);
        File targetFile  = new File(targetPath);
        if (targetFile.exists() && targetFile.isDirectory()) {
            targetFile = new File(targetFile, sourceFile.getName());
            System.out.println("Путь - папка. Файл будет создан: " + targetFile.getPath());
        }
        try{
            if(!sourceFile.exists()){
                System.out.println("Файла не существует");
                return;
            }
            if (!sourceFile.isFile()) {
                System.out.println("Это не файл");
                return;
            }

            if(targetFile.exists()){
                System.out.println("Файл цде существует. Пересохранить файл? (д, н)");
            String answer = scanner.nextLine();
                if (!answer.equalsIgnoreCase("д")) {
                    System.out.println("Копирование отменено.");
                    return;
                }
            }

            try(FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(targetFile);){
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println("Успешное копирвование");
            }
        } catch (FileNotFoundException e){
            System.out.println("Ошибка: файл не найден - " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}
