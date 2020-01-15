package com.zhangyc.note.volley;

import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VolleyRequestManagerTest {

    private VolleyRequestManager volleyRequestManager;

    @Before
    public void setUp() throws Exception {
        volleyRequestManager = new VolleyRequestManager(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void testImageRequest() {
        volleyRequestManager.loadImage();
    }
}