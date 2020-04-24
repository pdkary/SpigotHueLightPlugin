package listeners;

import controllers.HueController;
import dtos.HueLight;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import utils.SuperNBT;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class HueItemAssignListener implements Listener {

    public static final Material TRIGGER = Material.REDSTONE_LAMP;
    public static Material ASSIGNER = Material.QUARTZ_BLOCK;
    private final String hue_id = "hue_id";

    private final HueController hueController;
    private Inventory assignmentInventory;
    private List<HueLight> lights;
    private final Logger logger;
    private final Plugin plugin;

    public HueItemAssignListener(HueController hueController, Logger logger, Plugin plugin) {
        this.logger = logger;
        this.hueController = hueController;
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent playerInteractEvent) throws IOException {
        ItemStack inHand = playerInteractEvent
                .getPlayer()
                .getInventory()
                .getItemInMainHand();
        Block clickedBlock = playerInteractEvent.getClickedBlock();

        if (inHand.getType().equals(Material.AIR) && clickedBlock.getType().equals(ASSIGNER)) {
            updateInventory();
            playerInteractEvent
                    .getPlayer()
                    .openInventory(assignmentInventory);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        SuperNBT itemNBT = SuperNBT.get(blockPlaceEvent.getItemInHand());
        String ID = itemNBT.getString(hue_id);

        SuperNBT blockNbt = SuperNBT.get(blockPlaceEvent.getBlockPlaced());
        blockNbt.setString(hue_id, ID);
    }


    private void updateInventory() throws IOException {
        lights = hueController.getLights();
        Integer inventorySize = 9 * ((9 % lights.size()) + 1);
        assignmentInventory = Bukkit.createInventory(null, inventorySize, "Hue Light Assigner");
        for (HueLight light : lights) {
            ItemStack stack = new ItemStack(TRIGGER, 1);
            SuperNBT stackNBT = SuperNBT.get(stack);
            stackNBT.setString(hue_id, light.ID);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(light.name);
            meta.setLore(Arrays.asList(light.productName));
            stack.setItemMeta(meta);
            assignmentInventory.setItem(lights.indexOf(light), stack);
        }
    }
}
