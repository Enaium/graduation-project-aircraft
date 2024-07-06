package extensions.java.lang.Object;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.function.Consumer;

/**
 * @author Enaium
 */
@Extension
public class ObjectExtensions {
    @SuppressWarnings("unchecked")
    public static <T> T apply(@This Object self, Consumer<T> consumer) {
        consumer.accept((T) self);
        return (T) self;
    }

    public static boolean isNull(@This Object self) {
        return self == null;
    }

    public static boolean isNotNull(@This Object self) {
        return self != null;
    }
}
