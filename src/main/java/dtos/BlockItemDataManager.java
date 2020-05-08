package dtos;

import java.util.Map;

public interface BlockItemDataManager<ENTITY, DATATYPE> {
    BlockItemDataManager setValue(String key, DATATYPE value);

    DATATYPE getValue(String key);

    ENTITY getEntity();

    boolean hasKey(String key);

    Map<DATATYPE, DATATYPE> getMap();
}
