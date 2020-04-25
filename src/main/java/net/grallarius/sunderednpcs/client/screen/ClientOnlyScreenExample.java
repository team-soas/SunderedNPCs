package net.grallarius.sunderednpcs.client.screen;

import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class ClientOnlyScreenExample extends Screen {

    private NPCEntity entity;
    protected TextFieldWidget nameTextField;
    protected Button doneButton;
    protected Button cancelButton;
    

    public ClientOnlyScreenExample(NPCEntity entity) {
        super(new TranslationTextComponent("npc.edit.title"));
        this.entity = entity;
    }

    protected void init() {

        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.doneButton = this.addButton(new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done"), (p_214187_1_) -> {
            this.saveAndClose();
        }));
        this.cancelButton = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel"), (p_214186_1_) -> {
            this.onClose();
        }));

        this.nameTextField = new TextFieldWidget(this.font, this.width / 2 - 150, 50, 300, 20, I18n.format("advMode.command"));
        this.nameTextField.setMaxStringLength(30);
        //this.nameTextField.setTextFormatter(this::formatCommand);
        //this.nameTextField.func_212954_a(this::func_214185_b);
        this.children.add(this.nameTextField);

        this.func_212928_a(this.nameTextField);
        this.nameTextField.setFocused2(true);
        

    }

    public void saveAndClose(){
        if (this.nameTextField.getText().length() > 0) {
            this.entity.setNpc_name(this.nameTextField.getText());
        }
        onClose();
    }

    public void removed() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        
    }

    public void tick() {
        if (this.entity == null) {
            this.close();
        }
    }

    private void close() {
        this.minecraft.displayGuiScreen((Screen)null);
    }

    public void onClose() {
        this.close();
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
            return true;
        }  else {
            return true;
        }
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, I18n.format("npc.edit.title"), this.width / 2, 20, 16777215);
        this.drawString(this.font, I18n.format("npc.edit.name"), this.width / 2 - 150, 40, 10526880);
        this.nameTextField.render(p_render_1_, p_render_2_, p_render_3_);
        int i = 75;


        super.render(p_render_1_, p_render_2_, p_render_3_);

/*        if (this.suggestionList != null) {
            this.suggestionList.render(p_render_1_, p_render_2_);
        } else {
            i = 0;

            for(String s : this.field_209111_t) {
                fill(this.field_209112_u - 1, 72 + 12 * i, this.field_209112_u + this.field_209113_v + 1, 84 + 12 * i, Integer.MIN_VALUE);
                this.font.drawStringWithShadow(s, (float)this.field_209112_u, (float)(74 + 12 * i), -1);
                ++i;
            }
        }*/
    }
}
