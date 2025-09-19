package org.example;

import java.util.*;

// Класс для генерации пароля
class GeneratorOptions {
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CHARS = CHARS.toUpperCase();
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+";
    private static final String ALL_CHARS = CHARS + UPPER_CHARS + DIGITS + SPECIAL;
    private static final Random random = new Random();

    // Генерирует пароль заданной длины
    public String generatePassword(int passwordLength) {
        StringBuilder password = new StringBuilder();

        // Добавляем по одному символу каждого типа
        password.append(CHARS.charAt(random.nextInt(CHARS.length())));
        password.append(UPPER_CHARS.charAt(random.nextInt(CHARS.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Заполняем оставшиеся символы
        for (int i = 4; i < passwordLength; i++) {
            password.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        return shuffleString(new String(password));
    }

    // Перемешивает символы в строке
    public String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled += letter;
        }
        return shuffled;
    }
}

// Основной класс для взаимодействия с пользователем
public class PasswordGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GeneratorOptions generatorOptions = new GeneratorOptions();

        while (true) {
            // Запрашиваем длину пароля
            System.out.println("Введите длину пароля (8 - 12 символов): ");
            int passwordLength = scanner.nextInt();

            // Проверяем корректность длины
            while (passwordLength < 8 || passwordLength > 12) {
                System.out.println("Ошибка, длина пароля должна быть от 8 до 12 символов");
                System.out.println("Введите длину пароля (8 - 12 символов): ");
                passwordLength = scanner.nextInt();
            }

            // Генерируем и выводим пароль
            String password = generatorOptions.generatePassword(passwordLength);
            System.out.println("Сгенерированный пароль: " + password);

            // Запрашиваем, продолжить ли
            System.out.println("Сгенерировать еще пароль? \n" +
                    "1 - да\n" +
                    "2 - нет");
            int action = scanner.nextInt();
            while (action != 1 && action != 2) {
                System.out.println("Неверный ввод!");
                System.out.println("Сгенерировать еще пароль? \n" +
                        "1 - да\n" +
                        "2 - нет");
                action = scanner.nextInt();
            }

            if (action == 2) {
                break;
            }
        }
        scanner.close();
    }
}