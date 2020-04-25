package net.grallarius.sunderednpcs.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class NPCDialogManager {

    private static int highestID = 0;

    public static int getNextDialogID(){
        File directory = new File("");

        String path = directory.getAbsolutePath()+ "\\mods\\sunderedquesting\\dialogs";

        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(file -> {

                if (Files.isRegularFile(file)) {
                    try {
                        //remove directories
                        String shortname = file.toString().substring(file.toString().lastIndexOf("\\")+1);
                        //remove .txt ending
                        shortname = shortname.substring(0, shortname.length()-4);

                        int fileID = Integer.parseInt(shortname);
                        System.out.println(fileID);
                        if (fileID > highestID) {
                            highestID = fileID;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (highestID + 1);
    }

    public static void createNewDialogFile(NPCDialog npcDialog){

        try {

            File dir = new File("mods/sunderedquesting/dialogs");
            dir.mkdirs();

            int nextID = getNextDialogID();

            npcDialog.setDialogID(nextID);

            File file = new File(dir, nextID + ".txt");
            file.createNewFile();

            ArrayList<String> emptyLines = new ArrayList<>();

            emptyLines.add("0#-1#####");
            for (int i=1; i<6; i++) {
                emptyLines.add(i +"######");
            }
            writeLinesToFile(emptyLines, nextID);

/*            List<String> lines = Arrays.asList("The first line", "The second line");
            Path file = Paths.get("the-file-name.txt");
            Files.write(file, lines, StandardCharsets.UTF_8);
            //Files.write(file, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            System.out.println("a new approach..");*/


            System.out.println("I made a file of name: " + file.toString());

        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void editExistingDialogFile(int dialogID, NPCDialog npcDialog){


        //if file of that ID not found
        if (!doesFileExist(dialogID)){
            System.out.println("File with that dialog ID was not found. Creating a new one instead.");
            createNewDialogFile(npcDialog);
        }


    }

    public static void writeLinesToFile(List<String> lines, int dialogID){
        try {
            Files.write(Paths.get("mods/sunderedquesting/dialogs/" + dialogID + ".txt"), lines);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static NPCDialog readLinesFromFile(int dialogID) /*throws FileNotFoundException*/ {
        //if file of that ID not found
        if (!doesFileExist(dialogID)){
            /*throw new FileNotFoundException(); */
            System.out.println("File not found. Something went very wrong.");
            return null;
        }
        NPCDialog dialog = new NPCDialog(dialogID);
        try {
            List<String> allLines = Files.readAllLines(Paths.get("mods/sunderedquesting/dialogs/" + dialogID + ".txt"));
            for (String line : allLines) {

                NPCDialogItem item = NPCDialogItem.fromLine(line);
                dialog.dialogItems[item.getItemID()-1] = item;
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public static boolean doesFileExist(int dialogID){
        File directory = new File("");

        AtomicBoolean matchIDfound = new AtomicBoolean(false);

        String path = directory.getAbsolutePath()+ "\\mods\\sunderedquesting\\dialogs";

        //check that the file you are trying to access even exists
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(file -> {

                if (Files.isRegularFile(file)) {
                    //remove directories
                    String shortname = file.toString().substring(file.toString().lastIndexOf("\\") + 1);
                    //remove .txt ending
                    shortname = shortname.substring(0, shortname.length() - 4);
                    //parse int from shortened filename
                    int fileID = Integer.parseInt(shortname);

                    if (fileID == dialogID) {
                        matchIDfound.set(true);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchIDfound.get();
    }

}
