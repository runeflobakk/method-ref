package no.rune.methodref.exception;

import no.rune.methodref.MethodRef;

public class MustRegisterMethodFirst extends RuntimeException {

    public MustRegisterMethodFirst() {
        super("There has not been any method invocation registered with " +
                MethodRef.class.getSimpleName() + ".on(Class).method()");
    }
}
