package org.example;

import java.util.*;


class WordRepository {
    List<String> wordsList = Arrays.asList("Компьютер", "Клавиатура", "Мышка", "Программа", "Алгоритм");

    private final Random random = new Random();

    public String getRandomWord() {
        return wordsList.get(random.nextInt(wordsList.size())).toLowerCase();
    }
}

class GameState {
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
    private String randomWord;
    private char[] secretWord;
    private int errorsCount = 0;
    private final List<String> enteredLetters = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void initGame() {
        randomWord = wordRepository.getRandomWord().toLowerCase();
        secretWord = new char[randomWord.length()];
        Arrays.fill(secretWord, '*');
        errorsCount = 0;
        enteredLetters.clear();
    }

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

    public boolean isRussianLetter(String letter) {
        String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        return russianAlphabet.contains(letter);
    }

    public void showWord(char[] array) {
        System.out.print("Слово: ");
        for (char c : array) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public char[] setLetterInsteadPass(String exampleLetter, char[] array, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == exampleLetter.charAt(0)) {
                array[i] = exampleLetter.charAt(0);
            }
        }
        return array;
    }

    public boolean checkWin(char[] array) {
        for (char c : array) {
            if (c == '*') {
                return false;
            }
        }
        return true;
    }

    public void showEnteredLetters() {
        System.out.println("Использованные буквы: " + enteredLetters);
    }

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
                break;
            }
        }
        System.out.println("Вы проиграли!");
    }
}


public class TheGallowsGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            GameState gameState = new GameState();
            gameState.playGame();

            System.out.println("Сыграть еще? (да/нет)");
            String userAnswer = scanner.nextLine().toLowerCase();

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
