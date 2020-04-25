package net.grallarius.sunderednpcs.client.screen;

import net.grallarius.sunderednpcs.entity.NPCEntity;
import net.grallarius.sunderednpcs.network.MessageUpdateNPCDialog;
import net.grallarius.sunderednpcs.network.PacketHandler;
import net.grallarius.sunderednpcs.util.NPCDialogItem;
import net.grallarius.sunderednpcs.util.NumberFieldWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;

public class NPCDialogEditScreen extends Screen {
    private NPCEntity entity;
    protected Button doneButton;
    protected Button cancelButton;

    private int mousePosx;
    private int mousePosy;

    //column start values (relative to width/2)
    private int col0 = -190; //index
    private int col1 = -180; //20 long text
    private int col2 = -150; //40 long text
    private int col3 = -100; //40 long text
    private int col4 = -50; //90 long text
    private int col5 = +50; //90 long text
    private int col6 = +150; //40 long text

    //row start values
    private int row0 = 50;
    private int rowGap = 25;

    protected ArrayList<TextFieldWidget> allTextFields;

    protected NumberFieldWidget prevID;
    protected TextFieldWidget prereqFlag;
    protected TextFieldWidget type;
    protected TextFieldWidget playerText;
    protected TextFieldWidget npcResponse;
    protected TextFieldWidget flagGiven;

    protected NumberFieldWidget prevID1;
    protected TextFieldWidget prereqFlag1;
    protected TextFieldWidget type1;
    protected TextFieldWidget playerText1;
    protected TextFieldWidget npcResponse1;
    protected TextFieldWidget flagGiven1;

    protected NumberFieldWidget prevID2;
    protected TextFieldWidget prereqFlag2;
    protected TextFieldWidget type2;
    protected TextFieldWidget playerText2;
    protected TextFieldWidget npcResponse2;
    protected TextFieldWidget flagGiven2;

    protected NumberFieldWidget prevID3;
    protected TextFieldWidget prereqFlag3;
    protected TextFieldWidget type3;
    protected TextFieldWidget playerText3;
    protected TextFieldWidget npcResponse3;
    protected TextFieldWidget flagGiven3;

    protected NumberFieldWidget prevID4;
    protected TextFieldWidget prereqFlag4;
    protected TextFieldWidget type4;
    protected TextFieldWidget playerText4;
    protected TextFieldWidget npcResponse4;
    protected TextFieldWidget flagGiven4;

    protected NumberFieldWidget prevID5;
    protected TextFieldWidget prereqFlag5;
    protected TextFieldWidget type5;
    protected TextFieldWidget playerText5;
    protected TextFieldWidget npcResponse5;
    protected TextFieldWidget flagGiven5;

    public NPCDialogEditScreen(NPCEntity entity) {
        super(new TranslationTextComponent("npc.dialog.title"));
        this.entity = entity;
        allTextFields = new ArrayList<>();
    }


