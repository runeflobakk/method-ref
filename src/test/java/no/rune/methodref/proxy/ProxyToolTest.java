package no.rune.methodref.proxy;

import static no.rune.methodref.proxy.ProxyFactory.proxy;
import static org.hamcrest.Matchers.emptyArray;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.argThat;

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
        Method readMethod = StringReader.class.getMethod("read");
        verify(methodInterceptor, times(1)).intercept(any(Object.class), eq(readMethod), argThat(emptyArray()), any(MethodProxy.class));
    }
}
