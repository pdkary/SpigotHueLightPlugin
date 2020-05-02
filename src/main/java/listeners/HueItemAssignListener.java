package listeners;

import controllers.HuePluginController;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import transformers.BlockItemDataTransformer;
import transformers.BlockStringManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class HueItemAssignListener implements Listener {

    public static String hue_id = "hue_id";
    public static Material ASSIGNER = Material.QUARTZ_BLOCK;
    private final Plugin plugin;
    private final HuePluginController pluginController;
    private final Logger logger;
    private boolean isReplaceEvent = false;

    public HueItemAssignListener(HuePluginController pluginController, Logger logger, Plugin plugin) {
        this.logger = logger;
        this.pluginController = pluginController;
        this.plugin = plugin;
        BlockItemDataTransformer.setPlugin(this.plugin);
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent playerInteractEvent) throws IOException {
        ItemStack inHand = playerInteractEvent
                .getPlayer()
                .getInventory()
                .getItemInMainHand();
        Block clickedBlock = playerInteractEvent.getClickedBlock();

        if (inHand.getType().equals(Material.AIR) && clickedBlock.getType().equals(ASSIGNER)) {
            pluginController.openAssignmentInventory(playerInteractEvent.getPlayer());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        ItemStack inHand = blockPlaceEvent.getItemInHand();
        if(blockPlaceEvent.getBlockPlaced().getType().equals(Material.REDSTONE_LAMP) && !isReplaceEvent){
            BlockItemDataTransformer.itemStackDataToBlock(inHand,blockPlaceEvent.getBlockPlaced(),hue_id);
        }
        if(isReplaceEvent)isReplaceEvent = false;
    }
}
