package info.javalab.inurrihelp.util;

import info.javalab.inutrihelp.model.Meal;
import info.javalab.inutrihelp.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {

    static Map<LocalDate,Integer > mapDay = new HashMap<>();

    private static final int DEFAULT_CALORIES_PER_DAY = 2000;
    public static void main(String[] args) {

        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 600),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println("getFilteredWithExcess: ");
        List<MealTo> mealsWithExcess = getFilteredWithExcess(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExcess.forEach(System.out::println);

        System.out.println("getFilteredWithExcessByRecursion: ");
        List<MealTo> mealsWithExcessRec = getFilteredWithExcessByRecursion(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExcessRec.forEach(System.out::println);

        System.out.println("getFilteredWithExcessByCycle: ");
        System.out.println(getFilteredWithExcessByCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealTo> getFilteredWithExcess(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealTo> getFilteredWithExcessByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealTo> mealsWithExcess = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExcess.add(createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExcess;
    }

    private static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List <MealTo> getFilteredWithExcessByRecursion(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<MealTo> list = new ArrayList<>();

        mapDay.merge(meals.get(0).getDateTime().toLocalDate(), meals.get(0).getCalories(), (a, b) -> a + b);
        if (meals.size() > 1){
            list.addAll(getFilteredWithExcessByRecursion(meals.subList(1, meals.size()), startTime, endTime, caloriesPerDay));

        }
        LocalTime time = meals.get(0).getDateTime().toLocalTime();
        if (TimeUtil.isBetween(time, startTime, endTime)){
            list.add(new MealTo(meals.get(0).getDateTime(),
                    meals.get(0).getDescription(),
                    meals.get(0).getCalories(),
                    mapDay.get(meals.get(0).getDateTime().toLocalDate()) <= caloriesPerDay));
        }

        return list;

    }
}