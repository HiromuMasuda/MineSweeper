import java.util.Random;

import jp.ne.kuramae.torix.lecture.ms.core.Cell;
import jp.ne.kuramae.torix.lecture.ms.core.MineSweeper;
import jp.ne.kuramae.torix.lecture.ms.core.Player;

public class Flag {

  private static boolean[][] flagList;
  private int totalFlagCount;

  public Flag(int fieldWidth, int fieldHeight){
    flagList = new boolean[fieldWidth][fieldHeight];
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
