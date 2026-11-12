package com.vex.baritone.config;

import com.vex.baritone.api.Settings;

/**
 * Reads a Settings field by name. Newer stock jars have settings that older
 * ones do not, so GUI code should look them up reflectively and skip what is
 * missing.
 */
public final class SettingRefs {

    private SettingRefs() {
    }

    public static Settings.Setting<Boolean> boolSetting(Settings settings, String name) {
        try {
            java.lang.reflect.Field f = Settings.class.getField(name);
            Object v = f.get(settings);
            if (v instanceof Settings.Setting<?> s) {
                @SuppressWarnings("unchecked")
                Settings.Setting<Boolean> b = (Settings.Setting<Boolean>) s;
                return b;
            }
            return null;
        } catch (Throwable t) {
            return null;
        }
    }
}
