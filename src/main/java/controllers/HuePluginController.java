package controllers;

import dtos.HueLight;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import transformers.BlockItemDataTransformer;
import utils.ItemMetaBuilder;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuePluginController {
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private final String username;
    public HueHttpController httpController;
    private Pattern pattern;

    public HuePluginController(String username, Logger logger) {
        this.pattern = Pattern.compile(IPADDRESS_PATTERN);
        this.username = username;
        this.httpController = new HueHttpController(this.username, logger);
    }

    public void openAssignmentInventory(Player player) {
        try {
            player.openInventory(httpController.getHueInventoryMenu());
        } catch (IOException e){
            player.sendMessage("HueLightPlugin Error: Invalid or Missing IP address");
        }
    }

    public boolean validateIP(String ip) {
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
