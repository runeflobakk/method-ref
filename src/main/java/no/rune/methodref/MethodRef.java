package no.rune.methodref;

import static no.rune.methodref.proxy.ProxyFactory.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import no.rune.methodref.exception.MustRegisterMethodFirst;

public class MethodRef implements InvocationRegistry, Serializable {

    private static final ThreadLocal<MethodRef> METHODREF = new ThreadLocal<MethodRef>();

    private String name;
    private Object[] arguments;
    private Class<?> targetType;
    private Class<?> returnType;
    private MethodRef nextInChain;

    private MethodRef() {}

    public static <T> T on(Class<T> type) {
        MethodRef methodRef = new MethodRef();
        METHODREF.set(methodRef);
        return proxy(type, new InvocationRegistrar(methodRef));
    }

    public static MethodRef get() {
        MethodRef methodRef = METHODREF.get();
        if (methodRef != null) {
            METHODREF.set(null);
            return methodRef;
        }
        throw new MustRegisterMethodFirst();
    }

    @Override
    public void register(Method method, Object[] args) {
        MethodRef methodRef = this;
        for (; methodRef.nextInChain != null; methodRef = methodRef.nextInChain);
        methodRef.name = method.getName();
        methodRef.returnType = method.getReturnType();
        methodRef.arguments = args;
        methodRef.targetType = method.getDeclaringClass();
        methodRef.nextInChain = new MethodRef();
    }



    public String getName() {
        return name;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public MethodRef nextInChain() {
        return nextInChain;
    }

    public Class<?> getTargetType() {
        return targetType;
    }

}
