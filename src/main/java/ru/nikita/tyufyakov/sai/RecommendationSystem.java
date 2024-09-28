package ru.nikita.tyufyakov.sai;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.nikita.tyufyakov.sai.model.Hero;

public class RecommendationSystem {
    private KnowledgeBase knowledgeBase;

    public RecommendationSystem() {
        knowledgeBase = new KnowledgeBase(); // Загрузка из ресурса
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Введение пользователя
        System.out.println("Привет! Введите запрос в формате:");
        System.out.println("\"Привет, я хочу играть на [роль] на персонаже с атрибутом [атрибут]\"");
        System.out.println("Введите 'exit' для выхода из программы.");

        while (true) {
            // Получаем строку ввода от пользователя
            String input = scanner.nextLine().trim().toLowerCase();

            // Проверяем, хочет ли пользователь выйти
            if (input.equals("exit")) {
                System.out.println("Выход из программы. Спасибо за использование!");
                break; // Завершаем цикл
            }

            // Парсим ввод пользователя
            String role = extractRole(input);
            String attribute = extractAttribute(input);

            if (role == null || attribute == null) {
                System.out.println("Не удалось распознать атрибут или роль. Попробуйте ещё раз.");
                continue; // Продолжаем цикл, чтобы снова запрашивать ввод
            }

            // Получаем героев по запросу
            List<Hero> heroesByAttribute = knowledgeBase.getHeroesByAttribute(attribute);
            List<Hero> heroesByRole = knowledgeBase.getHeroesByRole(role);

            // Предоставляем рекомендации
            System.out.println("\nРекомендации героев с атрибутом '" + attribute + "' и ролью '" + role + "':");

            boolean found = false;
            for (Hero hero : heroesByAttribute) {
                if (heroesByRole.contains(hero)) {
                    System.out.println("- " + hero.getName());
                    found = true;
                }
            }

            if (!found) {
                System.out.println("К сожалению, нет героев, удовлетворяющих критериям.");
            }
        }

        scanner.close(); // Закрываем сканер после завершения цикла
    }

    // Метод для извлечения роли из строки пользователя
    private String extractRole(String input) {
        // Используем регулярное выражение для поиска ролей
        Pattern pattern = Pattern.compile("(carry|midlane|offlane|soft_support|hard_support)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);  // Возвращаем найденную роль
        }

        return null;  // Роль не найдена
    }

    // Метод для извлечения атрибута из строки пользователя
    private String extractAttribute(String input) {
        // Используем регулярное выражение для поиска атрибутов
        Pattern pattern = Pattern.compile("(agility|strength|universal|intelligence)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);  // Возвращаем найденный атрибут
        }

        return null;  // Атрибут не найден
    }

    public static void main(String[] args) {
        RecommendationSystem system = new RecommendationSystem();
        system.start();
    }
}
