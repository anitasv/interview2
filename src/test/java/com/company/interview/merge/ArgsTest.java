package com.company.interview.merge;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by anita on 30-May-16.
 */
public class ArgsTest {

    @Test
    public void testArgParse() {
        String[] args = {
                "--model=m",
                "--dest=d",
                "--overwrite",
                "--skip=false"
        };
        Args parsed = Args.parse(args);
        assertEquals("m", parsed.getString("model", "p"));
        assertEquals("q", parsed.getString("model2", "q" ));
        assertEquals(true, parsed.getBoolean("overwrite", false ));
        assertEquals(false, parsed.getBoolean("skip", true ));
    }
}
