package org.example;

import java.util.*;

// Класс для хранения списка слов и получения случайного слова
class WordRepository {
    List<String> wordsList = Arrays.asList("Компьютер", "Клавиатура", "Мышка", "Программа", "Алгоритм");

    private final Random random = new Random();

    // Возвращает случайное слово из списка
    public String getRandomWord() {
        return wordsList.get(random.nextInt(wordsList.size())).toLowerCase();
    }
}

// Класс, управляющий состоянием и логикой игры
class GameState {
    // Массив с изображениями виселицы для разного количества ошибок
    private final String[] stages = new String[]{

            """
    +---+
    |   |
        |
        |
        |
        |
    =========
    """,


            """
    +---+
    |   |
    O   |
        |
        |
        |
    =========
    """,


            """
    +---+
    |   |
    O   |
    |   |
        |
        |
    =========
    """,


            """
    +---+
    |   |
    O   |
   /|   |
        |
        |
    =========
    """,


            """
    +---+
    |   |
    O   |
   /|\\  |
        |
        |
    =========
    """,


            """
    +---+
    |   |
    O   |
   /|\\  |
   /    |
        |
    =========
    """,

            """
    +---+
    |   |
    O   |
   /|\\  |
   / \\  |
        |
    =========
    """
    };

    private WordRepository wordRepository = new WordRepository();
    private String randomWord; // Загаданное слово
    private char[] secretWord; // Массив символов для отображения слова (с * для неугаданных букв)
    private int errorsCount = 0; // Счетчик ошибок
    private final List<String> enteredLetters = new ArrayList<>(); // Список введенных букв
    private final Scanner scanner = new Scanner(System.in);

    // Инициализирует новую игру
    public void initGame() {
        randomWord = wordRepository.getRandomWord().toLowerCase();
        secretWord = new char[randomWord.length()];
        Arrays.fill(secretWord, '*');
        errorsCount = 0;
        enteredLetters.clear();
    }

    // Запрашивает у пользователя букву с проверками
    public String getLetter() {
        while (true) {
            System.out.println("Введите одну русскую букву:");
            String letter = scanner.nextLine().toLowerCase();

            if (letter.length() != 1) {
                System.out.println("Введите ровно одну букву!");
                continue;
            }

            if (!isRussianLetter(letter)) {
                System.out.println("Вы ввели не русскую букву!");
                continue;
            }

            if (enteredLetters.contains(letter)) {
                System.out.println("Вы уже вводили эту букву!");
                continue;
            }

            return letter;
        }
    }

    // Проверяет, является ли введенный символ русской буквой
    public boolean isRussianLetter(String letter) {
        String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        return russianAlphabet.contains(letter);
    }

    // Выводит состояние слова
    public void showWord(char[] array) {
        System.out.print("Слово: ");
        for (char c : array) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    // Заменяет * на угаданную букву
    public char[] setLetterInsteadPass(String exampleLetter, char[] array, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == exampleLetter.charAt(0)) {
                array[i] = exampleLetter.charAt(0);
            }
        }
        return array;
    }

    // Проверяет, угадано ли слово полностью
    public boolean checkWin(char[] array) {
        for (char c : array) {
            if (c == '*') {
                return false;
            }
        }
        return true;
    }

    // Выводит список использованных букв
    public void showEnteredLetters() {
        System.out.println("Использованные буквы: " + enteredLetters);
    }

    // Основной метод игры
    public void playGame() {
        initGame();
        System.out.println("Краткие правила для программы:\n" +
                "Цель игры: угадать загаданное слово по буквам.\n" +
                "\n" +
                "Как играть:\n" +
                "\n" +
                "Программа загадываю слово.\n" +
                "\n" +
                "Вы предлагаете букву.\n" +
                "\n" +
                "Если буква есть — программа откроет все её позиции в слове.\n" +
                "\n" +
                "Если буквы нет — вы теряете одну жизнь.\n" +
                "\n" +
                "Игра продолжается, пока вы не угадаете всё слово или не потеряете все жизни.\n" +
                "\n" +
                "У вас есть 6 попыток (жизней). Удачи!");

        System.out.println("Слово загадано.");

        while (errorsCount != 6) {
            showWord(secretWord);

            String letter = getLetter();

            enteredLetters.add(letter);
            boolean wordContainsLetter = randomWord.contains(letter);
            if (!wordContainsLetter) {
                errorsCount++;
                System.out.println("Такой буквы нет в загаданном слове!");
                System.out.println("Потрачено жизней: " + errorsCount);
                System.out.println(stages[errorsCount]);
            } else {
                secretWord = setLetterInsteadPass(letter, secretWord, randomWord);
                showWord(secretWord);
            }
            showEnteredLetters();
            if (checkWin(secretWord)) {
                System.out.println("Вы выиграли! Загаданное слово:" + randomWord);
                System.out.println("Потрачено жизней:" + errorsCount);
                return;
            }
        }
        System.out.println("Вы проиграли!");
        System.out.println("Открытая часть слова: ");
        showWord(secretWord);
        System.out.println("Загаданное слово: " + randomWord);
    }
}

// Основной класс для запуска игры
public class TheGallowsGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            GameState gameState = new GameState();
            gameState.playGame();

            // Запрашиваем, хочет ли пользователь сыграть еще
            System.out.println("Сыграть еще? (да/нет)");
            String userAnswer = scanner.nextLine().toLowerCase();

            // Проверяем корректность ответа
            while (!userAnswer.equals("нет") && !userAnswer.equals("да")) {
                System.out.println("Неверный ввод!");
                System.out.println("Сыграть еще? (да/нет)");
                userAnswer = scanner.nextLine().toLowerCase();
            }

            if (userAnswer.equals("да")) {
                System.out.println("Новая игра!");
            } else {
                System.out.println("Спасибо за игру!");
                break;
            }
        }
    }
}