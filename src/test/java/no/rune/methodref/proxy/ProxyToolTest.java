package no.rune.methodref.proxy;

import static no.rune.methodref.proxy.Defaults.nullValueFor;
import static no.rune.methodref.proxy.ProxyFactory.proxy;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProxyToolTest {

    @Mock
    private MethodInterceptor methodInterceptor;

    private final Queue<Callback> interceptorQueue = new LinkedList<Callback>();

    @Test
    public void createNoOpProxiesWithoutAnyCallbacks() throws IOException {
        StringReader reader = proxy(StringReader.class);
        reader.read();
    }

    @Test
    public void createsProxiesWithCallback() throws Throwable {
        StringReader reader = proxy(StringReader.class, methodInterceptor);
        reader.read();
        Method readMethod = StringReader.class.getMethod("read");
        verify(methodInterceptor, times(1)).intercept(any(Object.class), eq(readMethod), argThat(emptyArray()), any(MethodProxy.class));
    }

    @Test
    public void createsProxiesWithCallbacksInvokedInSequence() throws IOException {
        class AddsItselfToInterceptorQueue implements MethodInterceptor {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
                interceptorQueue.add(this);
                return nullValueFor(method.getReturnType());
            }
        }

        Callback first = new AddsItselfToInterceptorQueue();
        Callback second = new AddsItselfToInterceptorQueue();

        StringReader reader = proxy(StringReader.class, first, second);
        reader.read();

        assertThat(interceptorQueue.poll(), sameInstance(first));
        assertThat(interceptorQueue.poll(), sameInstance(second));
        assertThat(interceptorQueue, org.hamcrest.Matchers.<Callback>empty());

    }
}
