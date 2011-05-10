package no.rune.methodref;

import static java.lang.reflect.Modifier.isFinal;
import static no.rune.methodref.proxy.ProxyFactory.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import no.rune.methodref.exception.MustRegisterMethodFirst;

public class MethodRef implements Serializable {

    private static final ThreadLocal<MethodRef> METHODREF = new ThreadLocal<MethodRef>();

    private String name;
    private Object[] arguments;
    private Class<?> returnType;
    private MethodRef nextInChain;
    private Class<?> targetType;

    private MethodRef() {}

    public static <T> T on(Class<T> type) {
        METHODREF.set(new MethodRef());
        return proxy(type, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
                METHODREF.get().register(method, args);
                return nextMethodRegistratorIfPossible(method.getReturnType());
            }

            protected Object nextMethodRegistratorIfPossible(Class<?> returnType) {
                return isFinal(returnType.getModifiers())? null : proxy(returnType, this);
            }
        });
    }

    public void register(Method method, Object[] args) {
        MethodRef methodRef = this;
        for (; methodRef.nextInChain != null; methodRef = methodRef.nextInChain);
        methodRef.name = method.getName();
        methodRef.returnType = method.getReturnType();
        methodRef.arguments = args;
        methodRef.targetType = method.getDeclaringClass();
        methodRef.nextInChain = new MethodRef();
    }

    public static MethodRef get() {
        MethodRef methodRef = METHODREF.get();
        if (methodRef != null) {
            METHODREF.set(null);
            return methodRef;
        }
        throw new MustRegisterMethodFirst();
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
