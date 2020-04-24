package listeners;

import org.bukkit.event.Listener;
import pluginbase.PluginBase;

public class PlayerListener implements Listener {
    private final PluginBase plugin;

    public PlayerListener(PluginBase instance) {
        plugin = instance;
    }
}
