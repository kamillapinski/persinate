package dev.lapinski.persinate.api;

public final class PersinateColumns {
    private PersinateColumns() {
    }

    public static final String UUID = "uuid";
    public static final String VERSION = "version";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    public static final class Hst {
        private Hst() {
        }

        public static final String LIVE_UUID = "live_uuid";
        public static final String TRANSACTION_TIMESTAMP = "transaction_timestamp";
        public static final String OPERATION = "operation";
    }
}
