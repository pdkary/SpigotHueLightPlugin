package dtos;

import java.util.Map;

public interface BlockItemDataManager<ENTITY, DATATYPE> {
    public BlockItemDataManager setValue(String key, DATATYPE value);

    public DATATYPE getValue(String key);

    public ENTITY getEntity();

    public boolean hasKey(String key);

    public Map<DATATYPE, DATATYPE> getMap();
}
