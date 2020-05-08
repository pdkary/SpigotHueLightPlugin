package controllers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import transformers.BlockItemDataTransformer;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuePluginController {
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public final Logger logger;
    private final Plugin plugin;
    private final Pattern pattern;
    public HueHttpController httpController;
    private String username;

    public HuePluginController(String username, Plugin plugin) {
        this.pattern = Pattern.compile(IPADDRESS_PATTERN);
        this.username = username;
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.httpController = new HueHttpController(this.username, logger);
        BlockItemDataTransformer.setPlugin(this.plugin);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void openAssignmentInventory(Player player) {
        try {
            player.openInventory(httpController.getHueInventoryMenu());
        } catch (IOException e) {
            player.sendMessage("HueLightPlugin Error: Invalid or Missing IP address");
        }
    }

    public boolean validateIP(String ip) {
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
