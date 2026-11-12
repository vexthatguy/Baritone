package io.github.itzispyder.clickcrystals.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (GuiElement.java), stripped of the module-border
 * dependency.
 */
public abstract class GuiElement implements Positionable {
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean rendering;
    public boolean draggable;
    public boolean renderDependentOnParent;
    public boolean isContainer;
    private GuiElement parent;
    private final List<GuiElement> children;

    public GuiElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rendering = true;
        this.draggable = false;
        this.renderDependentOnParent = false;
        this.parent = null;
        this.children = new ArrayList<>();
        this.isContainer = false;
    }

    public void render(class_332 context, int mouseX, int mouseY) {
        boolean bl = this.canRender();
        if (bl) {
            this.onRender(context, mouseX, mouseY);
        }
        for (GuiElement child : this.children) {
            child.render(context, mouseX, mouseY);
        }
        if (bl) {
            this.postRender(context, mouseX, mouseY);
        }
    }

    public abstract void onRender(class_332 context, int mouseX, int mouseY);

    public void onClick(double mouseX, double mouseY, int button) {
    }

    public void onRelease(double mouseX, double mouseY, int button) {
    }

    public void postRender(class_332 context, int mouseX, int mouseY) {
    }

    public void onTick() {
        for (GuiElement child : this.children) {
            child.onTick();
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered((int) mouseX, (int) mouseY)) {
            this.onClick(mouseX, mouseY, button);
        }
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            GuiElement child = this.getChildren().get(i);
            child.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (this.isHovered((int) mouseX, (int) mouseY)) {
            this.onRelease(mouseX, mouseY, button);
        }
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            GuiElement child = this.getChildren().get(i);
            child.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return this.rendering && mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }

    public List<GuiElement> getChildren() {
        return this.children;
    }

    public void clearChildren() {
        this.children.clear();
    }

    public void forEachChild(Consumer<GuiElement> action) {
        this.children.forEach(action);
    }

    public void addChild(GuiElement child) {
        if (child != null && child != this) {
            child.parent = this;
            this.children.add(child);
        }
    }

    public void removeChild(GuiElement child) {
        this.children.remove(child);
    }

    public GuiElement getParent() {
        return this.parent;
    }

    public void move(double deltaX, double deltaY) {
        this.x = (int) (this.x + deltaX);
        this.y = (int) (this.y + deltaY);
        this.children.forEach(child -> child.move(deltaX, deltaY));
    }

    public void moveTo(int x, int y) {
        int delX = x - this.x;
        int delY = y - this.y;
        this.x = x;
        this.y = y;
        this.children.forEach(child -> child.move(delX, delY));
    }

    public void centerIn(int frameWidth, int frameHeight) {
        this.moveTo(frameWidth / 2 - this.width / 2, frameHeight / 2 - this.height / 2);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isRendering() {
        return this.rendering;
    }

    public void setRendering(boolean rendering) {
        this.rendering = rendering;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public void setParent(GuiElement parent) {
        this.parent = parent;
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public boolean isRenderDependentOnParent() {
        return this.renderDependentOnParent;
    }

    public void setRenderDependentOnParent(boolean renderDependentOnParent) {
        this.renderDependentOnParent = renderDependentOnParent;
    }

    public boolean isContainer() {
        return this.isContainer;
    }

    public void setContainer(boolean container) {
        this.isContainer = container;
    }

    public boolean canRender() {
        if (this.rendering) {
            return this.renderDependentOnParent && this.hasParent() ? this.parent.canRender() : true;
        }
        return false;
    }

    public GuiElement getHoveredElement(double mouseX, double mouseY) {
        for (int i = this.children.size() - 1; i >= 0; i--) {
            GuiElement child = this.children.get(i);
            if (child.isHovered((int) mouseX, (int) mouseY)) {
                return child;
            }
        }
        return null;
    }
}
