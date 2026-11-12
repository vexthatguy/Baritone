package io.github.itzispyder.clickcrystals.modules;

import java.util.ArrayList;
import java.util.List;

/**
 * Category of modules (port of ClickCrystals' Category).
 */
public class Category {
    private final String name;
    private final int id;
    private final List<Module> modules = new ArrayList<>();

    public Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
