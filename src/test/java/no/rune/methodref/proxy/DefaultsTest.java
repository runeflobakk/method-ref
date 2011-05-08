package no.rune.methodref.proxy;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.sun.tools.hat.internal.parser.Reader;


public class DefaultsTest {

    @Test
    public void nullValueForAnyObjectReturnsNullPointer() {
        assertNull(Defaults.nullValueFor(String.class));
        assertNull(Defaults.nullValueFor(Integer.class));
        assertNull(Defaults.nullValueFor(Reader.class));
    }

    @Test
    public void nullValueForNonBooleanPrimitivesReturnsZeroValues() {
        int zeroInt = Defaults.nullValueFor(Integer.TYPE);
        assertThat(zeroInt, is(0));

        long zeroLong = Defaults.nullValueFor(Long.TYPE);
        assertThat(zeroLong, is(0L));

        double zeroDouble = Defaults.nullValueFor(Double.TYPE);
        assertThat(zeroDouble, is(0.0));

        float zeroFloat = Defaults.nullValueFor(Float.TYPE);
        assertThat(zeroFloat, is(0.0f));

        short zeroShort = Defaults.nullValueFor(Short.TYPE);
        assertThat(zeroShort, is((short)0));

        byte zeroByte = Defaults.nullValueFor(Byte.TYPE);
        assertThat(zeroByte, is((byte)0));

        char zeroChar = Defaults.nullValueFor(Character.TYPE);
        assertThat(zeroChar, is('\u0000'));

    }

    @Test
    public void nullValueForBooleanReturnsFalse() {
        boolean falseValue = Defaults.nullValueFor(Boolean.TYPE);
        assertFalse(falseValue);
    }

    @Test
    public void nullValueForVoidTypeAlthoughMakesNoSenseWeJustReturnRegularNullPointer() {
        Object voidNull = Defaults.nullValueFor(Void.TYPE);
        assertNull(voidNull);
    }
}
