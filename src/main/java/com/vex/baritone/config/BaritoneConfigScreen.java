package com.vex.baritone.config;

import com.vex.baritone.api.BaritoneAPI;
import com.vex.baritone.api.Settings;
import com.vex.baritone.api.utils.SettingsUtil;
import com.vex.baritone.config.ui.ConfigBase;
import com.vex.baritone.config.ui.NavItem;
import com.vex.baritone.config.ui.SettingRow;
import net.minecraft.class_437;

/**
 * The config screen ModMenu opens. Six grouped sections expose the live
 * Baritone boolean settings most players actually touch, all through the
 * shared minimal shell.
 */
public class BaritoneConfigScreen extends ConfigBase {

    private static final String[] TABS = {
        "Movement", "Mining", "Building", "Pathing", "Safety", "Interface"
    };

    private static final String[] SUBTITLES = {
        "How Baritone moves and jumps",
        "What it breaks, and with which tool",
        "How it places blocks and builds",
        "Pathfinding, inventory and camera",
        "Hazards, liquids and world rules",
        "Rendering, chat and notifications"
    };

    private final NavItem[] tabs = new NavItem[TABS.length];
    private final class_437 parent;
    private int activeTab = 0;

    public BaritoneConfigScreen() {
        this(null);
    }

    public BaritoneConfigScreen(class_437 parent) {
        super("Baritone Configuration");
        this.parent = parent;
        for (int i = 0; i < TABS.length; i++) {
            final int index = i;
            NavItem item = new NavItem(TABS[i], () -> select(index));
            this.tabs[i] = item;
            addNav(item);
        }
        select(0);
    }

    @Override
    public void method_25419() {
        if (this.parent != null) {
            this.field_22787.method_1507(this.parent);
        } else {
            super.method_25419();
        }
    }

    private void select(int index) {
        if (this.activeTab == index && !this.rows.isEmpty()) {
            return;
        }
        this.activeTab = index;
        for (int i = 0; i < this.tabs.length; i++) {
            this.tabs[i].setSelected(i == index);
        }
        rebuild();
    }

