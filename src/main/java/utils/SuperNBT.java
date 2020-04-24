package utils;

import com.jojodmo.safeNBT.api.SafeNBT;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class SuperNBT extends SafeNBT {
    protected static Plugin plugin;
    protected Block block;

    public SuperNBT() {
        this((Object) null);
    }

    public SuperNBT(Object tagCompound) {
        super(tagCompound);
    }

    protected SuperNBT(Block block) {
        this.block = block;
    }

    public static SuperNBT get(Block block) {
        return new SuperNBT(block);
    }

    public static SuperNBT get(ItemStack itemStack) {
        return (SuperNBT) SafeNBT.get(itemStack);
    }

    public static void setPlugin(Plugin plugin) {
        SuperNBT.plugin = plugin;
    }

    public String getString(String key) {
        if (this.block != null) {
            MetadataValue metadataValue = this.block.getMetadata(key).get(0);
            return metadataValue.asString();
        } else {
            return super.getString(key);
        }
    }

    public void setString(String key, String value) {
        if (plugin != null && this.block != null) {
            this.block.setMetadata(key, new FixedMetadataValue(plugin, value));
        } else {
            super.setString(key, value);
        }
    }
}
