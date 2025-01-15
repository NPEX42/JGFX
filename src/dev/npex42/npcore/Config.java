package dev.npex42.npcore;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class Config {
    private final HashMap<String, String> ITEMS = new HashMap<>();

    public String Lookup(String key) {
        return ITEMS.get(key);
    }

    public void Store(String key, String value) {
        ITEMS.put(key, value);
    }

    public void Save(String filepath) throws RuntimeException {
        StringBuilder sb = new StringBuilder();
        for (String key : ITEMS.keySet()) {
            sb.append(key);
            sb.append(':');
            sb.append(ITEMS.get(key));
            sb.append("\n");
        }

        IO.WriteText(filepath, sb.toString());
    }

    public static Config Load(String filepath) {
        List<String> lines = IO.ReadLines(filepath);
        Config c = new Config();

        for (String line : lines) {
            if (line.isEmpty()) continue;
            line = line.strip();
            String[] sections = line.split("\\s*:\\s*");
            c.Store(sections[0], sections[1]);
        }

        return c;
    }

    public void StoreObject(String key, Object value) {
        Class<?> clazz = value.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.canAccess(value)) {
                String fieldKey = key+"."+field.getName();
                try {
                    Object fieldValue = field.get(value);
//                    switch (fieldValue) {
////                        case null:
////                            Store(fieldKey, "null");
////                            break;
////                        case String s:
////                            Store(fieldKey, s);
////                            break;
////
////                        case Integer i:
////                            Store(fieldKey, ""+i);
////                            break;
////
////                        case Float f:
////                            Store(fieldKey, ""+f);
////                            break;
////
////                        case Long f:
////                            Store(fieldKey, ""+f);
////                            break;
////
////                        case Short f:
////                            Store(fieldKey, ""+f);
////                            break;
////
////                        case Byte f:
////                            Store(fieldKey, ""+f);
////                            break;
////
////                        case Double f:
////                            Store(fieldKey, ""+f);
////                            break;
////
////                        case Character f:
////                            Store(fieldKey, ""+f);
////                            break;
////
////                        default:
////                            StoreObject(fieldKey, fieldValue);
                    //}
                } catch (IllegalAccessException e) {
                    continue;
                }
            }
        }
    }

}
