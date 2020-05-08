import com.jojodmo.safeNBT.api.SafeNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Main {
    public static void main(String[] args) {
        ItemStack item = new ItemStack(Material.STICK);
        SafeNBT safeNBT = SafeNBT.get(item);

        safeNBT.setString("Test", "test");

        item = safeNBT.apply(item);

        SafeNBT newSafeNbt = SafeNBT.get(item);
        assert (newSafeNbt.getString("Test").equals("test"));
    }
}
