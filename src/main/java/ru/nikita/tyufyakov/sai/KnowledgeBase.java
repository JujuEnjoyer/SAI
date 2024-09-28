package ru.nikita.tyufyakov.sai;

import ru.nikita.tyufyakov.sai.model.Hero;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeBase {
    private List<Hero> heroes;

    public KnowledgeBase() {
        heroes = new ArrayList<>();
        loadHeroesFromResource();
    }

    // Загрузка героев из файла ресурса bz.pl
    private void loadHeroesFromResource() {
        // Используем getResourceAsStream для загрузки из ресурсов
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("bz.pl");

        if (inputStream == null) {
            System.out.println("Ошибка: файл базы знаний не найден в ресурсах.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("hero_attribute")) {
                    // Парсим строку формата hero_attribute(anti_mage, agility).
                    String content = line.replace("hero_attribute(", "").replace(").", "");
                    String[] parts = content.split(",");

                    if (parts.length >= 2) {
                        String name = parts[0].replace("_", " "); // Заменяем подчеркивание на пробел
                        String attribute = parts[1];
                        // Ищем героя по имени, если он уже существует
                        Hero hero = findOrCreateHero(name);
                        hero.setAttribute(attribute); // Устанавливаем атрибут
                    } else {
                        System.out.println("Ошибка: некорректный формат строки: " + line);
                    }
                } else if (line.startsWith("priority_role")) {
                    // Парсим строку формата priority_role(anti_mage, carry).
                    String content = line.replace("priority_role(", "").replace(").", "");
                    String[] parts = content.split(",");

                    if (parts.length >= 2) {
                        String name = parts[0].replace("_", " ");
                        String role = parts[1];
                        // Ищем героя по имени, если он уже существует
                        Hero hero = findOrCreateHero(name);
                        hero.setRole(role); // Устанавливаем роль
                    } else {
                        System.out.println("Ошибка: некорректный формат строки: " + line);
                    }
                }
            }
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла базы знаний: " + e.getMessage());
            }
        }
    private Hero findOrCreateHero(String name) {
        for (Hero hero : heroes) {
            if (hero.getName().equalsIgnoreCase(name)) {
                return hero;
            }
        }
        Hero newHero = new Hero();
        newHero.setName(name);
        heroes.add(newHero);
        return newHero;
    }

    // Метод для получения героев по атрибуту
    public List<Hero> getHeroesByAttribute(String attribute) {
        List<Hero> result = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.getAttribute().equalsIgnoreCase(attribute)) {
                result.add(hero);
            }
        }
        return result;
    }

    // Метод для получения героев по роли
    public List<Hero> getHeroesByRole(String role) {
        List<Hero> result = new ArrayList<>();
        for (Hero hero : heroes) {
            if (hero.getRole().equalsIgnoreCase(role)) {
                result.add(hero);
            }
        }
        return result;
    }
}

