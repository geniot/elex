package io.github.geniot.elex.tools.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

public class TagTest {
    @Test
    public void tagCompareTest() {
        TreeSet<Tag> set = new TreeSet<>();
        set.add(new Tag("[ref]"));
        set.add(new Tag("[com]"));
        Assertions.assertEquals("com", set.toArray(new Tag[set.size()])[0].name);
        Assertions.assertEquals("ref", set.toArray(new Tag[set.size()])[1].name);
    }
}
