package plugin;

import controllers.HuePluginController;
import listeners.HueEventListener;
import org.bukkit.plugin.java.JavaPlugin;
import transformers.BlockItemDataTransformer;

public class PluginBase extends JavaPlugin {

    private final String username = "BO-ZPrfySnudjaskrhYyPBVePr5D1josxc546KLa";
    private HuePluginController huePluginController;

    @Override
    public void onEnable() {
        getLogger().info("Parker's Plugin is running");

        BlockItemDataTransformer.setPlugin(this);
        huePluginController = new HuePluginController(username, this);

        getServer()
                .getPluginManager()
                .registerEvents(new HueEventListener(huePluginController), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Parker's Plugin has been disabled");
    }
}
