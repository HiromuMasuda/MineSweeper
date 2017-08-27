import java.util.Random;

import jp.ne.kuramae.torix.lecture.ms.core.Cell;
import jp.ne.kuramae.torix.lecture.ms.core.MineSweeper;
import jp.ne.kuramae.torix.lecture.ms.core.Player;

public class Flag {

  int width = 9;
  int height = 9;
  private static boolean[][] flagList;
  private int totalFlagCount;

  public Flag(){
    flagList = new boolean[width][height];
    totalFlagCount = 0;
  }

  public int getTotalFlagCount(){
    return totalFlagCount;
  }

  public void set(int x, int y){
    if(!flagList[x][y]){
      flagList[x][y] = true;
      totalFlagCount += 1;
    }
  }

  public boolean isSet(int x, int y){
    return flagList[x][y];
  }
}
