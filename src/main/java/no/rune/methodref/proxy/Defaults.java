package no.rune.methodref.proxy;

public final class Defaults {

    /**
     * A null value factory for cases where simply using <code>null</code> is not
     * robust enough, i.e. when you don't know if a primitive is required or not. This is
     * typically for cases involving proxy callbacks where return values for methods are irrelevant,
     * but one cannot just explicitly return a null-pointer because it will lead to a
     * {@link ClassCastException} for methods with primitives as return type. For regular
     * {@link Object} types, this method just returns <code>null</code>.
     *
     * @param <T> Type type to get null value for.
     * @param type The class of the type to get null value for.
     * @return <code>null</code>, except 0 for numeric primitive types (incl. char), and false for
     *         the boolean type.
     */
    @SuppressWarnings("unchecked")
    public static <T> T nullValueFor(Class<T> type) {
        if (type == Integer.TYPE) {
            return (T)Integer.valueOf(0);
        } else if (type == Double.TYPE) {
            return (T)Double.valueOf(0.0);
        } else if (type == Long.TYPE) {
            return (T)Long.valueOf(0L);
        } else if (type == Boolean.TYPE) {
            return (T)Boolean.FALSE;
        } else if (type == Float.TYPE) {
            return (T)Float.valueOf(0.0f);
        } else if (type == Short.TYPE) {
            return (T)Short.valueOf((short)0);
        } else if (type == Byte.TYPE) {
            return (T)Byte.valueOf((byte)0);
        } else if (type == Character.TYPE) {
            return (T)Character.valueOf('\u0000');
        } else {
            return null;
        }
    }

    private Defaults() {} static { new Defaults(); }
}
