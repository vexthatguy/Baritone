package io.github.itzispyder.clickcrystals.modules;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Module categories registry (port of ClickCrystals' Categories).
 */
public final class Categories {
    public static final Category MOVEMENT = new Category("Movement", 0);
    public static final Category MINING = new Category("Mining", 1);
    public static final Category SAFETY = new Category("Safety", 2);
    public static final Category ADVANCED = new Category("Advanced", 3);

    private static final Map<String, Category> CATEGORIES = new LinkedHashMap<>();

    static {
        CATEGORIES.put(MOVEMENT.getName(), MOVEMENT);
        CATEGORIES.put(MINING.getName(), MINING);
        CATEGORIES.put(SAFETY.getName(), SAFETY);
        CATEGORIES.put(ADVANCED.getName(), ADVANCED);
    }

    private Categories() {
    }

    public static Map<String, Category> getCategories() {
        return CATEGORIES;
    }

    public static Set<String> getNames() {
        return CATEGORIES.keySet();
    }

    public static Category get(String name) {
        return CATEGORIES.get(name);
    }
}
