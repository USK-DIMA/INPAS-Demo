package ru.uskov.dmitry.inpas.demo.rainy.hills;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Dmitry on 11.06.2017.
 */
public class RainyHillsExecutorTest {
    @Test
    public void execute() throws Exception {
        int[] test1 = new int[]{5, 6, 2, 3, 1, 1, 4, 2, 3, 3, 2};
        int expected1 = 10;

        int[] test2 = new int[]{2, 3, 6, 4, 3, 2, 1, 3, 3, 4, 2, 5};
        int expected2 = 18;

        int[] test3 = new int[]{2, 3, 6, 4, 3, 2, 1, 3, 1, 4, 2, 5};
        int expected3 = 20;

        int[] test4 = new int[]{5, 5, 5, 5, 5, 5, 5, 5};
        int expected4 = 0;

        int[] test5 = new int[]{1, 2, 3, 4, 5, 10, 4, 1};
        int expected5 = 0;

        int[] test6 = new int[]{3, 2, 4, 1, 2};
        int expected6 = 2;

        int[] test7 = new int[]{4, 1, 1, 0, 2, 3};
        int expected7 = 8;

        Assert.assertEquals(expected1, RainyHillsExecutor.execute(test1));
        Assert.assertEquals(expected2, RainyHillsExecutor.execute(test2));
        Assert.assertEquals(expected3, RainyHillsExecutor.execute(test3));
        Assert.assertEquals(expected4, RainyHillsExecutor.execute(test4));
        Assert.assertEquals(expected5, RainyHillsExecutor.execute(test5));
        Assert.assertEquals(expected6, RainyHillsExecutor.execute(test6));
        Assert.assertEquals(expected7, RainyHillsExecutor.execute(test7));
    }

}