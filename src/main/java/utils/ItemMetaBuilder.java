package utils;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemMetaBuilder {
    private final ItemMeta meta;

    public ItemMetaBuilder(ItemMeta meta) {
        this.meta = meta;
    }

    public ItemMetaBuilder setDisplayName(String displayName) {
        this.meta.setDisplayName(displayName);
        return this;
    }

    public ItemMetaBuilder setLore(String lore) {
        this.meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemMeta get() {
        return this.meta;
    }
}
