package net.grallarius.sunderednpcs.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.SharedConstants;

public class NumberFieldWidget extends TextFieldWidget {

    private boolean isEnabled = true;

    public NumberFieldWidget(FontRenderer fontIn, int p_i51137_2_, int p_i51137_3_, int p_i51137_4_, int p_i51137_5_, String msg) {
        super(fontIn, p_i51137_2_, p_i51137_3_, p_i51137_4_, p_i51137_5_, msg);
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        if (!this.func_212955_f()) {
            return false;
        } else if (SharedConstants.isAllowedCharacter(p_charTyped_1_)) {
            if (this.isEnabled) {
                Character c = p_charTyped_1_;
                if (Character.isDigit(c))
                    this.writeText(c.toString());
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * Sets whether this text box is enabled. Disabled text boxes cannot be typed in.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }
}
