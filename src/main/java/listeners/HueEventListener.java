package listeners;

import controllers.HueHttpController;
import controllers.HuePluginController;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import transformers.BlockItemDataTransformer;
import utils.NearbyBlockGrabber;
import utils.NearbyButtonGrabber;
import utils.NearbySignGrabber;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HueEventListener implements Listener {
    public static String hue_id = "hue_id";
    public static Material POWERED_BLOCK = Material.REDSTONE_LAMP;
    public static Material ASSIGNER = Material.QUARTZ_BLOCK;
    private final Logger logger;
    private final HuePluginController pluginController;
    private final HueHttpController httpController;

    public HueEventListener(HuePluginController pluginController) {
        this.logger = pluginController.logger;
        this.pluginController = pluginController;
        this.httpController = pluginController.httpController;
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent blockRedstoneEvent) throws IOException {
        Material blockType = blockRedstoneEvent.getBlock().getType();
        Block affectedBlock = blockRedstoneEvent.getBlock();

        if (blockType.equals(POWERED_BLOCK)) {
            String HueID = BlockItemDataTransformer.getValue(affectedBlock, hue_id);
            if (HueID != null) httpController.toggleLight(HueID);

        } else if (NearbyButtonGrabber.isButton(affectedBlock)) {
            List<Block> nearCube = NearbyBlockGrabber.getNearbyCube(1, affectedBlock);
            Block assigner = nearCube.stream()
                    .filter(s -> s.getType().equals(ASSIGNER))
                    .findFirst()
                    .orElse(null);

            if (assigner != null) {
                getIpFromSign(assigner);
                String username = httpController.getUsername();
                this.pluginController.setUsername(username);
            }
        }
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent playerInteractEvent) {
        ItemStack inHand = playerInteractEvent
                .getPlayer()
                .getInventory()
                .getItemInMainHand();
        Block clickedBlock = playerInteractEvent.getClickedBlock();

        if (inHand.getType().equals(Material.AIR)) {
            if (clickedBlock != null && clickedBlock.getType().equals(ASSIGNER)) {
                getIpFromSign(clickedBlock);
                pluginController.openAssignmentInventory(playerInteractEvent.getPlayer());
            }
        }
    }

    private void getIpFromSign(Block clickedBlock) {
        String ip = NearbySignGrabber
                .getMessages(clickedBlock)
                .stream()
                .filter(pluginController::validateIP)
                .collect(Collectors.joining());
        if (!ip.equals("")) httpController.setIp(ip);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        ItemStack inHand = blockPlaceEvent.getItemInHand();
        if (blockPlaceEvent.getBlockPlaced().getType().equals(Material.REDSTONE_LAMP)) {
            BlockItemDataTransformer.itemStackDataToBlock(inHand, blockPlaceEvent.getBlockPlaced(), hue_id);
        }
    }
}
