package io.github.itzispyder.clickcrystals.modules.settings;

/**
 * Boolean setting (port of ClickCrystals' BooleanSetting).
 */
public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name, String description, boolean def) {
        super(name, description, def);
    }

    public static BooleanSetting create(String name, String description, boolean def) {
        return new BooleanSetting(name, description, def);
    }
}
