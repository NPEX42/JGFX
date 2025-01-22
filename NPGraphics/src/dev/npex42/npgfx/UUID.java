package dev.npex42.npgfx;

import java.util.Objects;

public class UUID {
    private long high, low;

    public UUID() {
        high = (long) (Math.random() * Long.MAX_VALUE);
        low =  (long) (Math.random() * Long.MAX_VALUE);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UUID uuid)) return false;
        return high == uuid.high && low == uuid.low;
    }

    @Override
    public int hashCode() {
        return Objects.hash(high, low);
    }
}
