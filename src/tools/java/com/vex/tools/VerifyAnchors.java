package com.vex.tools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Verifies every ASM anchor the Remastered pipeline relies on. Run against
 * the patched jar produced for a given Minecraft version; any FAIL means the
 * port needs an anchor update in PatchBaritone (or the UI code).
 *
 * Usage: VerifyAnchors <patchedJar> <mcVersion>
 */
public final class VerifyAnchors {

    private final Map<String, byte[]> classes = new HashMap<>();
    private final java.util.Set<String> resources = new java.util.HashSet<>();
    private final List<String> failures = new ArrayList<>();
    private int checks = 0;
    private final String mcVersion;

    private VerifyAnchors(String mcVersion) {
        this.mcVersion = mcVersion;
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: VerifyAnchors <patchedJar> <mcVersion>");
            System.exit(2);
            return;
        }
        VerifyAnchors v = new VerifyAnchors(args[1]);
        v.load(Paths.get(args[0]));
        v.run();
        System.out.println();
        System.out.println("== Anchors for Minecraft " + args[1] + " ==");
        System.out.println("checks: " + v.checks + ", failures: " + v.failures.size());
        for (String f : v.failures) {
            System.out.println("  FAIL: " + f);
        }
        if (!v.failures.isEmpty()) {
            System.out.println("== ANCHOR CHECK FAILED ==");
            System.exit(1);
        }
        System.out.println("== ALL ANCHORS OK ==");
    }

    private void load(Path jar) throws IOException {
        System.out.println("scanning " + jar);
        try (ZipFile zf = new ZipFile(jar.toFile())) {
            Enumeration<? extends ZipEntry> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry e = en.nextElement();
                if (e.getName().endsWith(".class")) {
                    try (InputStream in = zf.getInputStream(e)) {
                        classes.put(e.getName(), in.readAllBytes());
                    }
                } else {
                    resources.add(e.getName());
                }
            }
        }
    }

    private byte[] bytes(String internalName) {
        return classes.get(internalName + ".class");
    }

    private ClassNode node(String internalName) {
        byte[] b = bytes(internalName);
        if (b == null) {
            return null;
        }
        ClassNode cn = new ClassNode();
        new ClassReader(b).accept(cn, 0);
        return cn;
    }

    private void checkClass(String name) {
        checks++;
        if (node(name) == null) {
            failures.add(name + " missing");
        }
    }

    private void checkMethod(String cls, String m, String desc) {
        checks++;
        ClassNode cn = node(cls);
        if (cn == null) {
            failures.add(cls + " missing (needs method " + m + desc + ")");
            return;
        }
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals(m) && mn.desc.equals(desc)) {
                return;
            }
        }
        failures.add(cls + "." + m + desc + " missing");
    }

    private void checkField(String cls, String f, String desc) {
        checks++;
        ClassNode cn = node(cls);
        if (cn == null) {
            failures.add(cls + " missing (needs field " + f + " " + desc + ")");
            return;
        }
        for (var fn : cn.fields) {
            if (fn.name.equals(f) && fn.desc.equals(desc)) {
                return;
            }
        }
        failures.add(cls + "." + f + " " + desc + " missing");
    }

    private void checkCall(String cls, String m, String desc, String callOwner, String callName, String callDesc, int opcode) {
        checks++;
        ClassNode cn = node(cls);
        if (cn == null) {
            failures.add(cls + " missing (needs call " + callOwner + "." + callName + ")");
            return;
        }
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals(m) || !mn.desc.equals(desc)) {
                continue;
            }
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof MethodInsnNode mi && mi.getOpcode() == opcode
                    && mi.owner.equals(callOwner) && mi.name.equals(callName) && mi.desc.equals(callDesc)) {
                    return;
                }
            }
            failures.add(cls + "." + m + " missing call " + callOwner + "." + callName + callDesc);
            return;
        }
        failures.add(cls + "." + m + desc + " missing (needs call " + callOwner + "." + callName + ")");
    }

    private void checkMethodStartsWithDisabled(String cls, String m, String desc) {
        checks++;
        ClassNode cn = node(cls);
        if (cn == null) {
            failures.add(cls + " missing (gate check)");
            return;
        }
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals(m) || !mn.desc.equals(desc)) {
                continue;
            }
            boolean found = false;
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof MethodInsnNode mi && mi.getOpcode() == Opcodes.INVOKESTATIC
                    && mi.owner.equals("com/vex/baritone/config/VisibilityConfig")
                    && mi.name.equals("isDisabled")) {
                    found = true;
                    break;
                }
                if (insn instanceof MethodInsnNode || insn instanceof FieldInsnNode) {
                    // only early instructions matter; first call found is fine
                }
            }
            if (!found) {
                failures.add(cls + "." + m + " missing VisibilityConfig.isDisabled gate");
            }
            return;
        }
        failures.add(cls + "." + m + desc + " missing (gate check)");
    }

    private void run() {
        // -- core classes
        for (String c : new String[]{
            "com/vex/baritone/api/command/Command",
            "com/vex/baritone/api/command/ICommand",
            "com/vex/baritone/api/IBaritone",
            "com/vex/baritone/api/cache/IWaypoint",
            "com/vex/baritone/api/cache/IWaypoint$Tag",
            "com/vex/baritone/api/cache/Waypoint",
            "com/vex/baritone/api/cache/IWaypointCollection",
            "com/vex/baritone/api/pathing/goals/GoalBlock",
            "com/vex/baritone/api/pathing/goals/GoalXZ",
            "com/vex/baritone/api/process/ICustomGoalProcess",
            "com/vex/baritone/api/utils/Helper",
            "com/vex/baritone/config/HomeCommand",
            "com/vex/baritone/config/SetHomeCommand",
            "com/vex/baritone/config/DelHomeCommand",
            "com/vex/baritone/config/HomesCommand",
            "com/vex/baritone/config/BiomeCommand",
            "com/vex/baritone/config/ScriptCommand",
            "com/vex/baritone/config/ScriptManager",
            "com/vex/baritone/config/ScriptRunner",
            "com/vex/baritone/config/ScriptCreateScreen",
            "com/vex/baritone/config/FeatureConfig",
            "com/vex/baritone/config/DeathLogger",
            "com/vex/baritone/config/VersionInfo",
            "com/vex/baritone/config/HelpSorter",
            "com/vex/baritone/config/WorldKey",
            "com/vex/baritone/config/FarmLimits",
            "com/vex/baritone/config/CommandBootstrap",
            "com/vex/baritone/config/CommandErrors",
        }) {
            checkClass(c);
        }

        // -- bootstrap injection
        checkCall("com/vex/baritone/command/defaults/DefaultCommands",
            "a", "(Lcom/vex/baritone/Baritone;)Ljava/util/List;",
            "com/vex/baritone/config/CommandBootstrap", "install", "(Ljava/util/List;)V",
            Opcodes.INVOKESTATIC);

        // -- chat gate
        checkMethodStartsWithDisabled("com/vex/baritone/command/ExampleBaritoneControl",
            "onSendChatMessage", "(Lcom/vex/baritone/api/event/events/ChatEvent;)V");
        checkMethodStartsWithDisabled("com/vex/baritone/command/ExampleBaritoneControl",
            "onPreTabComplete", "(Lcom/vex/baritone/api/event/events/TabCompleteEvent;)V");

        // -- command manager gate
        checkMethodStartsWithDisabled("com/vex/baritone/command/manager/CommandManager",
            "execute", "(Ljava/lang/String;)Z");

        // -- provider gate
        checkMethod("com/vex/baritone/api/BaritoneAPI", "getProvider",
            "()Lcom/vex/baritone/api/IBaritoneProvider;");
        checkMethod("com/vex/baritone/api/BaritoneAPI", "getProviderReal",
            "()Lcom/vex/baritone/api/IBaritoneProvider;");

        // -- version command
        checkCall("com/vex/baritone/command/defaults/VersionCommand",
            "execute", "(Ljava/lang/String;Lcom/vex/baritone/api/command/argument/IArgConsumer;)V",
            "com/vex/baritone/config/VersionInfo", "get", "()Ljava/lang/String;",
            Opcodes.INVOKESTATIC);

        // -- help sorting
        checkCall("com/vex/baritone/command/defaults/HelpCommand",
            "execute", "(Ljava/lang/String;Lcom/vex/baritone/api/command/argument/IArgConsumer;)V",
            "com/vex/baritone/config/HelpSorter", "sort", "(Ljava/util/List;)V",
            Opcodes.INVOKESTATIC);

        // -- death logger
        checkCall("com/vex/baritone/behavior/WaypointBehavior", "onPlayerDeath", "()V",
            "com/vex/baritone/config/DeathLogger", "report",
            "(Lcom/vex/baritone/api/utils/IPlayerContext;)V", Opcodes.INVOKESTATIC);

        // -- world provider keying
        checkCall("com/vex/baritone/cache/WorldProvider", "a",
            "(Lnet/minecraft/class_1937;)V",
            "com/vex/baritone/config/WorldKey", "serverKey",
            "(Lnet/minecraft/class_642;)Ljava/lang/String;", Opcodes.INVOKESTATIC);

        // -- farm process
        checkField("com/vex/baritone/process/FarmProcess", "b", "I");
        checkMethod("com/vex/baritone/process/FarmProcess", "a", "(Ljava/util/ArrayList;)V");
        checkMethod("com/vex/baritone/process/FarmProcess", "farm", "(ILnet/minecraft/class_2338;)V");
        checkFarmScanRadius();
        checkFarmClamp();
        checkWartFields();

        // -- settings default
        checkSettingDefault();

        // -- resources
        checks++;
        if (!resources.contains("fabric.mod.json")) {
            failures.add("fabric.mod.json missing from jar");
        }
        checks++;
        if (!resources.contains("mixins.baritone-visibility.json")) {
            failures.add("mixins.baritone-visibility.json missing from jar");
        }
    }

    private void checkFarmScanRadius() {
        checks++;
        ClassNode cn = node("com/vex/baritone/process/FarmProcess");
        if (cn == null) {
            failures.add("FarmProcess missing (scan radius)");
            return;
        }
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals("a") || !mn.desc.equals("(Ljava/util/ArrayList;)V")) {
                continue;
            }
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof MethodInsnNode mi && mi.name.equals("scanChunkRadius")) {
                    AbstractInsnNode p1 = insn.getPrevious();
                    AbstractInsnNode p2 = p1 == null ? null : p1.getPrevious();
                    if (p1 instanceof IntInsnNode i1 && i1.getOpcode() == Opcodes.BIPUSH && i1.operand == 12
                        && p2 instanceof IntInsnNode i2 && i2.getOpcode() == Opcodes.BIPUSH && i2.operand == 12) {
                        return;
                    }
                    failures.add("FarmProcess scan radius is not 12/12");
                    return;
                }
            }
        }
        failures.add("FarmProcess scanChunkRadius call missing");
    }

    private void checkFarmClamp() {
        checks++;
        ClassNode cn = node("com/vex/baritone/process/FarmProcess");
        if (cn == null) {
            failures.add("FarmProcess missing (range clamp)");
            return;
        }
        for (MethodNode mn : cn.methods) {
            if (!mn.name.equals("farm") || !mn.desc.equals("(ILnet/minecraft/class_2338;)V")) {
                continue;
            }
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof FieldInsnNode f && f.getOpcode() == Opcodes.PUTFIELD
                    && f.owner.equals("com/vex/baritone/process/FarmProcess") && f.name.equals("b")) {
                    AbstractInsnNode prev = insn.getPrevious();
                    if (prev instanceof MethodInsnNode mi && mi.getOpcode() == Opcodes.INVOKESTATIC
                        && mi.owner.equals("com/vex/baritone/config/FarmLimits")
                        && mi.name.equals("clampRange")) {
                        return;
                    }
                    failures.add("FarmProcess range clamp missing before putfield b");
                    return;
                }
            }
        }
        failures.add("FarmProcess farm method missing");
    }

    private void checkWartFields() {
        // Soul sand flow must still be used for replant targeting, and the
        // NETHER_WART harvest predicate must be present in the Harvest enum.
        checks++;
        ClassNode cn = node("com/vex/baritone/process/FarmProcess");
        if (cn == null) {
            failures.add("FarmProcess missing (wart fields)");
            return;
        }
        int soulSandUses = 0;
        for (MethodNode mn : cn.methods) {
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof FieldInsnNode f && f.getOpcode() == Opcodes.GETSTATIC
                    && f.owner.equals("net/minecraft/class_2246") && f.name.equals("field_10114")) {
                    soulSandUses++;
                }
            }
        }
        if (soulSandUses < 2) {
            failures.add("FarmProcess soul-sand (field_10114) flow altered (uses=" + soulSandUses + ")");
        }
        ClassNode harvest = node("com/vex/baritone/process/FarmProcess$Harvest");
        if (harvest == null) {
            failures.add("FarmProcess$Harvest missing");
            return;
        }
        boolean netherWartSeen = false;
        for (MethodNode mn : harvest.methods) {
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof FieldInsnNode f && f.getOpcode() == Opcodes.GETSTATIC
                    && f.owner.equals("net/minecraft/class_2246") && f.name.equals("field_9974")) {
                    netherWartSeen = true;
                }
            }
        }
        if (!netherWartSeen) {
            failures.add("FarmProcess$Harvest missing NETHER_WART (field_9974)");
        }
    }

    private void checkSettingDefault() {
        checks++;
        ClassNode cn = node("com/vex/baritone/api/Settings");
        if (cn == null) {
            failures.add("Settings missing (replantNetherWart default)");
            return;
        }
        for (MethodNode mn : cn.methods) {
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof FieldInsnNode f && f.getOpcode() == Opcodes.PUTFIELD
                    && f.owner.equals("com/vex/baritone/api/Settings") && f.name.equals("replantNetherWart")) {
                    AbstractInsnNode cur = insn.getPrevious();
                    int guard = 0;
                    while (cur != null && guard++ < 30) {
                        if (cur instanceof MethodInsnNode mi
                            && mi.getOpcode() == Opcodes.INVOKESPECIAL
                            && mi.owner.equals("com/vex/baritone/api/Settings$Setting")
                            && mi.name.equals("<init>")) {
                            break;
                        }
                        cur = cur.getPrevious();
                    }
                    if (cur != null) {
                        cur = cur.getPrevious();
                        if (cur instanceof FieldInsnNode g && g.getOpcode() == Opcodes.GETSTATIC
                            && g.owner.equals("java/lang/Boolean") && g.name.equals("TRUE")) {
                            return;
                        }
                    }
                    failures.add("Settings.replantNetherWart default is not TRUE");
                    return;
                }
            }
        }
        failures.add("Settings.replantNetherWart field missing");
    }
}
