package listeners;

import controllers.HueController;
import dtos.HueLight;
import org.bukkit.Material;
import org.bukkit.block.data.type.Switch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import utils.SuperNBT;

import java.io.IOException;
import java.util.logging.Logger;

public class HueItemInteractEventListener implements Listener {
    public static Material POWERED_BLOCK = Material.REDSTONE_LAMP;

    private final Logger logger;
    private final HueController hueController;

    public HueItemInteractEventListener(HueController hueController, Logger logger) {
        this.logger = logger;
        this.hueController = hueController;
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent blockRedstoneEvent) throws IOException {
        Material blockType = blockRedstoneEvent.getBlock().getType();
        if (blockType.equals(POWERED_BLOCK)) {
            SuperNBT powerBlockNBT = SuperNBT.get(blockRedstoneEvent.getBlock());
            String HueID = powerBlockNBT.getString("hue_id");
            HueLight light = hueController.getLightByID(HueID);
            if (light != null) hueController.toggleLight(light);
        }
        if (blockType.data.equals(Switch.class)) {

        }
    }

//    @EventHandler
//    public Block getBlockWithButton(Switch button){
//        BlockFace blockFace = button.getFacing();
//    }


}
