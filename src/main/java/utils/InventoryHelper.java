package utils;

import dtos.HueLight;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import transformers.BlockItemDataTransformer;

import java.util.List;

public class InventoryHelper {

    private static final String hue_id = "hue_id";

    public static Inventory getInventory(List<HueLight> lights, Material block) {
        Integer numRows = (int) Math.ceil(lights.size() / 9.0);
        Integer offset = lights.size() % 9;
        Integer inventorySize = numRows == 0 ? 9 : 9*numRows;
        Inventory assignmentInventory = Bukkit.createInventory(null, inventorySize, "Hue Light Assigner");
        for (HueLight light : lights) {
            ItemStack stack = new ItemStack(block, 1);

            stack.setItemMeta(new ItemMetaBuilder(stack.getItemMeta())
                    .setDisplayName(light.name)
                    .setLore(light.productId)
                    .get());

            stack = BlockItemDataTransformer.setString(stack, hue_id, light.ID);
            assignmentInventory.setItem(lights.indexOf(light), stack);
        }
        return assignmentInventory;
    }
}
