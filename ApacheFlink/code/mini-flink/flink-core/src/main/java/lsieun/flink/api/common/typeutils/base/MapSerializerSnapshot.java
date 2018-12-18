package lsieun.flink.api.common.typeutils.base;


import java.util.Map;

import lsieun.flink.api.common.typeutils.CompositeSerializerSnapshot;
import lsieun.flink.api.common.typeutils.TypeSerializer;
import lsieun.flink.api.common.typeutils.TypeSerializerSnapshot;
import lsieun.flink.util.Preconditions;

/**
 * Snapshot class for the {@link MapSerializer}.
 */
public class MapSerializerSnapshot<K, V> implements TypeSerializerSnapshot<Map<K, V>> {

    private static final int CURRENT_VERSION = 1;

    private CompositeSerializerSnapshot nestedKeyValueSerializerSnapshot;

    /**
     * Constructor for read instantiation.
     */
    public MapSerializerSnapshot() {}

    /**
     * Constructor to create the snapshot for writing.
     */
    public MapSerializerSnapshot(TypeSerializer<K> keySerializer, TypeSerializer<V> valueSerializer) {
        Preconditions.checkNotNull(keySerializer);
        Preconditions.checkNotNull(valueSerializer);
        this.nestedKeyValueSerializerSnapshot = new CompositeSerializerSnapshot(keySerializer, valueSerializer);
    }

    @Override
    public int getCurrentVersion() {
        return CURRENT_VERSION;
    }

    @Override
    public TypeSerializer<Map<K, V>> restoreSerializer() {
        return new MapSerializer<>(
                nestedKeyValueSerializerSnapshot.getRestoreSerializer(0),
                nestedKeyValueSerializerSnapshot.getRestoreSerializer(1));
    }

    @Override
    public TypeSerializerSchemaCompatibility<Map<K, V>> resolveSchemaCompatibility(TypeSerializer<Map<K, V>> newSerializer) {
        checkState(nestedKeyValueSerializerSnapshot != null);

        if (newSerializer instanceof MapSerializer) {
            MapSerializer<K, V> serializer = (MapSerializer<K, V>) newSerializer;

            return nestedKeyValueSerializerSnapshot.resolveCompatibilityWithNested(
                    TypeSerializerSchemaCompatibility.compatibleAsIs(),
                    serializer.getKeySerializer(),
                    serializer.getValueSerializer());
        }
        else {
            return TypeSerializerSchemaCompatibility.incompatible();
        }
    }

    @Override
    public void writeSnapshot(DataOutputView out) throws IOException {
        nestedKeyValueSerializerSnapshot.writeCompositeSnapshot(out);
    }

    @Override
    public void readSnapshot(int readVersion, DataInputView in, ClassLoader userCodeClassLoader) throws IOException {
        this.nestedKeyValueSerializerSnapshot = CompositeSerializerSnapshot.readCompositeSnapshot(in, userCodeClassLoader);
    }
}
