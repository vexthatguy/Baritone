package com.vex.tools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * Small ASM patches on the renamed Baritone jar:
 *  - ThisWayCommand: call setGoalAndPath(...) instead of setGoal(...) so the
 *    bot actually starts walking.
 *  - FarmProcess: extend the supported-crop list with kelp and chorus flower.
 *
 * Usage: PatchBaritone <inputJar> <outputJar>
 */
public final class PatchBaritone {

    private PatchBaritone() {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: PatchBaritone <inputJar> <outputJar>");
            System.exit(1);
            return;
        }
        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);
        Files.createDirectories(output.getParent());

        boolean thisWayPatched = false;
        boolean farmPatched = false;

        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(input));
             ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(output))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                byte[] data = readFully(zis);
                String name = entry.getName();
                byte[] out = data;

                if (name.equals("com/vex/baritone/command/defaults/ThisWayCommand.class")) {
                    byte[] patched = patchThisWay(data);
                    if (patched != null) {
                        out = patched;
                        thisWayPatched = true;
                    }
                } else if (name.equals("com/vex/baritone/process/FarmProcess.class")) {
                    byte[] patched = patchFarm(data);
                    if (patched != null) {
                        out = patched;
                        farmPatched = true;
                    }
                } else if (name.equals("com/vex/baritone/api/BaritoneAPI.class")) {
                    byte[] patched = patchProviderGate(data);
                    if (patched != null) {
                        out = patched;
                    }
                } else if (name.equals("com/vex/baritone/command/manager/CommandManager.class")) {
                    byte[] patched = patchCommandGate(data);
                    if (patched != null) {
                        out = patched;
                    }
                } else if (name.equals("com/vex/baritone/command/ExampleBaritoneControl.class")) {
                    byte[] patched = patchChatGate(data);
                    if (patched != null) {
                        out = patched;
                    }
                } else if (name.equals("com/vex/baritone/command/defaults/DefaultCommands.class")) {
                    byte[] patched = patchDefaultCommands(data);
                    if (patched != null) {
                        out = patched;
                    }
                } else if (name.equals("com/vex/baritone/cache/WorldProvider.class")) {
                    byte[] patched = patchWorldProvider(data);
                    if (patched != null) {
                        out = patched;
                    }
                } else if (name.equals("com/vex/baritone/api/Settings.class")) {
                    byte[] patched = patchSettingsDefault(data);
                    if (patched != null) {
                        out = patched;
                    }
                } else if (name.equals("com/vex/baritone/command/defaults/VersionCommand.class")) {
                    byte[] patched = patchVersionCommand(data);
                    if (patched != null) {
                        out = patched;
                    }
                } else if (name.equals("com/vex/baritone/command/defaults/HelpCommand.class")) {
                    byte[] patched = patchHelpSort(data);
                    if (patched != null) {
                        out = patched;
                    }
                } else if (name.equals("com/vex/baritone/behavior/WaypointBehavior.class")) {
                    byte[] patched = patchDeathWaypoint(data);
                    if (patched != null) {
                        out = patched;
                    }
                }

                zos.putNextEntry(new ZipEntry(name));
                zos.write(out);
                zos.closeEntry();
            }
        }

        System.out.println("ThisWayCommand patched: " + thisWayPatched);
        System.out.println("FarmProcess patched: " + farmPatched);
        System.out.println("wrote " + output);
    }

    private static ClassWriter cw() {
        if (System.getProperty("BT_NO_FRAMES") != null) {
            return new ClassWriter(ClassWriter.COMPUTE_MAXS);
        }
        return new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
    }

    private static byte[] emit(ClassNode cn) {
        try {
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cn.accept(cw);
            return cw.toByteArray();
        } catch (StackOverflowError | RuntimeException e) {
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);
            return cw.toByteArray();
        }
    }

    private static byte[] patchCommandGate(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        boolean any = false;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("execute") && mn.desc.equals("(Ljava/lang/String;)Z")) {
                InsnList gate = new InsnList();
                LabelNode skip = new LabelNode();
                gate.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                    "com/vex/baritone/config/VisibilityConfig", "isDisabled", "()Z", false));
                gate.add(new JumpInsnNode(Opcodes.IFEQ, skip));
                gate.add(new InsnNode(Opcodes.ICONST_0));
                gate.add(new InsnNode(Opcodes.IRETURN));
                gate.add(skip);
                mn.instructions.insert(gate);
                any = true;
            } else if (mn.name.equals("tabComplete") && mn.desc.equals("(Ljava/lang/String;)Ljava/util/stream/Stream;")) {
                InsnList gate = new InsnList();
                LabelNode skip = new LabelNode();
                gate.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                    "com/vex/baritone/config/VisibilityConfig", "isDisabled", "()Z", false));
                gate.add(new JumpInsnNode(Opcodes.IFEQ, skip));
                gate.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                    "java/util/stream/Stream", "empty", "()Ljava/util/stream/Stream;", false));
                gate.add(new InsnNode(Opcodes.ARETURN));
                gate.add(skip);
                mn.instructions.insert(gate);
                any = true;
            }
        }
        if (!any) {
            return null;
        }
        return emit(cn);
    }

    private static byte[] patchProviderGate(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        String desc = "()Lcom/vex/baritone/api/IBaritoneProvider;";
        MethodNode original = null;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("getProvider") && mn.desc.equals(desc)) {
                original = mn;
                break;
            }
        }
        if (original == null) {
            return null;
        }
        original.name = "getProviderReal";

        MethodNode gate = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "getProvider", desc, null, null);
        LabelNode skip = new LabelNode();
        gate.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "com/vex/baritone/config/VisibilityConfig", "isDisabled", "()Z", false));
        gate.instructions.add(new JumpInsnNode(Opcodes.IFEQ, skip));
        gate.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC,
            "com/vex/baritone/config/NoopBaritoneProvider", "INSTANCE",
            "Lcom/vex/baritone/config/NoopBaritoneProvider;"));
        gate.instructions.add(new InsnNode(Opcodes.ARETURN));
        gate.instructions.add(skip);
        gate.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "com/vex/baritone/api/BaritoneAPI", "getProviderReal", desc, false));
        gate.instructions.add(new InsnNode(Opcodes.ARETURN));

        cn.methods.add(gate);

        return emit(cn);
    }

    private static byte[] patchChatGate(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        boolean any = false;
        String[] voidMethods = {
            "onSendChatMessage", "(Lcom/vex/baritone/api/event/events/ChatEvent;)V",
            "onPreTabComplete", "(Lcom/vex/baritone/api/event/events/TabCompleteEvent;)V"
        };
        for (int i = 0; i < voidMethods.length; i += 2) {
            String mname = voidMethods[i];
            String mdesc = voidMethods[i + 1];
            for (MethodNode mn : cn.methods) {
                if (mn.name.equals(mname) && mn.desc.equals(mdesc)) {
                    InsnList gate = new InsnList();
                    LabelNode skip = new LabelNode();
                    gate.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                        "com/vex/baritone/config/VisibilityConfig", "isDisabled", "()Z", false));
                    gate.add(new JumpInsnNode(Opcodes.IFEQ, skip));
                    gate.add(new InsnNode(Opcodes.RETURN));
                    gate.add(skip);
                    mn.instructions.insert(gate);
                    any = true;
                }
            }
        }
        if (!any) {
            return null;
        }
        return emit(cn);
    }

    private static byte[] patchDefaultCommands(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        MethodNode target = null;
        for (MethodNode mn : cn.methods) {
            if ((mn.name.equals("a") || mn.name.equals("createAll"))
                && mn.desc.endsWith(")Ljava/util/List;")) {
                target = mn;
                break;
            }
        }
        if (target == null) {
            return null;
        }

        MethodInsnNode unmod = null;
        for (AbstractInsnNode insn : target.instructions) {
            if (insn instanceof MethodInsnNode m && m.name.equals("unmodifiableList")
                && m.owner.equals("java/util/Collections")) {
                unmod = m;
                break;
            }
        }
        if (unmod == null) {
            return null;
        }

        InsnList inject = new InsnList();
        inject.add(new VarInsnNode(Opcodes.ALOAD, 1));
        inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "com/vex/baritone/config/CommandBootstrap", "install",
            "(Ljava/util/List;)V", false));
        target.instructions.insertBefore(unmod, inject);

        return emit(cn);
    }

    private static byte[] patchWorldProvider(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        boolean any = false;
        for (MethodNode mn : cn.methods) {
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof FieldInsnNode f && f.getOpcode() == Opcodes.GETFIELD
                    && f.owner.equals("net/minecraft/class_642") && f.name.equals("field_3761")) {
                    mn.instructions.set(insn, new MethodInsnNode(Opcodes.INVOKESTATIC,
                        "com/vex/baritone/config/WorldKey", "serverKey",
                        "(Lnet/minecraft/class_642;)Ljava/lang/String;", false));
                    any = true;
                }
            }
        }
        if (!any) {
            return null;
        }
        return emit(cn);
    }

    private static byte[] patchSettingsDefault(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        boolean changed = false;
        for (MethodNode mn : cn.methods) {
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof FieldInsnNode f && f.getOpcode() == Opcodes.PUTFIELD
                    && f.owner.equals("com/vex/baritone/api/Settings") && f.name.equals("replantNetherWart")) {
                    AbstractInsnNode cur = insn.getPrevious();
                    int guard = 0;
                    while (cur != null && guard++ < 30) {
                        if (cur instanceof MethodInsnNode m
                            && m.getOpcode() == Opcodes.INVOKESPECIAL
                            && m.owner.equals("com/vex/baritone/api/Settings$Setting")
                            && m.name.equals("<init>")) {
                            break;
                        }
                        cur = cur.getPrevious();
                    }
                    if (cur != null) {
                        cur = cur.getPrevious();
                        if (cur instanceof MethodInsnNode mi && mi.getOpcode() == Opcodes.INVOKESTATIC
                            && mi.owner.equals("java/lang/Boolean") && mi.name.equals("valueOf")) {
                            cur = cur.getPrevious();
                        }
                        if (cur instanceof FieldInsnNode g && g.getOpcode() == Opcodes.GETSTATIC
                            && g.owner.equals("java/lang/Boolean") && g.name.equals("FALSE")) {
                            g.name = "TRUE";
                            changed = true;
                        } else if (cur instanceof InsnNode in && in.getOpcode() == Opcodes.ICONST_0) {
                            // older builds: iconst_0; Boolean.valueOf(false)
                            mn.instructions.set(cur, new InsnNode(Opcodes.ICONST_1));
                            changed = true;
                        }
                    }
                }
            }
        }
        if (!changed) {
            System.out.println("Settings patch: no replantNetherWart default flipped (lookup failed)");
            return null;
        }
        System.out.println("Settings patch: replantNetherWart default flipped to TRUE");
        return emit(cn);
    }

    private static byte[] patchVersionCommand(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        MethodNode target = null;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("execute") && mn.desc.equals("(Ljava/lang/String;Lcom/vex/baritone/api/command/argument/IArgConsumer;)V")) {
                target = mn;
                break;
            }
        }
        if (target == null) {
            return null;
        }

        InsnList body = new InsnList();
        body.add(new VarInsnNode(Opcodes.ALOAD, 0));
        body.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "com/vex/baritone/config/VersionInfo", "get", "()Ljava/lang/String;", false));
        body.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
            "com/vex/baritone/api/command/Command", "logDirect", "(Ljava/lang/String;)V", false));
        body.add(new InsnNode(Opcodes.RETURN));
        target.instructions.clear();
        target.instructions.add(body);

        return emit(cn);
    }

    private static byte[] patchHelpSort(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        MethodNode target = null;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("execute") && mn.desc.equals("(Ljava/lang/String;Lcom/vex/baritone/api/command/argument/IArgConsumer;)V")) {
                target = mn;
                break;
            }
        }
        if (target == null) {
            return null;
        }

        MethodInsnNode collect = null;
        for (AbstractInsnNode insn : target.instructions) {
            if (insn instanceof MethodInsnNode m && m.getOpcode() == Opcodes.INVOKEINTERFACE
                && m.owner.equals("java/util/stream/Stream") && m.name.equals("collect")
                && m.desc.equals("(Ljava/util/stream/Collector;)Ljava/lang/Object;")) {
                collect = m;
                break;
            }
        }
        if (collect == null) {
            return null;
        }

        InsnList sort = new InsnList();
        sort.add(new InsnNode(Opcodes.DUP));
        sort.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "com/vex/baritone/config/HelpSorter", "sort", "(Ljava/util/List;)V", false));
        target.instructions.insert(collect, sort);

        return emit(cn);
    }

    private static byte[] patchDeathWaypoint(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        MethodNode target = null;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("onPlayerDeath") && mn.desc.equals("()V")) {
                target = mn;
                break;
            }
        }
        if (target == null) {
            return null;
        }

        InsnList gate = new InsnList();
        gate.add(new VarInsnNode(Opcodes.ALOAD, 0));
        gate.add(new FieldInsnNode(Opcodes.GETFIELD,
            "com/vex/baritone/behavior/Behavior", "a",
            "Lcom/vex/baritone/api/utils/IPlayerContext;"));
        gate.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
            "com/vex/baritone/config/DeathLogger", "report",
            "(Lcom/vex/baritone/api/utils/IPlayerContext;)V", false));
        target.instructions.insert(gate);

        return emit(cn);
    }

    private static byte[] patchThisWay(byte[] classBytes) {
        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = cw();
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String mname, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, mname, descriptor, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (name.equals("setGoal") && descriptor.contains("Goal")) {
                            name = "setGoalAndPath";
                        }
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }
                };
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    private static byte[] patchFarm(byte[] classBytes) {
        ClassNode cn = new ClassNode();
        new ClassReader(classBytes).accept(cn, 0);

        MethodNode clinit = null;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("<clinit>")) {
                clinit = mn;
                break;
            }
        }
        if (clinit == null) {
            return null;
        }

        // Find the Arrays.asList construction used for the crop list: an Object
        // array whose stores include the known crop block fields.
        Integer arraySize = null;
        int arrayIndex = -1;
        boolean containsKnownBlock = false;
        for (int i = 0; i < clinit.instructions.size(); i++) {
            AbstractInsnNode insn = clinit.instructions.get(i);
            if (insn instanceof IntInsnNode intNode && intNode.getOpcode() == Opcodes.BIPUSH && intNode.operand == 16) {
                AbstractInsnNode next = insn.getNext();
                if (next instanceof TypeInsnNode typeNode && typeNode.getOpcode() == Opcodes.ANEWARRAY) {
                    // verify a known crop block field appears in the following stores
                    boolean known = false;
                    int end = Math.min(i + 70, clinit.instructions.size());
                    for (int k = i; k < end; k++) {
                        AbstractInsnNode node = clinit.instructions.get(k);
                        if (node instanceof FieldInsnNode fieldNode && fieldNode.owner.equals("net/minecraft/class_2246")
                            && (fieldNode.name.equals("field_10424") || fieldNode.name.equals("field_10211") || fieldNode.name.equals("field_10029"))) {
                            known = true;
                            break;
                        }
                        if (node instanceof MethodInsnNode m && m.name.equals("asList") && m.owner.equals("java/util/Arrays")) {
                            break;
                        }
                    }
                    if (known) {
                        arraySize = 16;
                        arrayIndex = i;
                        containsKnownBlock = true;
                        break;
                    }
                }
            }
        }
        if (!containsKnownBlock || arraySize == null) {
            return null;
        }

        // Find the invokestatic Arrays.asList call after the array stores.
        MethodInsnNode asListNode = null;
        for (int i = arrayIndex; i < clinit.instructions.size(); i++) {
            AbstractInsnNode insn = clinit.instructions.get(i);
            if (insn instanceof MethodInsnNode m && m.name.equals("asList") && m.owner.equals("java/util/Arrays")) {
                asListNode = m;
                break;
            }
        }
        if (asListNode == null) {
            return null;
        }

        // New crop block fields to append: kelp, chorus flower.
        String[] newFields = {"field_9993", "field_10528"};
        int newCount = newFields.length;

        // Bump array size 16 -> 16 + newCount
        AbstractInsnNode sizeNode = clinit.instructions.get(arrayIndex);
        clinit.instructions.set(sizeNode, new IntInsnNode(Opcodes.BIPUSH, 16 + newCount));

        // Insert the store sequences just before the Arrays.asList call.
        InsnList extras = new InsnList();
        int slot = 16;
        for (String field : newFields) {
            extras.add(new InsnNode(Opcodes.DUP));
            extras.add(new IntInsnNode(Opcodes.BIPUSH, slot));
            extras.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/class_2246", field, "Lnet/minecraft/class_2248;"));
            extras.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/class_2248", "method_8389", "()Lnet/minecraft/class_1792;", false));
            extras.add(new InsnNode(Opcodes.AASTORE));
            slot++;
        }
        clinit.instructions.insertBefore(asListNode, extras);

        // --- Nether wart: leave the soul-sand scan/plant flow as-is. The
        // actual wart harvest path uses Harvest.g (NETHER_WART, age >= 3),
        // which is correct. Replanting is driven by replantNetherWart, whose
        // default we flip to true in Settings (see patchSettingsDefault).
        boolean wartSwapped = false;

        // --- Farm scan radius: hard cap at 12 chunks (BIPUSH 10 -> 12) --------
        boolean radiusChanged = false;
        for (MethodNode mn : cn.methods) {
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn instanceof MethodInsnNode m && m.name.equals("scanChunkRadius")) {
                    AbstractInsnNode p1 = insn.getPrevious();
                    AbstractInsnNode p2 = p1 == null ? null : p1.getPrevious();
                    if (p1 instanceof IntInsnNode i1 && i1.getOpcode() == Opcodes.BIPUSH && i1.operand == 10
                        && p2 instanceof IntInsnNode i2 && i2.getOpcode() == Opcodes.BIPUSH && i2.operand == 10) {
                        i1.operand = 12;
                        i2.operand = 12;
                        radiusChanged = true;
                    }
                    break;
                }
            }
        }

        // --- Farm range: clamp the #farm <range> radius to 12 chunks (192) ----
        boolean rangeClamped = false;
        for (MethodNode mn : cn.methods) {
            if (mn.name.equals("farm") && mn.desc.equals("(ILnet/minecraft/class_2338;)V")) {
                for (AbstractInsnNode insn : mn.instructions) {
                    if (insn instanceof FieldInsnNode f && f.getOpcode() == Opcodes.PUTFIELD
                        && f.desc.equals("I")) {
                        mn.instructions.insertBefore(insn, new MethodInsnNode(Opcodes.INVOKESTATIC,
                            "com/vex/baritone/config/FarmLimits", "clampRange", "(I)I", false));
                        rangeClamped = true;
                        break;
                    }
                }
            }
        }

        System.out.println("FarmProcess: crops=" + true + " wart=" + wartSwapped
            + " radius12=" + radiusChanged + " rangeClamp=" + rangeClamped);

        return emit(cn);
    }

    private static byte[] readFully(InputStream in) throws IOException {
        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) != -1) {
            bos.write(buf, 0, n);
        }
        return bos.toByteArray();
    }
}


