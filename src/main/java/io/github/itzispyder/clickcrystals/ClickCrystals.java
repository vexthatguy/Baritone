package io.github.itzispyder.clickcrystals;

import io.github.itzispyder.clickcrystals.client.system.ClickCrystalsSystem;
import io.github.itzispyder.clickcrystals.client.system.Config;
import io.github.itzispyder.clickcrystals.client.system.Version;

/**
 * Minimal facade matching what the ported UI references.
 */
public final class ClickCrystals {
    public static final String version = Version.VERSION;
    public static Config config = new Config();
    public static ClickCrystalsSystem system = ClickCrystalsSystem.getInstance();

    private ClickCrystals() {
    }
}
