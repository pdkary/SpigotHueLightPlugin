package utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NearbySignGrabber {
    private static final List<BlockFace> blockDirections = Arrays.asList(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH);
    private static final List<Material> signMaterials = Arrays.asList(
            Material.SPRUCE_WALL_SIGN,
            Material.ACACIA_WALL_SIGN,
            Material.BIRCH_WALL_SIGN,
            Material.DARK_OAK_WALL_SIGN,
            Material.JUNGLE_WALL_SIGN,
            Material.OAK_WALL_SIGN,
            Material.SPRUCE_SIGN,
            Material.ACACIA_SIGN,
            Material.BIRCH_SIGN,
            Material.DARK_OAK_SIGN,
            Material.JUNGLE_SIGN,
            Material.OAK_SIGN);

    public static List<Sign> getSigns(Block block){
        return blockDirections.stream()
                .filter(s -> signMaterials.contains(block.getRelative(s).getType()))
                .map(s -> (Sign) block.getRelative(s).getState())
                .collect(Collectors.toList());
    }
    public static List<String> getMessages(Block block){
        List<String> msgs = new ArrayList<>();
        getSigns(block).forEach(s -> msgs.addAll(Arrays.asList(s.getLines())));
        return msgs;
    }
}
