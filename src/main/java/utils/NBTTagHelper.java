package utils;

import com.jojodmo.safeNBT.api.SafeNBT;
import org.bukkit.inventory.ItemStack;

public class NBTTagHelper {

    public static String getNBTString(ItemStack itemInHand, String key) {
        SafeNBT stackNbt = SafeNBT.get(itemInHand);
        return stackNbt.getString(key);
    }

    public static ItemStack setNBTString(ItemStack item, String key, String value) {
        SafeNBT stackNbt = SafeNBT.get(item);
        stackNbt.setString(key, value);
        return stackNbt.apply(item);
    }
}
