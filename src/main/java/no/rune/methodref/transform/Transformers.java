package no.rune.methodref.transform;

import org.apache.commons.collections15.Transformer;

public final class Transformers {

    public static Transformer<Object, Class<?>> asType() {
        return new TypeOfObject();
    }

    private Transformers() {} static { new Transformers(); }
}
