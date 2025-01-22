package dev.npex42.npgfx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssetRegistry<T> {
    private Map<String, T> assets = new HashMap<>();
    public void Put(String id, T asset) { assets.put(id, asset); }
    public T    Get(String id)          { return assets.get(id); }

    public Set<String> Keys() {
        return assets.keySet();
    }
}
