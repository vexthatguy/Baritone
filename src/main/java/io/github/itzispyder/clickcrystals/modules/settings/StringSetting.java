package io.github.itzispyder.clickcrystals.modules.settings;

/**
 * String setting stub for compiling the ported UI.
 */
public class StringSetting extends Setting<String> {
    public StringSetting(String name, String description, String def) {
        super(name, description, def);
    }

    public static StringSetting create(String name, String description, String def) {
        return new StringSetting(name, description, def);
    }
}
