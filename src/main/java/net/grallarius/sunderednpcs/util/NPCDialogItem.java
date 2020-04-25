package net.grallarius.sunderednpcs.util;

import javax.annotation.Nullable;

public class NPCDialogItem {

    private int itemID;
    private int prevItem;
    private String prereqFlag;
    private DialogItemType type;
    private String playerText;
    private String npcResponse;
    private String flagGiven;

    public NPCDialogItem(int itemID, int prevItem, @Nullable String prereqFlag, DialogItemType type, String playerText, String npcResponse, @Nullable String flagGiven) {
        this.itemID = itemID;
        this.prevItem = prevItem;
        this.prereqFlag = prereqFlag;
        this.type = type;
        this.playerText = playerText;
        this.npcResponse = npcResponse;
        this.flagGiven = flagGiven;
    }

    //TODO make sure # can't be entered as character into the screen where these items are inputted.
    /**
     * Makes a single string line out of the class variables for storage as a line in a dialog file, using # as separator
     * @return string to be stored in dialog file
     */
    public String toLine(){
        return this.itemID + "#" +
                this.prevItem + "#" +
                this.prereqFlag + "#" +
                this.type + "#" +
                this.playerText + "#" +
                this.npcResponse + "#" +
                this.flagGiven;

    }

    public static String toLineFromStrings(String itemID, String prevItem, String prereqFlag, String type, String playerText, String npcResponse, String flagGiven){
        return itemID + "#" +
                prevItem + "#" +
                prereqFlag + "#" +
                type + "#" +
                playerText + "#" +
                npcResponse + "#" +
                flagGiven;
    }

    /**
     * Starts by cutting strings off the end at the separator #, working its way through all variables from right to left
     * @param s string from file that need to be turned back into a dialog item
     * @return a new dialog item with variables read out of the string
     */
    public static NPCDialogItem fromLine(String s){

        //TODO make a safer reader with some good defaults
        String flagGiven = s.substring(s.lastIndexOf("#")+1);
        s = s.substring(0, s.lastIndexOf("#"));
        String npcResponse = s.substring(s.lastIndexOf("#")+1);
        s = s.substring(0, s.lastIndexOf("#"));
        String playerText = s.substring(s.lastIndexOf("#")+1);
        s = s.substring(0, s.lastIndexOf("#"));
        DialogItemType type = DialogItemType.fromString(s.substring(s.lastIndexOf("#")+1));
        s = s.substring(0, s.lastIndexOf("#"));
        String prereqFlag = s.substring(s.lastIndexOf("#")+1);
        s = s.substring(0, s.lastIndexOf("#"));
        int prevItem = 0;
        try { prevItem = Integer.parseInt(s.substring(s.lastIndexOf("#")+1)); } catch (Exception ex){}
        s = s.substring(0, s.lastIndexOf("#"));
        int itemID = Integer.parseInt(s);

        return new NPCDialogItem(itemID, prevItem, prereqFlag, type, playerText, npcResponse, flagGiven);
    }

    public int getItemID() {
        return itemID;
    }

    public int getPrevItem() {
        return prevItem;
    }

    public String getPrereqFlag() {
        return prereqFlag;
    }

    public DialogItemType getType() {
        return type;
    }

    public String getPlayerText() {
        return playerText;
    }

    public String getNpcResponse() {
        return npcResponse;
    }

    public String getFlagGiven() {
        return flagGiven;
    }

    public enum DialogItemType{
        BLANK, CHAT, CHATRET, SHOP, COMMAND, HIRE;

        public static DialogItemType fromString(String s){
            switch (s.toLowerCase()){
                case "chat": return CHAT;
                case "chatret": return CHATRET;
                case "shop": return SHOP;
                case "command": return COMMAND;
                case "hire": return HIRE;
                default: return BLANK;
            }
        }
    }
}
