package net.grallarius.sunderednpcs.client.screen;


import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.grallarius.sunderednpcs.network.MessageNPCCommand;
import net.grallarius.sunderednpcs.network.PacketHandler;
import net.grallarius.sunderednpcs.util.NPCDialogItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class NPCChatScreen extends Screen {

    private NPCEntity entity;
    protected NPCDialogItem displayedDialogItem;
    protected Button cancelButton;
    protected Button text1Button;
    protected Button text2Button;
    protected Button text3Button;
    protected Button text4Button;



    public NPCChatScreen(NPCEntity entity, NPCDialogItem dialogItem) {
        super(new TranslationTextComponent("npc.chat.title"));
        this.entity = entity;
        this.displayedDialogItem = dialogItem;
    }

    protected void init() {

        this.minecraft.keyboardListener.enableRepeatEvents(true);

        //relative to this.height/4
        int startheight = 120;
        int spacing = 25;

        this.cancelButton = this.addButton(new Button(this.width / 2 - 120, this.height / 4 + startheight, 250, 20, I18n.format("npc.chat.leave"), (p_214186_1_) -> {
            this.onClose();
        }));

        try{

            for (NPCDialogItem item : this.entity.getDialog().dialogItems){
                if (item.getPrevItem() == this.displayedDialogItem.getItemID()){
                    if (text1Button == null) {
                        this.text1Button = this.addButton(new Button(this.width / 2 - 120, this.height / 4 + startheight - spacing, 250, 20, item.getPlayerText(), (p_214187_1_) -> {
                            this.chatItemClicked(item);
                        }));
                    } else if (text2Button == null) {
                        this.text2Button = this.addButton(new Button(this.width / 2 - 120, this.height / 4 + startheight - (spacing*2), 250, 20,item.getPlayerText(), (p_214187_1_) -> {
                            this.chatItemClicked(item);
                        }));
                    } else if (text3Button == null) {
                        this.text3Button = this.addButton(new Button(this.width / 2 - 120, this.height / 4 + startheight - (spacing*3), 250, 20,item.getPlayerText(), (p_214187_1_) -> {
                            this.chatItemClicked(item);
                        }));
                    } else if (text4Button == null) {
                        this.text4Button = this.addButton(new Button(this.width / 2 - 120, this.height / 4 + startheight - (spacing*4), 250, 20,item.getPlayerText(), (p_214187_1_) -> {
                            this.chatItemClicked(item);
                        }));
                    }
                }

            }


        } catch (NullPointerException e){

        }





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

    public void chatItemClicked(NPCDialogItem item){
        this.onClose();

        //this item is a chat item and leads to more dialog
        if (item.getType() == NPCDialogItem.DialogItemType.CHAT) {
            Minecraft.getInstance().displayGuiScreen(new NPCChatScreen(this.entity, item));
        //this item returns chat to the start
        } else if (item.getType() == NPCDialogItem.DialogItemType.CHATRET){
            Minecraft.getInstance().displayGuiScreen(new NPCChatScreen(this.entity, this.entity.getDialog().dialogItems[0]));
        } else if (item.getType() == NPCDialogItem.DialogItemType.COMMAND){

            PacketHandler.sendToServer(new MessageNPCCommand(this.entity.getEntityId(), item.getNpcResponse()));

            //client side only:
/*            try {
                //Minecraft.getInstance().getConnection().sendPacket((new CChatMessagePacket(item.getNpcResponse())));
            } catch (NullPointerException e){
                e.printStackTrace();
            }*/

        }

        //TODO add shop open functions etc here
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

        this.drawCenteredString(this.font, displayedDialogItem.getNpcResponse(), this.width / 2, 20, 16777215);

        //this.drawCenteredString(this.font, I18n.format("npc.edit.title"), this.width / 2, 60, 16777215);

        super.render(p_render_1_, p_render_2_, p_render_3_);

    }
}
