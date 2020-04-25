package net.grallarius.sunderednpcs.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.grallarius.sunderednpcs.SunderedNPCs;
import net.grallarius.sunderednpcs.container.NPCContainer;
import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.grallarius.sunderednpcs.network.MessageUpdateNPCDetails;
import net.grallarius.sunderednpcs.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class NPCEditScreen extends ContainerScreen<NPCContainer> {

    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(SunderedNPCs.MODID, "textures/gui/npc_edit.png");
    NPCEntity entity;

    private TextFieldWidget nameTextField;
    protected Button doneButton;
    protected Button cancelButton;
    protected Button dialogButton;

    private float mousePosx;
    private float mousePosy;


    public NPCEditScreen(NPCContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.passEvents = false;
        this.entity = this.getContainer().getNPC();
        this.xSize = 276;
    }

    protected void init() {
        super.init();

        this.minecraft.keyboardListener.enableRepeatEvents(true);

        this.doneButton = this.addButton(new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 30, 150, 20, I18n.format("gui.done"), (p_214187_1_) -> {
            this.saveAndClose();
        }));

        this.cancelButton = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 120 + 30, 150, 20, I18n.format("gui.cancel"), (p_214186_1_) -> {
            this.onClose();
        }));

        this.dialogButton = this.addButton(new Button(this.width / 2 + 20, this.height / 4 + 30, 50, 20, I18n.format("gui.dialog"), (p_214186_1_) -> {
            this.openDialogs(this.entity);
        }));

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.nameTextField = new TextFieldWidget(this.font, i + 180, j + 24, 103, 12, I18n.format(""));
        this.nameTextField.setCanLoseFocus(true);
        this.nameTextField.changeFocus(true);
        this.nameTextField.setTextColor(-1);
        this.nameTextField.setDisabledTextColour(-1);
        this.nameTextField.setEnableBackgroundDrawing(false);
        this.nameTextField.setMaxStringLength(20);
        this.nameTextField.func_212954_a(this::func_214075_a);
        this.children.add(this.nameTextField);
        //this.container.addListener(this);
        this.func_212928_a(this.nameTextField);
        nameTextField.setText(entity.getNpc_name());
        this.nameTextField.setFocused2(false);

    }

    public void saveAndClose(){
        if (!this.nameTextField.getText().equals("")) {
            PacketHandler.sendToServer(new MessageUpdateNPCDetails(this.entity.getEntityId(), this.nameTextField.getText()));
            //below only reaches client
            //this.entity.setNpc_name(this.nameTextField.getText());
        }
        onClose();
    }

    private void openDialogs(NPCEntity entity){
        onClose();
        Minecraft.getInstance().displayGuiScreen(new NPCDialogEditScreen(entity));

    }

    private void close() {
        this.minecraft.displayGuiScreen((Screen)null);
    }

    public void onClose() {
        this.close();
    }

    public void removed() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);

    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 256) {
            this.minecraft.player.closeScreen();
        }

        return !this.nameTextField.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) && !this.nameTextField.func_212955_f() ? super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) : true;
    }

    private void func_214075_a(String p_214075_1_) {
        if (!p_214075_1_.isEmpty()) {
            String s = p_214075_1_;
            Slot slot = this.container.getSlot(0);
            if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && p_214075_1_.equals(slot.getStack().getDisplayName().getString())) {
                s = "";
            }

            //this.container.updateItemName(s);
            //this.minecraft.player.connection.sendPacket(new CRenameItemPacket(s));
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        this.mousePosx = (float)mouseX;
        this.mousePosy = (float)mouseY;

        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        this.nameTextField.render(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format("npc.edit.title");
        drawString(Minecraft.getInstance().fontRenderer, name, 4, 6, 0x404040);

        name = I18n.format("npc.edit.name");
        drawString(Minecraft.getInstance().fontRenderer, name, 180, 6, 0x404040);


        //String invName = playerInv.getDisplayName().getUnformattedComponentText();
        //drawString(Minecraft.getInstance().fontRenderer, invName, 2, 6, 0x404040);


    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BG_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(i, j, this.blitOffset, 0.0F, 0.0F, this.xSize, this.ySize, 256, 512);

        InventoryScreen.drawEntityOnScreen(i + 110, j + 60, 25, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosy, this.entity);
        //InventoryScreen.drawEntityOnScreen(positionX + 51, positionY + 60, 30, (float)(positionX + 51) - this.mousePosx, (float)(positionY + 42) - this.mousePosy, this.theWolf);

    }
}
