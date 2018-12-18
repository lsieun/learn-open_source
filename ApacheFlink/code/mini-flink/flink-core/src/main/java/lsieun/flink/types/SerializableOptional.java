package lsieun.flink.types;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Serializable {@link Optional}.
 */
public final class SerializableOptional<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = -3312769593551775940L;

	private static final SerializableOptional<?> EMPTY = new SerializableOptional<>(null);

	@Nullable
	private final T value;

	private SerializableOptional(@Nullable T value) {
		this.value = value;
	}

	public T get() {
		if (value == null) {
			throw new NoSuchElementException("No value present");
		}
		return value;
	}

	public boolean isPresent() {
		return value != null;
	}

	public void ifPresent(Consumer<? super T> consumer) {
		if (value != null) {
			consumer.accept(value);
		}
	}

	public <R> Optional<R> map(Function<? super T, ? extends R> mapper) {
		if (value == null) {
			return Optional.empty();
		} else {
			return Optional.ofNullable(mapper.apply(value));
		}
	}

	public static <T extends Serializable> SerializableOptional<T> of(@Nonnull T value) {
		return new SerializableOptional<>(value);
	}

	public static <T extends Serializable> SerializableOptional<T> ofNullable(@Nullable T value) {
		if (value == null) {
			return empty();
		} else {
			return of(value);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> SerializableOptional<T> empty() {
		return (SerializableOptional<T>) EMPTY;
	}
}
