package dev.lapinski.persinate.generate;

import dev.lapinski.persinate.model.NativeInclusion;

public enum EntityGenerationType {
    LIVE,
    HST;

    public boolean includesIn(NativeInclusion nativeInclusion) {
        if (nativeInclusion == null) {
            return true;
        }

        return name().equals(nativeInclusion.name()) || nativeInclusion == NativeInclusion.ALWAYS;
    }
}
