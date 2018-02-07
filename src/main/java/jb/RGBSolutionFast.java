package jb;

import java.util.ArrayList;

public class RGBSolutionFast extends RGBTaskSolution {

  //almost O(n) but in fact it is still O(n^2)
  @Override
  @SuppressWarnings("unchecked")
  public int calcMinDist(int[] rgb) {
    int n = rgb.length;
    int[] dist = new int[n];
    int firstColor = rgb[0];

    dist[0] = 0;
    for (int i = 1; i < n; i++) {
      dist[i] = -1;
    }

    ArrayList<Integer>[] prevColors = new ArrayList[3];
    for (int i = 0; i < 3; i++) {
      prevColors[i] = new ArrayList<>(n);
    }

    prevColors[firstColor].add(0);

    int[] best = new int[3];
    best[firstColor] = 0;

    for (int i = 1; i < n; i++) {
      int cur = rgb[i];
      int prevColor = (cur + 2) % 3;
      int min = -1;

      ArrayList<Integer> prevPos = prevColors[prevColor];
      if (prevPos.size() > 0) {
        int bestPos = best[prevColor];
        int pos = prevPos.get(bestPos);
        int d1 = dist[pos];
        int d2 = (i - pos) * (i - pos);
        min = d1 + d2;

        for (int j = bestPos + 1 ; j < prevPos.size(); j++) {
          pos = prevPos.get(j);
          d1 = dist[pos];
          d2 = (i - pos) * (i - pos);
          int d = d1 + d2;
          if (d <= min) { //<=
            min = d;
            bestPos = j;
          }
        }

        best[prevColor] = bestPos;
      }

      if (min > 0) {
        dist[i] = min;
        prevColors[cur].add(i);
      }
    }

    return dist[n - 1];
  }
}
