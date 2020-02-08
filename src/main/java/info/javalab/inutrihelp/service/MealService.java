package info.javalab.inutrihelp.service;

import info.javalab.inutrihelp.model.Meal;
import info.javalab.inutrihelp.repository.MealRepository;
import info.javalab.inutrihelp.util.DateTimeUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static info.javalab.inutrihelp.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Meal> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetween(
                DateTimeUtil.createDateTime(startDate, LocalDate.MIN, LocalTime.MIN),
                DateTimeUtil.createDateTime(endDate, LocalDate.MAX, LocalTime.MAX), userId);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }
}