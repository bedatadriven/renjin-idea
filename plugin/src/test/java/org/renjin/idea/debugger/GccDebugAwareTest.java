package org.renjin.idea.debugger;

import org.junit.Test;

import static org.junit.Assert.*;

public class GccDebugAwareTest {

    @Test
    public void testSource() {
        assertTrue(GccDebugAware.isPossibleGccBridgeSourceFile("dqrls.f"));
        assertTrue(GccDebugAware.isPossibleGccBridgeSourceFile("rnorm.c"));
        assertFalse(GccDebugAware.isPossibleGccBridgeSourceFile("DqrlTest.java"));
    }

}