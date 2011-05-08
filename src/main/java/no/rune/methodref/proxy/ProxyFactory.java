package no.rune.methodref.proxy;

import static java.util.Arrays.asList;
import static org.apache.commons.collections15.CollectionUtils.collect;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import no.rune.methodref.transform.Transformers;

import org.objenesis.ObjenesisHelper;

public class ProxyFactory {

    public static <T> T proxy(Class<T> type, Callback ... callbacks) {
        callbacks = callbacks.length != 0? callbacks : new Callback[]{NICE_NO_OP};
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallbackTypes(collect(asList(callbacks), Transformers.asType()).toArray(new Class<?>[callbacks.length]));

        @SuppressWarnings("unchecked")
        Class<T> proxyClass = enhancer.createClass();

        @SuppressWarnings("unchecked")
        T proxy = (T) ObjenesisHelper.newInstance(proxyClass);
        ((Factory)proxy).setCallbacks(callbacks);
        return proxy;
    }

    private static final Callback NICE_NO_OP = new MethodInterceptor() {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
            return Defaults.nullValueFor(method.getReturnType());
        }
    };

    private ProxyFactory() {} static { new ProxyFactory(); }

}
