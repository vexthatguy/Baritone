package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.command.Command;
import com.vex.baritone.api.command.argument.IArgConsumer;
import com.vex.baritone.api.command.exception.CommandInvalidStateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * #script create <name> - opens the script editor GUI
 * #script list - list saved scripts
 * #script run <name> - run a saved script
 * #script delete <name> - delete a saved script
 * #script stop - stop the running script
 */
public class ScriptCommand extends SafeCommand {

    public ScriptCommand(IBaritone baritone) {
        super(baritone, "script");
    }

    @Override
    protected void doExecute(String label, IArgConsumer args) throws Throwable {
        args.requireMax(2);
        if (!args.hasAny()) {
            getLongDesc().forEach(this::logDirect);
            return;
        }
        String sub = args.getString().toLowerCase();
        switch (sub) {
            case "create":
            case "new":
            case "edit": {
                if (!args.hasExactlyOne()) {
                    throw CommandErrors.throwIt(new CommandInvalidStateException("Usage: #script " + sub + " <name>"));
                }
                String name = args.getString();
                List<String> existing = sub.equals("edit") ? ScriptManager.load(ctx, name) : List.of();
                ctx.minecraft().method_1507(new ScriptCreateScreen(null, name, existing, ctx.minecraft().field_1697.toPath()));
                break;
            }
            case "list": {
                List<String> scripts = ScriptManager.list(ctx);
                if (scripts.isEmpty()) {
                    logDirect("No scripts yet. Use #script create <name>");
                } else {
                    logDirect("Scripts (" + scripts.size() + "):");
                    scripts.forEach(this::logDirect);
                }
                break;
            }
            case "run": {
                if (!args.hasExactlyOne()) {
                    throw CommandErrors.throwIt(new CommandInvalidStateException("Usage: #script run <name>"));
                }
                ScriptRunner.run(baritone, args.getString());
                break;
            }
            case "delete":
            case "del": {
                if (!args.hasExactlyOne()) {
                    throw CommandErrors.throwIt(new CommandInvalidStateException("Usage: #script delete <name>"));
                }
                String name = args.getString();
                logDirect(ScriptManager.delete(ctx, name) ? "Deleted script \"" + name + "\"" : "Script \"" + name + "\" not found");
                break;
            }
            case "stop": {
                ScriptRunner.stop();
                logDirect("Stopping script");
                break;
            }
            default:
                throw CommandErrors.throwIt(new CommandInvalidStateException("Unknown subcommand \"" + sub + "\""));
        }
    }

    @Override
    protected Stream<String> doTabComplete(String label, IArgConsumer args) throws Throwable {
        if (!args.hasAny()) {
            return Stream.of("create", "edit", "list", "run", "delete", "stop");
        }
        String sub = args.peekString().toLowerCase();
        Stream<String> subs = Stream.of("create", "edit", "list", "run", "delete", "stop")
                .filter(s -> s.startsWith(sub));
        if (sub.equals("run") || sub.equals("delete") || sub.equals("edit")) {
            return Stream.concat(subs, ScriptManager.list(ctx).stream());
        }
        return subs;
    }

    @Override
    public String getShortDesc() {
        return "Create and run command scripts";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Scripts are lists of baritone commands that run one after another.",
                "",
                "Usage:",
                "> script create <name> - open the editor GUI",
                "> script list - list scripts",
                "> script run <name> - run a script",
                "> script delete <name> - delete a script",
                "> script stop - stop the running script"
        );
    }
}
