package no.rune.methodref;

import java.lang.reflect.Method;

public interface InvocationRegistry {

    void register(Method method, Object[] args);

}
