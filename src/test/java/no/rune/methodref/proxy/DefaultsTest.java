package no.rune.methodref.proxy;

import static no.rune.methodref.proxy.Defaults.nullValueFor;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.Reader;

import org.junit.Test;



public class DefaultsTest {

    @Test
    public void nullValueForAnyObjectReturnsNullPointer() {
        assertNull(nullValueFor(String.class));
        assertNull(nullValueFor(Integer.class));
        assertNull(nullValueFor(Reader.class));
    }

    @Test
    public void nullValueForNonBooleanPrimitivesReturnsZeroValues() {
        int zeroInt = nullValueFor(Integer.TYPE);
        assertThat(zeroInt, is(0));

        long zeroLong = nullValueFor(Long.TYPE);
        assertThat(zeroLong, is(0L));

        double zeroDouble = nullValueFor(Double.TYPE);
        assertThat(zeroDouble, is(0.0));

        float zeroFloat = nullValueFor(Float.TYPE);
        assertThat(zeroFloat, is(0.0f));

        short zeroShort = nullValueFor(Short.TYPE);
        assertThat(zeroShort, is((short)0));

        byte zeroByte = nullValueFor(Byte.TYPE);
        assertThat(zeroByte, is((byte)0));

        char zeroChar = nullValueFor(Character.TYPE);
        assertThat(zeroChar, is('\u0000'));

    }

    @Test
    public void nullValueForBooleanReturnsFalse() {
        boolean falseValue = nullValueFor(Boolean.TYPE);
        assertFalse(falseValue);
    }

    @Test
    public void nullValueForVoidTypeAlthoughMakesNoSenseWeJustReturnRegularNullPointer() {
        Object voidNull = nullValueFor(Void.TYPE);
        assertNull(voidNull);
    }
}
