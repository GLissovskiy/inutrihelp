package info.javalab.inutrihelp.repository;

import info.javalab.inutrihelp.model.Meal;

import java.util.Collection;

public interface MealRepository {

    Meal save (Meal meal);

    // false if not succeed
    boolean delete (int id);

    // null if not found
    Meal get (int id);

    Collection<Meal> getAll();
}
