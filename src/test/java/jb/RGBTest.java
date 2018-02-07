package jb;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class RGBTest {

  private void simpleTest(RGBTaskSolution solution) {
    int rgb[] = new int[] {0, 0, 0};
    Assert.assertEquals(-1, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1};
    Assert.assertEquals(1, solution.calcMinDist(rgb));

    rgb = new int[] {0, 0, 1};
    Assert.assertEquals(4, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 2};
    Assert.assertEquals(2, solution.calcMinDist(rgb));

    rgb = new int[] {0, 0, 1, 1, 2, 2};
    Assert.assertEquals(2 * 2 + 3 * 3, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 2};
    Assert.assertEquals(8, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 1, 1, 2};
    Assert.assertEquals(3*3 + 3*3, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 1, 1, 0, 2};
    Assert.assertEquals(3*3 + 4*4, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 1, 1, 0, 0, 2};
    Assert.assertEquals(4*4 + 4*4, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 1, 1, 0, 0, 0, 2};
    Assert.assertEquals(4*4 + 5*5, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 2};
    Assert.assertEquals(5*5 + 5*5, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 2};
    Assert.assertEquals(5*5 + 6*6, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 2};
    Assert.assertEquals(5*5 + 7*7, solution.calcMinDist(rgb));

    rgb = new int[] {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 2};
    Assert.assertEquals(5*5 + 8*8, solution.calcMinDist(rgb));

    rgb = new int[] {1,2,0,2,2,1,2,0};
    Assert.assertEquals(13, solution.calcMinDist(rgb));
  }

  @Test
  public void simpleTest() {
    simpleTest(new RGBSolutionSimple());
    simpleTest(new RGBSolutionImproved());
    simpleTest(new RGBSolutionFast());
  }

  @Test
  public void preciseTest() {
    for (int i = 1; i < 15; i++) {
      preciseTest(i);
    }
  }

  private void preciseTest(int n) {
    RGBTaskSolution simple = new RGBSolutionSimple();
    RGBTaskSolution improved = new RGBSolutionImproved();
    RGBTaskSolution fast = new RGBSolutionFast();

    int[] rgb = new int[n];
    boolean exit = false;
    while (!exit) {
      //add
      for (int i = 0; ; i++) {
        rgb[i] += 1;
        boolean override = rgb[i] / 3 > 0;
        rgb[i] %= 3;
        if (!override) {
          break;
        } else if (i + 1 > n - 1) {
          exit = true;
          break;
        }
      }

      int ans1 = simple.calcMinDist(rgb);
      int ans2 = improved.calcMinDist(rgb);
      int ans3 = fast.calcMinDist(rgb);


      if (ans1 != ans2 || ans2 != ans3) {
        print(rgb);
        System.out.println("simple:  ans = " + ans1);
        System.out.println("improved:  ans = " + ans2);
        System.out.println("fast:  ans = " + ans3);
      }
      Assert.assertEquals(ans1, ans2);
      Assert.assertEquals(ans2, ans3);
    }
  }

  private void print(int[] rgb) {
    for (int i = 0; i < rgb.length; i++) {
      System.out.print(rgb[i]);
    }
    System.out.println();
  }

  @Test
  public void randomTest() {
    Random rnd = new Random(0x123213);
    RGBTaskSolution simple = new RGBSolutionSimple();
    RGBTaskSolution improved = new RGBSolutionImproved();
    RGBTaskSolution fast = new RGBSolutionFast();

    for (int n = 1000; n < 1e5; n += 1000) {
      int rgb[] = generateRGB(n, rnd);
      long time0 = System.currentTimeMillis();
      int ans1 = simple.calcMinDist(rgb);
      long time1 = System.currentTimeMillis();
      int ans2 = improved.calcMinDist(rgb);
      long time2 = System.currentTimeMillis();
      int ans3 = fast.calcMinDist(rgb);
      long time3 = System.currentTimeMillis();

      System.out.println("n: " + n);
      System.out.println("simple:  ans = " + ans1 + "; time = " + (time1 - time0) / 1000.0);
      System.out.println("improved:  ans = " + ans2 + "; time = " + (time2 - time1) / 1000.0);
      System.out.println("fast:  ans = " + ans3 + "; time = " + (time3 - time2) / 1000.0);
      System.out.println();
    }
  }

  @Test
  public void fastSolutionTest() {
    int n = 25_000_000;
    Random rnd = new Random(0x123213 * n);
    RGBTaskSolution fast = new RGBSolutionFast();
    int rgb[] = generateRGB(n, rnd);
    long time0 = System.currentTimeMillis();
    int ans = fast.calcMinDist(rgb);
    long time1 = System.currentTimeMillis();
    System.out.println("n: " + n);
    System.out.println("fast:  ans = " + ans + "; time = " + (time1 - time0) / 1000.0);
  }

  @Test
  public void simpleSolutionTest() {
    Random rnd = new Random(0x123213);
    RGBTaskSolution simple = new RGBSolutionSimple();
    int n = 25_000;
    int rgb[] = generateRGB(n, rnd);
    long time0 = System.currentTimeMillis();
    int ans = simple.calcMinDist(rgb);
    long time1 = System.currentTimeMillis();
    System.out.println("n: " + n);
    System.out.println("simple:  ans = " + ans + "; time = " + (time1 - time0) / 1000.0);
  }

  @Test
  public void improvedSolutionTest() {
    Random rnd = new Random(0x123213);
    RGBTaskSolution improved = new RGBSolutionImproved();
    int n = 50_000;
    int rgb[] = generateRGB(n, rnd);
    long time0 = System.currentTimeMillis();
    int ans = improved.calcMinDist(rgb);
    long time1 = System.currentTimeMillis();
    System.out.println("n: " + n);
    System.out.println("improved:  ans = " + ans + "; time = " + (time1 - time0) / 1000.0);
  }

  private int[] generateRGB(int n, Random rnd) {
    int[] rgb = new int[n];
    for (int i = 0; i < n; i++) {
      rgb[i] = rnd.nextInt(3);
    }
    return rgb;
  }
}
