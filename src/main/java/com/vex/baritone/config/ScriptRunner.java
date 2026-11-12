package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.IBaritoneChatControl;
import com.vex.baritone.api.event.events.TickEvent;
import com.vex.baritone.api.event.listener.AbstractGameEventListener;
import com.vex.baritone.api.utils.Helper;
import java.util.Iterator;
import java.util.List;

/**
 * Runs a script one command per game tick so the client never stalls.
 * Stops on the first failed command or when #script stop is used.
 */
public final class ScriptRunner implements AbstractGameEventListener {

    private static final ScriptRunner INSTANCE = new ScriptRunner();
    private static boolean registered = false;

    private IBaritone baritone;
    private String name;
    private Iterator<String> lines;
    private boolean active;
    private int lastCount = -1;

    private ScriptRunner() {
    }

    public static void stop() {
        INSTANCE.active = false;
        INSTANCE.lines = null;
        INSTANCE.name = null;
        INSTANCE.baritone = null;
    }

    public static boolean run(IBaritone baritone, String name) {
        List<String> script = ScriptManager.load(baritone.getPlayerContext(), name);
        if (script.isEmpty()) {
            Helper.HELPER.logDirect("Script \"" + name + "\" not found");
            return false;
        }
        INSTANCE.baritone = baritone;
        INSTANCE.name = name;
        INSTANCE.lines = script.iterator();
        INSTANCE.active = true;
        INSTANCE.lastCount = -1;
        if (!registered) {
            baritone.getGameEventHandler().registerEventListener(INSTANCE);
            registered = true;
        }
        Helper.HELPER.logDirect("Running script \"" + name + "\"");
        return true;
    }

    @Override
    public void onTick(TickEvent event) {
        if (!active || lines == null || baritone == null) {
            return;
        }
        if (event.getCount() == lastCount) {
            return; // same tick delivered twice (IN/OUT)
        }
        lastCount = event.getCount();
        if (!lines.hasNext()) {
            finish(true);
            return;
        }
        String raw = lines.next();
        String line = raw.trim();
        if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
            return;
        }
        if (line.startsWith(IBaritoneChatControl.FORCE_COMMAND_PREFIX)) {
            line = line.substring(1);
        }
        if (!baritone.getCommandManager().execute(line)) {
            Helper.HELPER.logDirect("Script stopped at: " + raw);
            finish(false);
        }
    }

    private void finish(boolean done) {
        active = false;
        lines = null;
        String n = name;
        name = null;
        baritone = null;
        Helper.HELPER.logDirect(done
                ? "Script \"" + n + "\" finished"
                : "Script \"" + n + "\" stopped");
    }
}
