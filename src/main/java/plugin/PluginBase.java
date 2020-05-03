package plugin;

import controllers.HuePluginController;
import listeners.HueItemAssignListener;
import listeners.HueItemInteractEventListener;
import org.bukkit.plugin.java.JavaPlugin;
import transformers.BlockItemDataTransformer;

public class PluginBase extends JavaPlugin {

    private HuePluginController huePluginController;

    @Override
    public void onEnable() {
        BlockItemDataTransformer.setPlugin(this);
        huePluginController = new HuePluginController("nDUnfbZceW0m6wrMM3TK8rAbr2Sqgfu5N8LtdiMQ", getLogger());
        getLogger().info("Parker's Plugin is running");
        getServer().getPluginManager().registerEvents(new HueItemAssignListener(huePluginController, getLogger(), this), this);
        getServer().getPluginManager().registerEvents(new HueItemInteractEventListener(huePluginController.httpController, getLogger()), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Parker's Plugin has been disabled");
    }
}
