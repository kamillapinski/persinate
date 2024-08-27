package dev.lapinski.persinate.source.xml.wrapper;

import dev.lapinski.persinate.model.NativeInclusion;

public final class InclusionConverter {
    public static NativeInclusion convert(dev.lapinski.persinate.schemas.NativeInclusion nativeInclusion) {
        return switch (nativeInclusion) {
            case HST -> NativeInclusion.HST;
            case LIVE -> NativeInclusion.LIVE;
            case ALWAYS -> NativeInclusion.ALWAYS;
        };
    }
}
