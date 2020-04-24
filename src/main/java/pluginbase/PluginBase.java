package pluginbase;

import controllers.HueController;
import listeners.HueItemAssignListener;
import listeners.HueItemInteractEventListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginBase extends JavaPlugin {

    private HueController hueController;

    @Override
    public void onEnable() {
        hueController = new HueController("192.168.50.63", "nDUnfbZceW0m6wrMM3TK8rAbr2Sqgfu5N8LtdiMQ", getLogger());
        getLogger().info("Parker's Plugin is running");
        getServer().getPluginManager().registerEvents(new HueItemAssignListener(hueController, getLogger(), this), this);
        getServer().getPluginManager().registerEvents(new HueItemInteractEventListener(hueController, getLogger()), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Parker's Plugin has been disabled");
    }
}
