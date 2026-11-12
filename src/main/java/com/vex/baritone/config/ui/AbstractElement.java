package com.vex.baritone.config.ui;

import java.util.function.Supplier;
import net.minecraft.class_332;

/**
 * Ported from ClickCrystals (AbstractElement.java). The RENDER_BUTTON preset
 * is adapted to our vanilla-only RenderKit.
 */
public class AbstractElement extends GuiElement {
    public static final java.util.function.Function<Supplier<String>, RenderAction<AbstractElement>> RENDER_BUTTON =
        stringSupplier -> (context, mouseX, mouseY, button) -> {
            RenderKit.fillRoundRect(context, button.x, button.y, button.width, button.height, 3,
                button.isHovered(mouseX, mouseY) ? 1090519039 : 0);
            RenderKit.drawRoundRect(context, button.x, button.y, button.width, button.height, 3, -1);
            int textY = button.y + (int) ((button.height - 9.0 * 0.9) / 2);
            RenderKit.drawCenteredText(context, stringSupplier.get(), button.x + button.width / 2, textY, 0.9F, false);
        };

    private final RenderAction<AbstractElement> onRender;
    private final PressAction<AbstractElement> onPress;

    public AbstractElement(int x, int y, int width, int height,
                           RenderAction<AbstractElement> onRender,
                           PressAction<AbstractElement> pressAction) {
        super(x, y, width, height);
        this.onRender = onRender;
        this.onPress = pressAction;
    }

    @Override
    public void onRender(class_332 context, int mouseX, int mouseY) {
        this.onRender.onRender(context, mouseX, mouseY, this);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovered((int) mouseX, (int) mouseY)) {
            this.onPress.onPress(this);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        private int x;
        private int y;
        private int width;
        private int height;
        private RenderAction<AbstractElement> onRender = (context, mouseX, mouseY, button) -> {};
        private PressAction<AbstractElement> onPress = button -> {};

        public Builder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder dimensions(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder onRender(RenderAction<AbstractElement> onRender) {
            this.onRender = onRender;
            return this;
        }

        public Builder onPress(PressAction<AbstractElement> onPress) {
            this.onPress = onPress;
            return this;
        }

        public AbstractElement build() {
            return new AbstractElement(this.x, this.y, this.width, this.height, this.onRender, this.onPress);
        }
    }
}
