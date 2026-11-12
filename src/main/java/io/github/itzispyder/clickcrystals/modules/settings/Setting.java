package io.github.itzispyder.clickcrystals.modules.settings;

/**
 * Setting base (port of ClickCrystals' Setting).
 */
public abstract class Setting<T> {
    private final String name;
    private final String description;
    private T value;
    private final T def;

    public Setting(String name, String description, T def) {
        this.name = name;
        this.description = description;
        this.def = def;
        this.value = def;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public T getVal() {
        return this.value;
    }

    public void setVal(T value) {
        this.value = value;
    }

    public T getDefault() {
        return this.def;
    }
}
