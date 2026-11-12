package com.vex.baritone.config;

import com.vex.baritone.api.IBaritone;
import com.vex.baritone.api.IBaritoneProvider;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.class_310;
import net.minecraft.class_634;
import net.minecraft.class_746;

/**
 * Gate provider used when Baritone is switched off.
 *
 * <p>It never creates new baritones, but it also never hands back {@code null}
 * for the things Baritone's own mixins dereference unconditionally. Baritone's
 * mixins run on every tick and call
 * {@code BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior()}
 * without a null check, so a null here crashed the game the moment the master
 * switch was flipped. Every getter therefore delegates to the real provider
 * (looked up reflectively, because the build renames the original
 * {@code getProvider} to {@code getProviderReal}).</p>
 *
 * <p>Disabling still does what it should: commands are blocked, and
 * {@link VisibilityConfig#setEnabled(boolean)} cancels every running process
 * before flipping this gate.</p>
 */
public final class NoopBaritoneProvider implements IBaritoneProvider {
    public static final NoopBaritoneProvider INSTANCE = new NoopBaritoneProvider();

    /** Cached so a lookup failure can never produce a null primary baritone. */
    private static IBaritone primary;

    private NoopBaritoneProvider() {
    }

    @Override
    public IBaritone getPrimaryBaritone() {
        IBaritone cached = primary;
        if (cached != null) {
            return cached;
        }
        IBaritoneProvider real = realProvider();
        if (real != null) {
            try {
                cached = real.getPrimaryBaritone();
            } catch (Throwable ignored) {
            }
        }
        if (cached != null) {
            primary = cached;
        }
        return cached;
    }

    @Override
    public List<IBaritone> getAllBaritones() {
        IBaritoneProvider real = realProvider();
        if (real != null) {
            try {
                List<IBaritone> all = real.getAllBaritones();
                if (all != null) {
                    return all;
                }
            } catch (Throwable ignored) {
            }
        }
        IBaritone one = getPrimaryBaritone();
        return one == null ? List.of() : List.of(one);
    }

    @Override
    public IBaritone getBaritoneForPlayer(class_746 player) {
        IBaritoneProvider real = realProvider();
        if (real != null) {
            try {
                IBaritone found = real.getBaritoneForPlayer(player);
                if (found != null) {
                    return found;
                }
            } catch (Throwable ignored) {
            }
        }
        return getPrimaryBaritone();
    }

    @Override
    public IBaritone getBaritoneForMinecraft(class_310 minecraft) {
        IBaritoneProvider real = realProvider();
        if (real != null) {
            try {
                IBaritone found = real.getBaritoneForMinecraft(minecraft);
                if (found != null) {
                    return found;
                }
            } catch (Throwable ignored) {
            }
        }
        return getPrimaryBaritone();
    }

    @Override
    public IBaritone getBaritoneForConnection(class_634 connection) {
        IBaritoneProvider real = realProvider();
        if (real != null) {
            try {
                IBaritone found = real.getBaritoneForConnection(connection);
                if (found != null) {
                    return found;
                }
            } catch (Throwable ignored) {
            }
        }
        return getPrimaryBaritone();
    }

    /** Never creates a bot while disabled. */
    @Override
    public IBaritone createBaritone(class_310 minecraft) {
        return null;
    }

    @Override
    public boolean destroyBaritone(IBaritone baritone) {
        return false;
    }

    @Override
    public com.vex.baritone.api.cache.IWorldScanner getWorldScanner() {
        IBaritoneProvider real = realProvider();
        if (real != null) {
            try {
                return real.getWorldScanner();
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    @Override
    public com.vex.baritone.api.command.ICommandSystem getCommandSystem() {
        IBaritoneProvider real = realProvider();
        if (real != null) {
            try {
                return real.getCommandSystem();
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    @Override
    public com.vex.baritone.api.schematic.ISchematicSystem getSchematicSystem() {
        IBaritoneProvider real = realProvider();
        if (real != null) {
            try {
                return real.getSchematicSystem();
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    // ------------------------------------------------------------- internals

    /**
     * The real provider, read from the {@code BaritoneAPI} singleton field.
     * Reflection is used because the build renames {@code getProvider} to
     * {@code getProviderReal} at runtime.
     */
    private static IBaritoneProvider realProvider() {
        try {
            Field field = com.vex.baritone.api.BaritoneAPI.class.getDeclaredField("provider");
            field.setAccessible(true);
            Object value = field.get(null);
            if (value instanceof IBaritoneProvider provider && provider != INSTANCE) {
                return provider;
            }
        } catch (Throwable ignored) {
        }
        try {
            Method method = com.vex.baritone.api.BaritoneAPI.class.getDeclaredMethod("getProviderReal");
            method.setAccessible(true);
            Object value = method.invoke(null);
            if (value instanceof IBaritoneProvider provider && provider != INSTANCE) {
                return provider;
            }
        } catch (Throwable ignored) {
        }
        return null;
    }
}
