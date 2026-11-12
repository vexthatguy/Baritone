package io.github.itzispyder.clickcrystals.modules.settings;

/**
 * Keybind setting stub for compiling the ported UI.
 */
public class KeybindSetting extends Setting<Integer> {
    public KeybindSetting(String name, String description, int def) {
        super(name, description, def);
    }

    public static KeybindSetting create(String name, String description, int def) {
        return new KeybindSetting(name, description, def);
    }
}
