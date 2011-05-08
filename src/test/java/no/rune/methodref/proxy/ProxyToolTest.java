package no.rune.methodref.proxy;

import static no.rune.methodref.proxy.ProxyFactory.proxy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;

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

    @Test
    public void createNoOpProxiesWithoutAnyCallbacks() throws IOException {
        StringReader reader = proxy(StringReader.class);
        reader.read();
    }

    @Test
    public void createsProxiesWithCallback() throws Throwable {
        StringReader reader = proxy(StringReader.class, methodInterceptor);
        reader.read();
        verify(methodInterceptor).intercept(any(Object.class), any(Method.class), any(Object[].class), any(MethodProxy.class));
    }
}
