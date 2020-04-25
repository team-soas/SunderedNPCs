package net.grallarius.sunderednpcs.util;

import java.util.ArrayList;

public class NPCDialog {

    private int dialogID;
    public NPCDialogItem[] dialogItems;

    public NPCDialog(){
        //TODO match the array size to max size of dialog
        this.dialogItems = new NPCDialogItem[6];

        //make the file straight away!! This will auto create the id
        NPCDialogManager.createNewDialogFile(this);

    }

    public NPCDialog(int dialogID){
        this.dialogItems = new NPCDialogItem[6];
        setDialogID(dialogID);
    }

    public int getDialogID() {
        return dialogID;
    }

    public void setDialogID(int dialogID) {
        this.dialogID = dialogID;
    }

    public void editDialog(NPCDialogItem changedItem1, NPCDialogItem changedItem2, NPCDialogItem changedItem3,
                           NPCDialogItem changedItem4, NPCDialogItem changedItem5, NPCDialogItem changedItem6){

        //update the items that were sent in packet
        this.dialogItems[changedItem1.getItemID()-1] = changedItem1;
        this.dialogItems[changedItem2.getItemID()-1] = changedItem2;
        this.dialogItems[changedItem3.getItemID()-1] = changedItem3;
        this.dialogItems[changedItem4.getItemID()-1] = changedItem4;
        this.dialogItems[changedItem5.getItemID()-1] = changedItem5;
        this.dialogItems[changedItem6.getItemID()-1] = changedItem6;

        //write these newly changed dialog to file
        ArrayList<String> lines = new ArrayList<>();
        for (NPCDialogItem item : this.dialogItems){
            lines.add(item.toLine());
        }

        NPCDialogManager.writeLinesToFile(lines, this.getDialogID());

    }
}
