package net.grallarius.sunderednpcs.client;

import net.grallarius.sunderednpcs.SunderedNPCs;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ModNPCTextureManager {

    public static ArrayList<ResourceLocation> textureOptions = new ArrayList<>();
    //public static ArrayList<ResourceLocation> textureOptions2 = new ArrayList<>();


    public static void locateAllTextureFiles(String path){
        //System.out.println("In listAllfiles(String path) method");
        System.out.println("ATTEMPTING TO ADD TO TEXTUREOPTIONS:");

/*
        System.out.println("Attempting to get resource locations for texture files and add them to list:");
        Minecraft mc = Minecraft.getInstance();
        textureOptions2.addAll(mc.getResourceManager().getAllResourceLocations(path, Predicate.isEqual("npctextures")));
        System.out.println(textureOptions2.size() + " textures were added to a list");
*/

        //Object object = new DownloadingTexture(textureFile, null, MODEL_TEXTURE, new ImageBufferDownload());

        //mc.getTextureManager().

        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
/*
                        ArrayList<ClientResourcePackInfo> packs = new ArrayList<>();
                        ResourcePackList resourcePacks = mc.getResourcePackList();

                        packs.add(mc.getResourcePackList().getPackInfo("npctextures"));
                        packs = (ArrayList<ClientResourcePackInfo>) mc.getResourcePackList().getEnabledPacks();
                        //mc.getResourcePackList().setEnabledPacks(packs);
                        Collection<ResourceLocation> locations = packs.get(0).getResourcePack().getAllResourceLocations(ResourcePackType.CLIENT_RESOURCES, "", 10, Predicate.isEqual(""));
                        System.out.println(locations);

*/

                        String filename = filePath.toString().substring(filePath.toString().lastIndexOf("\\")+1);
                        textureOptions.add(new ResourceLocation(SunderedNPCs.MODID, "textures/entity/" + filename));
                        System.out.println(filename + " has been added to textureOptions from " + filePath);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
