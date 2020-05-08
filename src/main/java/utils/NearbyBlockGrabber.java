package utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NearbyBlockGrabber {
    protected static final List<BlockFace> blockDirections = Arrays.asList(
            BlockFace.EAST,
            BlockFace.WEST,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.UP,
            BlockFace.DOWN);

    public static List<Block> getNearbyItems(Block block) {
        return blockDirections.stream()
                .map(block::getRelative)
                .collect(Collectors.toList());
    }

    public static List<Material> getNearbyMaterials(Block block) {
        return blockDirections.stream()
                .map(s -> block.getRelative(s).getType())
                .collect(Collectors.toList());
    }

    public static List<Block> getNearblyFlat(Integer radius, Block block) {
        List<Block> result = new ArrayList<>();
        List<Integer> searchedBlocks = new ArrayList<>();
        BlockFace prevDirection = BlockFace.NORTH;
        while (result.size() <= Math.pow(2 * radius + 1, 2)) {
            result.add(block);
            searchedBlocks.add(block.hashCode());
            if (searchedBlocks.contains(block.getRelative(getLeft(prevDirection)).hashCode())) {
                block = block.getRelative(prevDirection);
            } else {
                block = block.getRelative(getLeft(prevDirection));
                prevDirection = getLeft(prevDirection);
            }
        }
        return result;
    }

    public static List<Block> getStraightLine(Integer length, Block block, BlockFace direction) {
        List<Block> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            result.add(block.getRelative(direction));
        }
        return result;
    }

    public static List<Block> getNearbyCube(Integer radius, Block block) {
        List<Block> layers = new ArrayList<>();
        layers.add(block);
        layers.addAll(getStraightLine(radius, block, BlockFace.UP));
        layers.addAll(getStraightLine(radius, block, BlockFace.DOWN));

        List<Block> result = new ArrayList<>();
        for (Block b : layers) {
            result.addAll(getNearblyFlat(radius, b));
        }
        return result;
    }

    private static BlockFace getLeft(BlockFace face) {
        switch (face) {
            case NORTH:
                return BlockFace.WEST;
            case EAST:
                return BlockFace.NORTH;
            case SOUTH:
                return BlockFace.EAST;
            case WEST:
                return BlockFace.SOUTH;
            default:
                return null;
        }
    }
}
