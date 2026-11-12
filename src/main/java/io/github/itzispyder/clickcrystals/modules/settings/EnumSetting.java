package io.github.itzispyder.clickcrystals.modules.settings;

/**
 * Enum setting stub (port of ClickCrystals' EnumSetting) for compiling the
 * ported UI; unused by Baritone's boolean-only settings.
 */
public class EnumSetting<T extends Enum<T>> extends Setting<T> {
    public EnumSetting(String name, String description, T def) {
        super(name, description, def);
    }

    public static <T extends Enum<T>> EnumSetting<T> create(String name, String description, T def) {
        return new EnumSetting<>(name, description, def);
    }
}
