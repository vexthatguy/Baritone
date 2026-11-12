package io.github.itzispyder.clickcrystals.modules;

import io.github.itzispyder.clickcrystals.modules.settings.Setting;
import java.util.ArrayList;
import java.util.List;

/**
 * Module base (port of ClickCrystals' Module, without tick behavior).
 */
public abstract class Module {
    private final String name;
    private final String description;
    private final Category category;
    private final List<Setting<?>> settings = new ArrayList<>();
    private boolean enabled;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
        this.category.addModule(this);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public void addSetting(Setting<?> setting) {
        this.settings.add(setting);
    }

    public List<Setting<?>> getSettings() {
        return this.settings;
    }

    public <T extends Setting<?>> T getSetting(Class<T> type, String name) {
        for (Setting<?> s : this.settings) {
            if (type.isInstance(s) && s.getName().equals(name)) {
                return type.cast(s);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
