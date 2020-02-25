package info.javalab.inutrihelp.web;

import info.javalab.inutrihelp.model.AbstractBaseEntity;

import static info.javalab.inutrihelp.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int id = AbstractBaseEntity.START_SEQ;

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}