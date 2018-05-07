/*
 * (C) Copyright 2018-2018, by Kirill Vishnyakov and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */

package org.jgrapht.alg.tour;

import org.jgrapht.alg.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParberryKnightTourTest {

    private ParberryKnightTour par;
    
    private boolean checkMove(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 2) || (Math.abs(x1  - x2) == 2 && Math.abs(y1 - y2) == 1);
    }

    private boolean checkCorrectnessParberry(List<Pair<Integer, Integer>> list, int n, int m) {
        if (n * m != list.size()) {
            return false;
        }

        if (!(Math.abs(list.get(0).getFirst() - list.get(list.size() - 1).getFirst()) == 1 && Math.abs(list.get(0).getSecond() - list.get(list.size() - 1).getSecond()) == 2
                || Math.abs(list.get(0).getFirst() - list.get(list.size() - 1).getFirst()) == 2 && Math.abs(list.get(0).getSecond() - list.get(list.size() - 1).getSecond()) == 1)) {
            return false;
        }

        boolean[][] used = new boolean[n][m];
        used[list.get(0).getFirst()][list.get(0).getSecond()] = true;

        for (int i = 1; i < list.size(); i++) {
            if (!checkMove(list.get(i).getFirst(), list.get(i).getSecond(), list.get(i - 1).getFirst(), list.get(i - 1).getSecond()) || used[list.get(i).getFirst()][list.get(i).getSecond()]) {
                return false;
            }
            used[list.get(i).getFirst()][list.get(i).getSecond()] = true;
        }
        return true;
    }

    @Test
    public void testParberry64x64() {
        par = new ParberryKnightTour(64, 64);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 64, 64));
    }

    @Test
    public void testParberry128x128() {
        par = new ParberryKnightTour(128, 128);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 128, 128));
    }

    @Test
    public void testParberry12x12() {
        par = new ParberryKnightTour(12, 12);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 12, 12));
    }

    @Test
    public void testParberry8x8() {
        par = new ParberryKnightTour(8, 8);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 8, 8));
    }

    @Test
    public void testParberry14x14() {
        par = new ParberryKnightTour(14, 14);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 14, 14));
    }

    @Test
    public void testParberry38x38() {
        par = new ParberryKnightTour(38, 38);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 38, 38));
    }

    @Test
    public void testParberry70x72() {
        par = new ParberryKnightTour(70, 72);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 70, 72));
    }

    @Test
    public void testParberry14x16() {
        par = new ParberryKnightTour(14, 16);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 14, 16));
    }

    @Test
    public void testParberry24x26() {
        par = new ParberryKnightTour(24, 26);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 24, 26));
    }

    @Test
    public void testParberry78x80() {
        par = new ParberryKnightTour(78, 80);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 78, 80));
    }

    @Test
    public void testParberry140x142() {
        par = new ParberryKnightTour(140, 142);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 140, 142));
    }

    @Test
    public void testParberry282x284() {
        par = new ParberryKnightTour(282, 284);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 282, 284));
    }

    @Test
    public void testParberry696x698() {
        par = new ParberryKnightTour(696, 698);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 696, 698));
    }

    @Test
    public void testParberry150x150() {
        par = new ParberryKnightTour(150, 150);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 150, 150));
    }

    @Test
    public void testParberry76x76() {
        par = new ParberryKnightTour(76, 76);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 76, 76));
    }

    @Test
    public void testParberry34x36() {
        par = new ParberryKnightTour(34, 36);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 34, 36));
    }

    @Test
    public void testParberry340x342() {
        par = new ParberryKnightTour(340, 342);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 340, 342));
    }

    @Test
    public void testParberry6x6() {
        par = new ParberryKnightTour(6, 6);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 6, 6));
    }

    @Test
    public void testParberry6x8() {
        par = new ParberryKnightTour(6, 8);
        assertTrue(checkCorrectnessParberry(par.getTour().toArrayList(), 6, 8));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testParberryIncorrectBoardConf1() {
        par = new ParberryKnightTour(2, 2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testParberryIncorrectBoardConf2() {
        par = new ParberryKnightTour(21, 22);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testParberryIncorrectBoardConf3() {
        par = new ParberryKnightTour(73, 73);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testParberryIncorrectBoardConf4() {
        par = new ParberryKnightTour(-20, 20);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testParberryIncorrectBoardConf5() {
        par = new ParberryKnightTour(40, 44);
    }

    @Test
    public void parberryDoubleInvocationToArrayList() {
        par = new ParberryKnightTour(48, 50);
        KnightTour cont = par.getTour();
        List<Pair<Integer, Integer>> arr1 = cont.toArrayList();
        List<Pair<Integer, Integer>> arr2 = cont.toArrayList();
        assertEquals(arr1.size(), arr2.size());
    }

    @Test
    public void parberryDoubleInvocationToArrayListAndGenerateTour() {
        par = new ParberryKnightTour(40, 40);
        KnightTour cont = par.getTour();
        List<Pair<Integer, Integer>> arr1 = cont.toArrayList();
        List<Pair<Integer, Integer>> arr2 = cont.toArrayList();
        assertEquals(arr1.size(), arr2.size());
        cont = par.getTour();
        arr1 = cont.toArrayList();
        arr2 = cont.toArrayList();
        assertEquals(arr1.size(), arr2.size());
    }
}
