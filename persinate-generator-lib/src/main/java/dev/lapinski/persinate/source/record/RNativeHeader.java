package dev.lapinski.persinate.source.record;

import dev.lapinski.persinate.model.NativeHeader;

public record RNativeHeader(boolean replace, String codeBlock) implements NativeHeader {
}
