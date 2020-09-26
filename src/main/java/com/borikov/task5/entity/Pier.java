package com.borikov.task5.entity;

import com.borikov.task5.util.IdGenerator;

public class Pier {
    private final long pierId;

    public Pier() {
        this.pierId = IdGenerator.generateId();
    }

    public long getPierId() {
        return pierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pier pier = (Pier) o;
        return pierId == pier.pierId;
    }

    @Override
    public int hashCode() {
        return (int) (pierId ^ (pierId >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pier{");
        sb.append("pierId=").append(pierId);
        sb.append('}');
        return sb.toString();
    }
}
