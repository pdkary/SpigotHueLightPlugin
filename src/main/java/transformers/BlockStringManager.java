package transformers;

import dtos.BlockItemDataManager;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class BlockStringManager implements BlockItemDataManager<Block, String> {
    private final Plugin plugin;
    private final Map<String, String> data;
    public Block block;

    public BlockStringManager(Block block, Plugin plugin) {
        this.block = block;
        this.plugin = plugin;
        this.data = new HashMap<>();
    }

    @Override
    public BlockStringManager setValue(String key, String Value) {
        this.block.setMetadata(key, new FixedMetadataValue(this.plugin, Value));
        this.data.put(key, Value);
        return this;
    }

    @Override
    public String getValue(String key) {
        if (this.hasKey(key)) return this.block
                .getMetadata(key)
                .get(0)
                .asString();
        return null;
    }

    @Override
    public Block getEntity() {
        return this.block;
    }

    @Override
    public boolean hasKey(String key) {
        return this.block.getMetadata(key).size() != 0;
    }

    @Override
    public Map<String, String> getMap() {
        return this.data;
    }
}
