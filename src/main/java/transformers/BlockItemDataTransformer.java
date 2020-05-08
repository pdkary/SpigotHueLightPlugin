package transformers;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockItemDataTransformer {
    private static Plugin plugin;

    public static void setPlugin(Plugin plugin) {
        BlockItemDataTransformer.plugin = plugin;
    }

    public static Block itemStackDataToBlock(ItemStack stack, Block block, String key) {
        BlockStringManager blockStringManager = new BlockStringManager(block, BlockItemDataTransformer.plugin);
        ItemStackStringManager itemStackStringManager = new ItemStackStringManager(stack);
        String itemStackValue = itemStackStringManager.getValue(key);
        blockStringManager.setValue(key, itemStackValue);
        return blockStringManager.getEntity();
    }

    public static ItemStack setString(ItemStack stack, String key, String value) {
        ItemStackStringManager itemStackStringManager = new ItemStackStringManager(stack);
        itemStackStringManager.setValue(key, value);
        return itemStackStringManager.getEntity();
    }

    public static String getValue(Block block, String key) {
        BlockStringManager blockStringManager = new BlockStringManager(block, BlockItemDataTransformer.plugin);
        return blockStringManager.getValue(key);
    }
}
