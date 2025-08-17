package com.watayouxiang.db;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.watayouxiang.db.test", appContext.getPackageName());
    }

    public class Original {
        public String name;
        public long id;
    }

    public class Dest {
        private String name;
        private long id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Dest{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }

    @Test
    public void copyBean() {
        Original original = new Original();
        original.id = 3131;
        original.name = "hedeidje";

        Dest dest = new Dest();

        Dest dest1 = modelA2B(original, dest.getClass());
        System.out.println(dest1);

    }

    public static <A, B> B modelA2B(A modelA, Class<B> bClass) {
        try {
            Gson gson = new Gson();
            String gsonA = gson.toJson(modelA);
            return gson.fromJson(gsonA, bClass);
        } catch (Exception e) {
            return null;
        }
    }
}