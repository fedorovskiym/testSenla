package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyCountry() {
        return currencyCountry;
    }

    public void setCurrencyCountry(String currencyCountry) {
        this.currencyCountry = currencyCountry;
    }
}

class CurrencyConverter {

    Currency[] currencies;
    HashMap<Currency, HashMap<Currency, Double>> exchangeRates;
    Scanner scanner = new Scanner(System.in);

    public void initRates() {

        exchangeRates = new HashMap<>();

        currencies = new Currency[]{
                new Currency("USD", "$", "USA"),
                new Currency("EUR", "€", "Europe"),
                new Currency("GBR", "£", "Great Britain"),
                new Currency("JPY", "¥", "Japan"),
                new Currency("RUB", "₽", "Russia")
        };

        double[][] rates = {
                //USD,  EUR,  GBP,   JPY,  RUB
                {1.00, 0.85, 0.73, 146.82, 83},    // USD -> другие
                {1.18, 1.00, 0.87, 173.61, 98.3},    // EUR -> другие
                {1.36, 1.15, 1.00, 200.12, 113.35},   // GBP -> другие
                {0.0068, 0.0058, 0.005, 1.00, 0.57}, // JPY -> другие
                {0.012, 0.01, 0.0089, 1.77, 1.00}  // RUB -> другие
        };

        for (int i = 0; i < currencies.length; i++) {
            Currency fromCurrency = currencies[i];
            HashMap<Currency, Double> targetRate = new HashMap<>();

            for (int j = 0; j < currencies.length; j++) {
                if (i != j) {
                    Currency targetCurrency = currencies[j];
                    targetRate.put(targetCurrency, rates[i][j]);
                }
            }
            exchangeRates.put(fromCurrency, targetRate);
        }

    }

    public void showRates(HashMap<Currency, HashMap<Currency, Double>> map) {
        for (Map.Entry<Currency, HashMap<Currency, Double>> entry : map.entrySet()) {

            for (Map.Entry<Currency, Double> entry1 : entry.getValue().entrySet()) {

                System.out.println("1 " + entry.getKey().getCurrencyCode() + " -->  1 " + entry1.getKey().getCurrencyCode() + " = " + entry1.getValue());
            }
            System.out.println();
        }
    }

    public void showRates(HashMap<Currency, HashMap<Currency, Double>> map, String code) {

        for (Map.Entry<Currency, HashMap<Currency, Double>> entry : map.entrySet()) {

            if (entry.getKey().getCurrencyCode().equalsIgnoreCase(code)) {
                for (Map.Entry<Currency, Double> entry1 : entry.getValue().entrySet()) {

                    System.out.println("1 " + entry.getKey().getCurrencyCode() + " -->  1 " + entry1.getKey().getCurrencyCode() + " = " + entry1.getValue());
                }
                System.out.println();
            }

        }

    }

    public void showCurrencies(Currency[] currencies) {

        for (Currency currency : currencies) {
            System.out.println(currency.getCurrencyCountry() + " " + currency.getCurrencyCode() + " " + currency.getCurrencySymbol());
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
                            "4 - Добавить свою валюту\n" +
                            "0 - Выход из программы"
            );

            int userAction = scanner.nextInt();
            switch (userAction) {

                case 1: {
                    showCurrencies(currencies);
                    break;
                }

                case 2: {
                    System.out.println("Напишите код валюты для которой хотите посмотреть курсы (оставьте поле ввода пустым если хотите посмотреть для всех валют): ");
                    String userCurrencyCode = scanner.next();

                    while (!Arrays.stream(currencies).anyMatch(userCurrencyCode.toUpperCase()::equals)) {
                        System.out.println("Такой валюты не существует!");
                        System.out.println("Напишите код валюты для которой хотите посмотреть курсы (оставьте поле ввода пустым если хотите посмотреть для всех валют): ");
                        userCurrencyCode = scanner.next();
                    }

                    showRates(exchangeRates, userCurrencyCode);

                    break;
                }

                case 3: {
                    break;
                }

                case 4: {
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
