package io.github.itzispyder.clickcrystals.modules.settings;

/**
 * Double setting stub for compiling the ported UI.
 */
public class DoubleSetting extends Setting<Double> {
    public DoubleSetting(String name, String description, double def) {
        super(name, description, def);
    }

    public static DoubleSetting create(String name, String description, double def) {
        return new DoubleSetting(name, description, def);
    }
}
