package utils;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public class NearbyButtonGrabber {
    public static final List<Material> materials = Arrays.asList(
            Material.BIRCH_BUTTON,
            Material.ACACIA_BUTTON,
            Material.DARK_OAK_BUTTON,
            Material.JUNGLE_BUTTON,
            Material.OAK_BUTTON,
            Material.SPRUCE_BUTTON,
            Material.STONE_BUTTON);

    public static boolean isButton(Block block) {
        return materials.contains(block.getType());
    }
}
