package jb;

import java.util.ArrayList;

public class RGBSolutionImproved extends RGBTaskSolution {

  //O(n^2) but slightly more efficient
  @Override
  @SuppressWarnings("unchecked")
  public int calcMinDist(int[] rgb) {
    int n = rgb.length;
    int[] dist = new int[n];

    dist[0] = 0;
    for (int i = 1; i < n; i++) {
      dist[i] = -1;
    }

    ArrayList<Integer>[] prevColors = new ArrayList[3];
    for (int i = 0; i < 3; i++) {
      prevColors[i] = new ArrayList<>(n);
    }

    prevColors[rgb[0]].add(0);

    for (int i = 1; i < n; i++) {
      int cur = rgb[i];
      int prevColor = (cur + 2) % 3;
      int min = -1;

      ArrayList<Integer> prevPos = prevColors[prevColor];
      for (int j = 0; j < prevPos.size(); j++) {
        int pos = prevPos.get(j);
        int d1 = dist[pos];
        int d2 = (i - pos) * (i - pos);
        if (d1 >= 0) {
          if (d1 + d2 < min || min < 0) {
            min = d1 + d2;
          }
        }
      }

      dist[i] = min;
      prevColors[cur].add(i);
    }

    return dist[n - 1];
  }
}
