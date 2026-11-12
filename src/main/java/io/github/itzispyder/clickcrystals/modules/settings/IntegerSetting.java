package io.github.itzispyder.clickcrystals.modules.settings;

/**
 * Integer setting stub for compiling the ported UI.
 */
public class IntegerSetting extends Setting<Integer> {
    public IntegerSetting(String name, String description, int def) {
        super(name, description, def);
    }

    public static IntegerSetting create(String name, String description, int def) {
        return new IntegerSetting(name, description, def);
    }
}
