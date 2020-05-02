package listeners;

import controllers.HueHttpController;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import transformers.BlockItemDataTransformer;

import java.util.logging.Logger;

public class HueItemInteractEventListener implements Listener {
    public static String hue_id = "hue_id";
    public static Material POWERED_BLOCK = Material.REDSTONE_LAMP;

    private final Logger logger;
    private final HueHttpController hueHttpController;

    public HueItemInteractEventListener(HueHttpController hueHttpController, Logger logger) {
        this.logger = logger;
        this.hueHttpController = hueHttpController;
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent blockRedstoneEvent) {
        Material blockType = blockRedstoneEvent.getBlock().getType();
        if (blockType.equals(POWERED_BLOCK)) {
            String HueID = BlockItemDataTransformer.getValue(blockRedstoneEvent.getBlock(), hue_id);
            if (HueID != null) hueHttpController.toggleLight(HueID);
        }
    }
}