    private void rebuild() {
        clearRows();
        resetScroll();

        if (VisibilityConfig.isDisabled() || BaritoneAPI.getSettings() == null) {
            setNotice(true);
            setSection("Disabled", "The mod is currently switched off", "");
            refreshRows();
            return;
        }

        setNotice(false);
        Settings s = BaritoneAPI.getSettings();

        switch (this.activeTab) {
            case 0 -> {
                addToggle("Sprint", "Sprint while pathing", s.allowSprint);
                addToggle("Parkour", "Allow parkour jumps", s.allowParkour);
                addToggle("Parkour place", "Place blocks during parkour", s.allowParkourPlace);
                addToggle("Parkour ascend", "Jump up while parkouring", s.allowParkourAscend);
                addToggle("Diagonal ascend", "Move up diagonally", s.allowDiagonalAscend);
                addToggle("Diagonal descend", "Move down diagonally", s.allowDiagonalDescend);
                addToggle("Overshoot diagonal", "Overshoot during diagonal descent", s.allowOvershootDiagonalDescend);
                addToggle("Downward", "Drop straight down", s.allowDownward);
                addToggle("Sprint ascends", "Sprint while climbing", s.sprintAscends);
                addToggle("Sprint in water", "Sprint while swimming", s.sprintInWater);
                addToggle("Vines", "Climb vines while pathing", s.allowVines);
                addToggle("Bottom slabs", "Walk on bottom slabs", s.allowWalkOnBottomSlab);
                addToggle("Overshoot traverse", "Overshoot while traversing", s.overshootTraverse);
                addToggle("Auto step", "Step up single blocks", s.assumeStep);
                addToggle("Safe walk", "Assume edge safety is handled", s.assumeSafeWalk);
            }
            case 1 -> {
                addToggle("Break blocks", "Break blocks while pathing", s.allowBreak);
                addToggle("Auto tool", "Auto-select the best tool", s.autoTool);
                addToggle("Silk touch", "Prefer silk touch when mining", s.preferSilkTouch);
                addToggle("Walk while breaking", "Keep moving while breaking", s.walkWhileBreaking);
                addToggle("Pause for falling blocks", "Pause mining under falling blocks", s.pauseMiningForFallingBlocks);
                addToggle("Legit mining", "Only mine visible blocks", s.legitMine);
                addToggle("Legit diagonals", "Include diagonal blocks in legit mining", s.legitMineIncludeDiagonals);
                addToggle("Exposed ores only", "Skip ores that are fully buried", s.allowOnlyExposedOres);
                addToggle("Scan dropped items", "Consider dropped items when mining", s.mineScanDroppedItems);
                addToggle("Replant crops", "Replant harvested crops", s.replantCrops);
                addToggle("Replant nether wart", "Replant harvested nether wart", s.replantNetherWart);
                addToggle("Sword to mine", "Use swords on mineable blocks", s.useSwordToMine);
                addToggle("Item saver", "Save tools from unnecessary damage", s.itemSaver);
                addToggle("Break from above", "Only break blocks from above", s.breakFromAbove);
                addToggle("Force internal mining", "Use internal block breaking", s.forceInternalMining);
            }
            case 2 -> {
                addToggle("Place blocks", "Place blocks while pathing", s.allowPlace);
                addToggle("Place in source fluid", "Allow placing into fluid sources", s.allowPlaceInFluidsSource);
                addToggle("Place in flowing fluid", "Allow placing into flowing fluids", s.allowPlaceInFluidsFlow);
                addToggle("Ignore existing", "Ignore existing blocks when building", s.buildIgnoreExisting);
                addToggle("Ignore direction", "Ignore build direction", s.buildIgnoreDirection);
                addToggle("Build in layers", "Build layer by layer", s.buildInLayers);
                addToggle("Skip failed layers", "Move on from failed layers", s.skipFailedLayers);
                addToggle("Only selection", "Build only inside the selection", s.buildOnlySelection);
                addToggle("Repeat sneaky", "Sneak while placing repeatedly", s.buildRepeatSneaky);
                addToggle("Map art mode", "Row-by-row map art behaviour", s.mapArtMode);
                addToggle("Backfill", "Fill in blocks behind you", s.backfill);
                addToggle("Keep falling blocks", "Avoid updating falling blocks", s.avoidUpdatingFallingBlocks);
            }
            case 3 -> {
                addToggle("Manage inventory", "Move items into the hotbar", s.allowInventory);
                addToggle("Stationary moves", "Only move items while standing still", s.inventoryMoveOnlyIfStationary);
                addToggle("Anti-cheat mode", "Behave conservatively for servers", s.antiCheatCompatibility);
                addToggle("Assume auto tool", "Assume another mod swaps tools", s.assumeExternalAutoTool);
                addToggle("Walk on water", "Treat water as walkable", s.assumeWalkOnWater);
                addToggle("Walk on lava", "Treat lava as walkable", s.assumeWalkOnLava);
                addToggle("Cached only", "Path only through cached chunks", s.pathThroughCachedOnly);
                addToggle("Simplify unloaded Y", "Simplify Y for unloaded chunks", s.simplifyUnloadedYCoord);
                addToggle("Cutoff at boundary", "Stop paths at load boundaries", s.cutoffAtLoadBoundary);
                addToggle("Splice paths", "Splice new paths into the old one", s.splicePath);
                addToggle("Slow path", "Prefer slower, safer routes", s.slowPath);
                addToggle("Trim distance", "Trim path start when far away", s.distanceTrim);
                addToggle("Free look", "Look around freely while pathing", s.freeLook);
                addToggle("Block free look", "Block free look while pathing", s.blockFreeLook);
                addToggle("Smooth look", "Smooth camera rotation", s.smoothLook);
            }
            case 4 -> {
                addToggle("Avoidance", "Avoid mobs and hazards", s.avoidance);
                addToggle("Strict liquid check", "Stricter liquid safety checks", s.strictLiquidCheck);
                addToggle("Water bucket fall", "Water bucket clutch on falls", s.allowWaterBucketFall);
                addToggle("Potion effects", "Account for potion effects", s.considerPotionEffects);
                addToggle("Enter portals", "Use portals when required", s.enterPortal);
                addToggle("Cancel on invalid goal", "Cancel if the goal becomes invalid", s.cancelOnGoalInvalidation);
                addToggle("Disconnect on arrival", "Disconnect when the goal is reached", s.disconnectOnArrival);
                addToggle("Open containers on arrival", "Right-click containers on arrival", s.rightClickContainerOnArrival);
                addToggle("Blacklist closest", "Blacklist the nearest block on failure", s.blacklistClosestOnFailure);
                addToggle("Jump at build limit", "Allow jumping at the build limit", s.allowJumpAtBuildLimit);
                addToggle("Jump at 256", "Allow jumping at Y 256", s.allowJumpAt256);
                addToggle("Repropagation", "Repropagate only on real improvement", s.minimumImprovementRepropagation);
            }
            default -> {
                addToggle("Render path", "Show the active path", s.renderPath);
                addToggle("Path as line", "Draw the path as a line", s.renderPathAsLine);
                addToggle("Fade path", "Fade the path over distance", s.fadePath);
                addToggle("Render goal", "Show the current goal", s.renderGoal);
                addToggle("Animated goal", "Animate the goal marker", s.renderGoalAnimated);
                addToggle("Goal through walls", "Draw the goal ignoring depth", s.renderGoalIgnoreDepth);
                addToggle("Selections", "Render selections", s.renderSelection);
                addToggle("Selection boxes", "Render selection boxes", s.renderSelectionBoxes);
                addToggle("Cached chunks", "Render cached chunks", s.renderCachedChunks);
                addToggle("Chunk caching", "Cache explored chunks to disk", s.chunkCaching);
                addToggle("Toast messages", "Show Baritone events as toasts", s.logAsToast);
                addToggle("Desktop notifications", "Send desktop notifications", s.desktopNotifications);
                addToggle("Notify on arrival", "Notify when a path completes", s.notificationOnPathComplete);
                addToggle("Notify on mine fail", "Notify when mining fails", s.notificationOnMineFail);
                addToggle("Chat debug", "Print verbose debug output", s.chatDebug);
                addToggle("Short prefix", "Use the short chat prefix", s.shortBaritonePrefix);
                addToggle("Censor coordinates", "Hide coordinates in chat", s.censorCoordinates);
            }
        }

        setSection(TABS[this.activeTab], SUBTITLES[this.activeTab], this.rows.size() + " options");
        refreshRows();
        animateRowsIn();
    }

    private void addToggle(String label, String description, Settings.Setting<Boolean> setting) {
        addRow(new SettingRow(label, description, setting, this::save));
    }

    private void save() {
        try {
            SettingsUtil.save(BaritoneAPI.getSettings());
        } catch (Throwable ignored) {
        }
    }
}
