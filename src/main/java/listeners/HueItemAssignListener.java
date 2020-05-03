package listeners;

import controllers.HuePluginController;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import transformers.BlockItemDataTransformer;
import utils.NearbySignGrabber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HueItemAssignListener implements Listener {

    public static String hue_id = "hue_id";
    public static Material ASSIGNER = Material.QUARTZ_BLOCK;
    private final Plugin plugin;
    private final HuePluginController pluginController;

    private final Logger logger;

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
            List<String> msgs = NearbySignGrabber.getMessages(clickedBlock);
            String ip = msgs.stream().filter(msg -> pluginController.validateIP(msg)).collect(Collectors.joining());

            if (!ip.equals("")) pluginController.httpController.setIp(ip);
            pluginController.openAssignmentInventory(playerInteractEvent.getPlayer());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        ItemStack inHand = blockPlaceEvent.getItemInHand();
        if (blockPlaceEvent.getBlockPlaced().getType().equals(Material.REDSTONE_LAMP)) {
            BlockItemDataTransformer.itemStackDataToBlock(inHand, blockPlaceEvent.getBlockPlaced(), hue_id);
        }
    }
}
