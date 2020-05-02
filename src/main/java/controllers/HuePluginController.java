package controllers;

import dtos.HueLight;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import transformers.BlockItemDataTransformer;
import utils.ItemMetaBuilder;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class HuePluginController {
    public static Material TRIGGER = Material.REDSTONE_LAMP;
    private final String hue_id = "hue_id";
    private final String ip;
    private final String username;
    public HueHttpController httpController;

    public HuePluginController(String ip, String username, Logger logger) {

        this.ip = ip;
        this.username = username;
        this.httpController = new HueHttpController(this.ip, this.username, logger);
    }

    public Inventory getHueInventoryMenu() throws IOException {
        List<HueLight> lights = httpController.getLights();
        Integer inventorySize = 9 * ((9 % lights.size()) + 1);
        Inventory assignmentInventory = Bukkit.createInventory(null, inventorySize, "Hue Light Assigner");
        for (HueLight light : lights) {
            ItemStack stack = new ItemStack(TRIGGER, 1);

            ItemMeta meta = new ItemMetaBuilder(stack.getItemMeta())
                    .setDisplayName(light.name)
                    .setLore(light.productId)
                    .get();

            stack.setItemMeta(meta);
            stack = BlockItemDataTransformer.setString(stack,hue_id,light.ID);
            assignmentInventory.setItem(lights.indexOf(light), stack);
        }
        return assignmentInventory;
    }

    public void openAssignmentInventory(Player player) throws IOException {
        player.openInventory(getHueInventoryMenu());
    }
}
