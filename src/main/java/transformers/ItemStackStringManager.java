package transformers;

import com.jojodmo.safeNBT.api.SafeNBT;
import dtos.BlockItemDataManager;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemStackStringManager implements BlockItemDataManager<ItemStack,String> {
    public ItemStack stack;
    public SafeNBT stackNBT;
    private Map<String,String> data;

    public ItemStackStringManager(ItemStack stack) {
        this.stack = stack;
        this.stackNBT = SafeNBT.get(stack);
        this.data = new HashMap<>();
    }

    @Override
    public ItemStackStringManager setValue(String key, String value) {
        this.stackNBT.setString(key,value);
        this.data.put(key,value);
        return this;
    }

    @Override
    public String getValue(String key) {
        if(this.hasKey(key))return this.stackNBT.getString(key);
        else return null;
    }

    @Override
    public ItemStack getEntity() {
        return this.stackNBT.apply(this.stack);
    }

    @Override
    public boolean hasKey(String key) {
        return this.stackNBT.hasKey(key);
    }

    @Override
    public Map<String, String> getMap() {
        return this.data;
    }
}
