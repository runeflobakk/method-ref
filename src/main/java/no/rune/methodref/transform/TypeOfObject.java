package no.rune.methodref.transform;

import org.apache.commons.collections15.Transformer;

class TypeOfObject implements Transformer<Object, Class<?>> {

    @Override
    public Class<?> transform(Object object) {
        return object.getClass();
    }

}
