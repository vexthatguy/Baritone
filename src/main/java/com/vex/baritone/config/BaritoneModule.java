package com.vex.baritone.config;

import com.vex.baritone.api.Settings;
import com.vex.baritone.api.utils.SettingsUtil;
import io.github.itzispyder.clickcrystals.modules.Category;
import io.github.itzispyder.clickcrystals.modules.Module;

/**
 * A ClickCrystals Module backed by a Baritone boolean setting.
 */
public class BaritoneModule extends Module {
    private final Settings.Setting<Boolean> setting;

    public BaritoneModule(Category category, String name, String description, Settings.Setting<Boolean> setting) {
        super(name, description, category);
        this.setting = setting;
        this.setEnabled(setting.value);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.setting != null) {
            this.setting.value = enabled;
            SettingsUtil.save(com.vex.baritone.api.BaritoneAPI.getSettings());
        }
    }
}
