package jb;

public class RGBSolutionSimple extends RGBTaskSolution {

  //O(n^2) solution
  @Override
  public int calcMinDist(int[] rgb) {
    int n = rgb.length;
    int[] dist = new int[n];

    dist[0] = 0;
    for (int i = 1; i < n; i++) {
      dist[i] = -1;
    }

    for (int i = 1; i < n; i++) {
      int cur = rgb[i];
      int prevColor = (cur + 2) % 3;
      int min = -1;
      for (int j = 0; j < i; j++) {
        if (rgb[j] == prevColor) {
          int d1 = dist[j];
          int d2 = (i - j) * (i - j);
          if (d1 >= 0) {
            if (d1 + d2 < min || min < 0) {
              min = d1 + d2;
            }
          }
        }
      }
      dist[i] = min;
    }

    return dist[n - 1];
  }
}