    protected void init() {

        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.doneButton = this.addButton(new Button(this.width / 2 - 4 - 150, this.height / 4 + 140, 150, 20, I18n.format("gui.done"), (p_214187_1_) -> {
            this.saveAndClose();
        }));
        this.cancelButton = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 140, 150, 20, I18n.format("gui.cancel"), (p_214186_1_) -> {
            this.onClose();
        }));

        //set location and length of all text fields, add them to list
        initTextFields();

        //add all text fields from list as children
        this.children.addAll(this.allTextFields);

    }

    public void saveAndClose(){
        //first pack the dialog items into a single connected String with # as separator
        String line0 = NPCDialogItem.toLineFromStrings("1", "0", "",
                this.type.getText(), "", this.npcResponse.getText(), this.flagGiven.getText());

        String line1 = NPCDialogItem.toLineFromStrings("2", this.prevID1.getText(), this.prereqFlag1.getText(),
                this.type1.getText(), this.playerText1.getText(), this.npcResponse1.getText(), this.flagGiven1.getText());
        String line2 = NPCDialogItem.toLineFromStrings("3", this.prevID2.getText(), this.prereqFlag2.getText(),
                this.type2.getText(), this.playerText2.getText(), this.npcResponse2.getText(), this.flagGiven2.getText());
        String line3 = NPCDialogItem.toLineFromStrings("4", this.prevID3.getText(), this.prereqFlag3.getText(),
                this.type3.getText(), this.playerText3.getText(), this.npcResponse3.getText(), this.flagGiven3.getText());
        String line4 = NPCDialogItem.toLineFromStrings("5", this.prevID4.getText(), this.prereqFlag4.getText(),
                this.type4.getText(), this.playerText4.getText(), this.npcResponse4.getText(), this.flagGiven4.getText());
        String line5 = NPCDialogItem.toLineFromStrings("6", this.prevID5.getText(), this.prereqFlag5.getText(),
                this.type5.getText(), this.playerText5.getText(), this.npcResponse5.getText(), this.flagGiven5.getText());

        PacketHandler.sendToServer(new MessageUpdateNPCDialog(this.entity.getEntityId(), line0, line1, line2, line3, line4, line5));

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

        this.mousePosx = p_render_1_;
        this.mousePosy = p_render_2_;

        //draw titles for gui and text fields
        this.drawCenteredString(this.font, I18n.format("npc.dialog.title"), this.width / 2, 20, 16777215);
        this.drawString(this.font, I18n.format("npc.dialog.prev"), this.width / 2 +col1, 35, 16777215);
        this.drawString(this.font, I18n.format("npc.dialog.prereq"), this.width / 2 +col2, 35, 16777215);
        this.drawString(this.font, I18n.format("npc.dialog.type"), this.width / 2 +col3, 35, 16777215);
        this.drawString(this.font, I18n.format("npc.dialog.playertext"), this.width / 2 +col4, 35, 16777215);
        this.drawString(this.font, I18n.format("npc.dialog.npcresponse"), this.width / 2 +col5, 35, 16777215);
        this.drawString(this.font, I18n.format("npc.dialog.flaggiven"), this.width / 2 +col6, 35, 16777215);

        //draw indices
        for(int i=1; i<7; i++){
            this.drawString(this.font, String.valueOf(i), this.width /2 +col0, (i-1)*rowGap + row0 +5, 10526880);
        }

        //draw all text fields from list
        for (TextFieldWidget field : this.allTextFields){
            field.render(p_render_1_, p_render_2_, p_render_3_);
        }

        //first line only needs some strings drawn instead of text boxes
        this.drawString(this.font, "  0", this.width /2 +col1, row0 +5, 10526880);
        this.drawString(this.font, "  ----", this.width /2 +col2, row0 + 5, 10526880);
        this.drawString(this.font, "  ------------", this.width /2 +col4, row0 + 5, 10526880);

        super.render(p_render_1_, p_render_2_, p_render_3_);


        int i = 75;
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

    private void initTextFields(){

        //ROW0
/*        this.prevID = new NumberFieldWidget(this.font, this.width / 2 +col1, row0, 20, 20, I18n.format("gui.dialog"));
        this.prevID.setMaxStringLength(2);
        this.prevID.setText("" + this.entity.getDialog().dialogItems[0].getPrevItem());
        this.prevID.setEnabled(false);
        this.allTextFields.add(this.prevID);

        this.prereqFlag = new TextFieldWidget(this.font, this.width / 2 +col2, row0, 40, 20, I18n.format("gui.dialog"));
        this.prereqFlag.setMaxStringLength(10);
        this.prereqFlag.setEnabled(false);
        this.allTextFields.add(this.prereqFlag);*/

        this.type = new TextFieldWidget(this.font, this.width / 2 +col3, row0, 40, 20, I18n.format("gui.dialog"));
        this.type.setMaxStringLength(8);
        this.allTextFields.add(this.type);

/*        this.playerText = new TextFieldWidget(this.font, this.width / 2 +col4, row0, 90, 20, I18n.format("gui.dialog"));
        this.playerText.setMaxStringLength(60);
        this.playerText.setEnabled(false);
        this.allTextFields.add(this.playerText);*/

        this.npcResponse = new TextFieldWidget(this.font, this.width / 2 +col5, row0, 90, 20, I18n.format("gui.dialog"));
        this.npcResponse.setMaxStringLength(300);
        this.allTextFields.add(this.npcResponse);

        this.flagGiven = new TextFieldWidget(this.font, this.width / 2 +col6, row0, 40, 20, I18n.format("gui.dialog"));
        this.flagGiven.setMaxStringLength(5);
        this.allTextFields.add(this.flagGiven);

        //ROW1
        this.prevID1 = new NumberFieldWidget(this.font, this.width / 2 +col1, row0+rowGap, 20, 20, I18n.format("gui.dialog"));
        this.prevID1.setMaxStringLength(2);
        this.allTextFields.add(this.prevID1);

        this.prereqFlag1 = new TextFieldWidget(this.font, this.width / 2 +col2, row0+rowGap, 40, 20, I18n.format("gui.dialog"));
        this.prereqFlag1.setMaxStringLength(10);
        this.allTextFields.add(this.prereqFlag1);

        this.type1 = new TextFieldWidget(this.font, this.width / 2 +col3, row0+rowGap, 40, 20, I18n.format("gui.dialog"));
        this.type1.setMaxStringLength(8);
        this.allTextFields.add(this.type1);

        this.playerText1 = new TextFieldWidget(this.font, this.width / 2 +col4, row0+rowGap, 90, 20, I18n.format("gui.dialog"));
        this.playerText1.setMaxStringLength(60);
        this.allTextFields.add(this.playerText1);

        this.npcResponse1 = new TextFieldWidget(this.font, this.width / 2 +col5, row0+rowGap, 90, 20, I18n.format("gui.dialog"));
        this.npcResponse1.setMaxStringLength(300);
        this.allTextFields.add(this.npcResponse1);

        this.flagGiven1 = new TextFieldWidget(this.font, this.width / 2 +col6, row0+rowGap, 40, 20, I18n.format("gui.dialog"));
        this.flagGiven1.setMaxStringLength(5);
        this.allTextFields.add(this.flagGiven1);

        //ROW2
        this.prevID2 = new NumberFieldWidget(this.font, this.width / 2 +col1, row0+(2*rowGap), 20, 20, I18n.format("gui.dialog"));
        this.prevID2.setMaxStringLength(2);
        this.allTextFields.add(this.prevID2);

        this.prereqFlag2 = new TextFieldWidget(this.font, this.width / 2 +col2, row0+(2*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.prereqFlag2.setMaxStringLength(10);
        this.allTextFields.add(this.prereqFlag2);

        this.type2 = new TextFieldWidget(this.font, this.width / 2 +col3, row0+(2*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.type2.setMaxStringLength(8);
        this.allTextFields.add(this.type2);

        this.playerText2 = new TextFieldWidget(this.font, this.width / 2 +col4, row0+(2*rowGap), 90, 20, I18n.format("gui.dialog"));
        this.playerText2.setMaxStringLength(60);
        this.allTextFields.add(this.playerText2);

        this.npcResponse2 = new TextFieldWidget(this.font, this.width / 2 +col5, row0+(2*rowGap), 90, 20, I18n.format("gui.dialog"));
        this.npcResponse2.setMaxStringLength(300);
        this.allTextFields.add(this.npcResponse2);

        this.flagGiven2 = new TextFieldWidget(this.font, this.width / 2 +col6, row0+(2*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.flagGiven2.setMaxStringLength(5);
        this.allTextFields.add(this.flagGiven2);

        //ROW3
        this.prevID3 = new NumberFieldWidget(this.font, this.width / 2 +col1, row0+(3*rowGap), 20, 20, I18n.format("gui.dialog"));
        this.prevID3.setMaxStringLength(2);
        this.allTextFields.add(this.prevID3);

        this.prereqFlag3 = new TextFieldWidget(this.font, this.width / 2 +col2, row0+(3*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.prereqFlag3.setMaxStringLength(10);
        this.allTextFields.add(this.prereqFlag3);

        this.type3 = new TextFieldWidget(this.font, this.width / 2 +col3, row0+(3*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.type3.setMaxStringLength(8);
        this.allTextFields.add(this.type3);

        this.playerText3 = new TextFieldWidget(this.font, this.width / 2 +col4, row0+(3*rowGap), 90, 20, I18n.format("gui.dialog"));
        this.playerText3.setMaxStringLength(60);
        this.allTextFields.add(this.playerText3);

        this.npcResponse3 = new TextFieldWidget(this.font, this.width / 2 +col5, row0+(3*rowGap), 90, 20, I18n.format("gui.dialog"));
        this.npcResponse3.setMaxStringLength(300);
        this.allTextFields.add(this.npcResponse3);

        this.flagGiven3 = new TextFieldWidget(this.font, this.width / 2 +col6, row0+(3*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.flagGiven3.setMaxStringLength(5);
        this.allTextFields.add(this.flagGiven3);

        //ROW4
        this.prevID4 = new NumberFieldWidget(this.font, this.width / 2 +col1, row0+(4*rowGap), 20, 20, I18n.format("gui.dialog"));
        this.prevID4.setMaxStringLength(2);
        this.allTextFields.add(this.prevID4);

        this.prereqFlag4 = new TextFieldWidget(this.font, this.width / 2 +col2, row0+(4*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.prereqFlag4.setMaxStringLength(10);
        this.allTextFields.add(this.prereqFlag4);

        this.type4 = new TextFieldWidget(this.font, this.width / 2 +col3, row0+(4*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.type4.setMaxStringLength(8);
        this.allTextFields.add(this.type4);

        this.playerText4 = new TextFieldWidget(this.font, this.width / 2 +col4, row0+(4*rowGap), 90, 20, I18n.format("gui.dialog"));
        this.playerText4.setMaxStringLength(60);
        this.allTextFields.add(this.playerText4);

        this.npcResponse4 = new TextFieldWidget(this.font, this.width / 2 +col5, row0+(4*rowGap), 90, 20, I18n.format("gui.dialog"));
        this.npcResponse4.setMaxStringLength(300);
        this.allTextFields.add(this.npcResponse4);

        this.flagGiven4 = new TextFieldWidget(this.font, this.width / 2 +col6, row0+(4*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.flagGiven4.setMaxStringLength(5);
        this.allTextFields.add(this.flagGiven4);

        //ROW5
        this.prevID5 = new NumberFieldWidget(this.font, this.width / 2 +col1, row0+(5*rowGap), 20, 20, I18n.format("gui.dialog"));
        this.prevID5.setMaxStringLength(2);
        this.allTextFields.add(this.prevID5);

        this.prereqFlag5 = new TextFieldWidget(this.font, this.width / 2 +col2, row0+(5*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.prereqFlag5.setMaxStringLength(10);
        this.allTextFields.add(this.prereqFlag5);

        this.type5 = new TextFieldWidget(this.font, this.width / 2 +col3, row0+(5*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.type5.setMaxStringLength(8);
        this.allTextFields.add(this.type5);

        this.playerText5 = new TextFieldWidget(this.font, this.width / 2 +col4, row0+(5*rowGap), 90, 20, I18n.format("gui.dialog"));
        this.playerText5.setMaxStringLength(60);
        this.allTextFields.add(this.playerText5);

        this.npcResponse5 = new TextFieldWidget(this.font, this.width / 2 +col5, row0+(5*rowGap), 90, 20, I18n.format("gui.dialog"));
        this.npcResponse5.setMaxStringLength(300);
        this.allTextFields.add(this.npcResponse5);

        this.flagGiven5 = new TextFieldWidget(this.font, this.width / 2 +col6, row0+(5*rowGap), 40, 20, I18n.format("gui.dialog"));
        this.flagGiven5.setMaxStringLength(5);
        this.allTextFields.add(this.flagGiven5);

        //if a dialog for this npc already exists then attempt to fill the text fields with this data
        if (this.entity.getDialog() != null){
            try {
                NPCDialogItem item0 = this.entity.getDialog().dialogItems[0];
                this.type.setText(item0.getType().toString());
                this.npcResponse.setText(item0.getNpcResponse());
                this.flagGiven.setText(item0.getFlagGiven());
                NPCDialogItem item1 = this.entity.getDialog().dialogItems[1];
                this.prevID1.setText(item1.getPrevItem() + "");
                this.prereqFlag1.setText(item1.getPrereqFlag());
                this.type1.setText(item1.getType().toString());
                this.playerText1.setText(item1.getPlayerText());
                this.npcResponse1.setText(item1.getNpcResponse());
                this.flagGiven1.setText(item1.getFlagGiven());
                NPCDialogItem item2 = this.entity.getDialog().dialogItems[2];
                this.prevID2.setText(item2.getPrevItem() + "");
                this.prereqFlag2.setText(item2.getPrereqFlag());
                this.type2.setText(item2.getType().toString());
                this.playerText2.setText(item2.getPlayerText());
                this.npcResponse2.setText(item2.getNpcResponse());
                this.flagGiven2.setText(item2.getFlagGiven());
                NPCDialogItem item3 = this.entity.getDialog().dialogItems[3];
                this.prevID3.setText(item3.getPrevItem() + "");
                this.prereqFlag3.setText(item3.getPrereqFlag());
                this.type3.setText(item3.getType().toString());
                this.playerText3.setText(item3.getPlayerText());
                this.npcResponse3.setText(item3.getNpcResponse());
                this.flagGiven3.setText(item3.getFlagGiven());
                NPCDialogItem item4 = this.entity.getDialog().dialogItems[4];
                this.prevID4.setText(item4.getPrevItem() + "");
                this.prereqFlag4.setText(item4.getPrereqFlag());
                this.type4.setText(item4.getType().toString());
                this.playerText4.setText(item4.getPlayerText());
                this.npcResponse4.setText(item4.getNpcResponse());
                this.flagGiven4.setText(item4.getFlagGiven());
                NPCDialogItem item5 = this.entity.getDialog().dialogItems[5];
                this.prevID5.setText(item5.getPrevItem() + "");
                this.prereqFlag5.setText(item5.getPrereqFlag());
                this.type5.setText(item5.getType().toString());
                this.playerText5.setText(item5.getPlayerText());
                this.npcResponse5.setText(item5.getNpcResponse());
                this.flagGiven5.setText(item5.getFlagGiven());
            } catch (NullPointerException e){
                //there are no dialog items yet, that's okay
            }
        }
    }

}
