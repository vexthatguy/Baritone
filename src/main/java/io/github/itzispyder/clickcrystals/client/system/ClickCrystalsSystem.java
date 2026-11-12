package io.github.itzispyder.clickcrystals.client.system;

import java.awt.Desktop;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import io.github.itzispyder.clickcrystals.client.system.scheduler.Scheduler;
import io.github.itzispyder.clickcrystals.modules.Module;
import io.github.itzispyder.clickcrystals.modules.Registry;

/**
 * Minimal ClickCrystalsSystem for the ported UI.
 */
public final class ClickCrystalsSystem {
    private static final ClickCrystalsSystem INSTANCE = new ClickCrystalsSystem();
    public final Scheduler scheduler = new Scheduler();

    private ClickCrystalsSystem() {
    }

    public static ClickCrystalsSystem getInstance() {
        return INSTANCE;
    }

    public List<Module> collectModules() {
        return new ArrayList<>(Registry.getModules());
    }

    public void openFile(String path) {
        try {
            if (Files.exists(Paths.get(path))) {
                Desktop.getDesktop().open(Paths.get(path).toFile());
            } else {
                Desktop.getDesktop().open(Paths.get(".").toFile());
            }
        } catch (Throwable ignored) {
        }
    }

    public void openUrl(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Throwable ignored) {
        }
    }

    public void printErr(String msg) {
        System.err.println("[ClickCrystals] " + msg);
    }

    public void printErrF(String format, Object... args) {
        System.err.printf("[ClickCrystals] " + format + "%n", args);
    }

    public void print(String msg) {
        System.out.println("[ClickCrystals] " + msg);
    }

    public void printF(String format, Object... args) {
        System.out.printf("[ClickCrystals] " + format + "%n", args);
    }
}
