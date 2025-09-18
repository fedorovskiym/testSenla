package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


// Класс валюты
class Currency {
    String currencyCode;
    String currencySymbol;
    String currencyCountry;

    Currency(String currencyCode, String currencySymbol, String currencyCountry) {
        this.currencyCode = currencyCode;
        this.currencySymbol = currencySymbol;
        this.currencyCountry = currencyCountry;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }


    public String getCurrencySymbol() {
        return currencySymbol;
    }


    public String getCurrencyCountry() {
        return currencyCountry;
    }

}

// Основной класс для конвертации валют
class CurrencyConverter {

    Currency[] currencies;
    HashMap<Currency, HashMap<Currency, Double>> exchangeRates;
    Scanner scanner = new Scanner(System.in);

    //  Инициализация курсов валют
    public void initRates() {

        exchangeRates = new HashMap<>();

        currencies = new Currency[]{
                new Currency("USD", "$", "USA"),
                new Currency("EUR", "€", "Europe"),
                new Currency("GBR", "£", "Great Britain"),
                new Currency("JPY", "¥", "Japan"),
                new Currency("RUB", "₽", "Russia")
        };

//      Матрица курсов
        double[][] rates = {
                //USD,  EUR,  GBP,   JPY,  RUB
                {1.00, 0.85, 0.73, 146.82, 83},    // USD -> другие
                {1.18, 1.00, 0.87, 173.61, 98.3},    // EUR -> другие
                {1.36, 1.15, 1.00, 200.12, 113.35},   // GBP -> другие
                {0.0068, 0.0058, 0.005, 1.00, 0.57}, // JPY -> другие
                {0.012, 0.01, 0.0089, 1.77, 1.00}  // RUB -> другие
        };

//      Заполнение HashMap курсами
        for (int i = 0; i < currencies.length; i++) {
            Currency fromCurrency = currencies[i];
            HashMap<Currency, Double> targetRate = new HashMap<>();

            for (int j = 0; j < currencies.length; j++) {
                Currency targetCurrency = currencies[j];
                targetRate.put(targetCurrency, rates[i][j]);
            }
            exchangeRates.put(fromCurrency, targetRate);
        }

    }

    //  Метод для показа курсов для всех валют
    public void showRates(HashMap<Currency, HashMap<Currency, Double>> map) {
        for (Map.Entry<Currency, HashMap<Currency, Double>> entry : map.entrySet()) {

            for (Map.Entry<Currency, Double> entry1 : entry.getValue().entrySet()) {

                System.out.println("1 " + entry.getKey().getCurrencyCode() + " (" + entry.getKey().getCurrencyCountry() + ") " + " -->  1 "
                        + entry1.getKey().getCurrencyCode() + " (" + entry1.getKey().getCurrencyCountry() + ") " + " = " + entry1.getValue());
            }
            System.out.println();
        }
    }

    //  Метод для показа курсов для конкретной валюты
    public void showRates(HashMap<Currency, HashMap<Currency, Double>> map, String code) {

        for (Map.Entry<Currency, HashMap<Currency, Double>> entry : map.entrySet()) {

            if (entry.getKey().getCurrencyCode().equalsIgnoreCase(code)) {
                for (Map.Entry<Currency, Double> entry1 : entry.getValue().entrySet()) {

                    System.out.println("1 " + entry.getKey().getCurrencyCode() + " (" + entry.getKey().getCurrencyCountry() + ") " + " -->  1 "
                            + entry1.getKey().getCurrencyCode() + " (" + entry1.getKey().getCurrencyCountry() + ") " + " = " + entry1.getValue());
                }
                System.out.println();
            }

        }

    }

    //  Метод для вывода существующих валют
    public void showCurrencies(Currency[] currencies) {

        for (Currency currency : currencies) {
            System.out.println(currency.getCurrencyCountry() + " " + currency.getCurrencyCode() + " " + currency.getCurrencySymbol());
        }

    }

    //  Проверка на существование валюты
    public boolean isCurrencyExists(Currency[] currencies, String currencyCode) {
        for (Currency currency : currencies) {
            if (currency.getCurrencyCode().equalsIgnoreCase(currencyCode)) {
                return true;
            }
        }
        return false;
    }

    //  Метод для конвертации валют
    public double convertCurrency(String fromCurrencyCode, String targetCurrencyCode, Double amount) {
        Currency fromCurrency = getCurrencyByCode(fromCurrencyCode);
        Currency targetCurrency = getCurrencyByCode(targetCurrencyCode);

        HashMap<Currency, Double> rates = exchangeRates.get(fromCurrency);
        Double rate = rates.get(targetCurrency);
        return amount * rate;
    }

    //  Метод для конвертации получения валюты по ее коду
    public Currency getCurrencyByCode(String code) {
        for (Currency currency : exchangeRates.keySet()) {
            if (currency.getCurrencyCode().equalsIgnoreCase(code)) {
                return currency;
            }
        }
        return null;
    }

    //  Метод для получения валидных кодов валют
    public String getValidCurrencyCode(String message) {
        while (true) {

            System.out.println(message);
            String code = scanner.nextLine();
            if (isCurrencyExists(currencies, code) || code.isEmpty()) {
                return code;
            }
            System.out.println("Неверный код валюты! Доступные: USD, EUR, GBR, JPY, RUB");
        }
    }

    //  Метод для получения валидной суммы конвертации
    public Double getValidAmount() {
        while (true) {
            System.out.print("Введите сумму, которую хотите конвертировать: ");
            String stringAmount = scanner.nextLine();
            try {
                return Double.parseDouble(stringAmount);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }
    }

    public void action() {
        initRates();
        System.out.println("Консольное приложение 'Курс валют'");

        while (true) {
            System.out.println(
                    "1 - Посмотреть валюты доступные для конвертации\n" +
                            "2 - Посмотреть курсы валют\n" +
                            "3 - Конвертация валют\n" +
                            "0 - Выход из программы"
            );

            int userAction = scanner.nextInt();
            switch (userAction) {

                case 1: {
                    showCurrencies(currencies);
                    break;
                }

                case 2: {
                    scanner.nextLine();
                    String userCurrencyCode = getValidCurrencyCode("Введите код валюты (USD, EUR, RUB, GBR, JPY), чтобы посмотреть ее курсы или оставьте пустым, чтобы увидеть все курсы");
                    if (userCurrencyCode.isEmpty()) {
                        showRates(exchangeRates);
                        break;
                    }

                    showRates(exchangeRates, userCurrencyCode);

                    break;
                }

                case 3: {
                    scanner.nextLine();
                    String fromCurrencyCode = getValidCurrencyCode("Напишите код валюты (USD, EUR, RUB, GBR, JPY), которую хотите перевести: ");

                    String targetCurrencyCode = getValidCurrencyCode("Напишите код валюты (USD, EUR, RUB, GBR, JPY), в которую хотите перевести: ");

                    Double amount = getValidAmount();
                    System.out.println(fromCurrencyCode + "   " + targetCurrencyCode + "   " + amount);
                    double convertedAmount = convertCurrency(fromCurrencyCode, targetCurrencyCode, amount);
                    System.out.println(amount + " " + fromCurrencyCode + " --> " + convertedAmount + " " + targetCurrencyCode);

                    break;
                }

                case 0: {
                    return;
                }

                default: {
                    break;
                }
            }

        }
    }
}

public class ExchangeRate {
    public static void main(String[] args) {
        CurrencyConverter currencyConverter = new CurrencyConverter();
        currencyConverter.action();
    }
}
