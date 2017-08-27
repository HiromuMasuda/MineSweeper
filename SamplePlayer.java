import java.util.Random;

import jp.ne.kuramae.torix.lecture.ms.core.Cell;
import jp.ne.kuramae.torix.lecture.ms.core.MineSweeper;
import jp.ne.kuramae.torix.lecture.ms.core.Player;

public class SamplePlayer extends Player {

  static int N = 100;
  static float winCount = 0;
  protected static Flag flag;

  static public void main(String[] args){
    Player player = new SamplePlayer();

    MineSweeper mineSweeper = new MineSweeper(0);
    mineSweeper.setRandomSeed(0);
    //MineSweeper mineSweeper = new MineSweeper(lv); //lv=0,1,2でレベルを指定して作成
    //MineSweeper mineSweeper = new MineSweeper(width, height, bombNum);

    for(int i = 0; i < N; i++){
      mineSweeper.start(player);
    }

    System.out.println("勝率: " + winCount/N*100 + "%");
    // System.out.println("勝数: " + winCount + "回");
  }

  @Override
  protected void start() {
    flag = new Flag(getHeight(), getWidth());

    LOOP:while(!isClear() && !isGameOver()){
      for(int y = 0; y < getHeight(); y++){
        for(int x = 0; x < getWidth(); x++){
          int bombNum = getCell(x, y);

          // check
          setFlagOrOpenCellAround(x, y);

          // Open cell
          if(bombNum < 0 && !flag.isSet(x, y)){
            open(x, y);

            if(checkIsClear()){
              winCount += 1;
            }

            continue LOOP;
          }
        }
      }
    }
  }

  // 本当はisClear()を使いたい、その場しのぎ
  private boolean checkIsClear(){
    return getBombNum() == getMaskedCellCount() - 1 && getBombNum() == flag.getTotalFlagCount();
  }

  private void setFlagOrOpenCellAround(int x, int y){
    for(int X = x-1; X <= x+1; X++){
      for(int Y = y-1; Y <= y+1; Y++){
        if(isPosInField(X, Y) && getCell(X, Y) > 0){
          // x, yの周りの場所でopen済みのマス、つまり周囲の爆弾数が書いてあるマス

          if(getCell(X, Y) == getUnOpenedCellCountAround(X, Y)){
            setFlagAround(X, Y);
          }

          // if(getCell(X, Y) == getFlagCountAround(X, Y)){
          //   System.out.println("fuga x:" + x + ", y:" + y);
          //   openAround(X, Y);
          // }

        }
      }
    }
  }

  private boolean canSetFlagMore(){
    return flag.getTotalFlagCount() - getBombNum() < 0;
  }

  private void setFlagAround(int x, int y){
    for(int X = x-1; X <= x+1; X++){
      for(int Y = y-1; Y <= y+1; Y++){
        if(isPosInField(X, Y) && getCell(X, Y) < 0 && canSetFlagMore() && !flag.isSet(X, Y)){
          flag.set(X, Y);
        }
      }
    }
  }

  private void openAround(int x, int y){
    for(int X = x-1; X <= x+1; X++){
      for(int Y = y-1; Y <= y+1; Y++){
        if(isPosInField(X, Y) && getCell(X, Y) < 0){
          open(X, Y);
        }
      }
    }
  }

  private int getUnOpenedCellCountAround(int x, int y){
    int unOpenedCellCount = 0;

    for(int X = x-1; X <= x+1; X++){
      for(int Y = y-1; Y <= y+1; Y++){
        if(isPosInField(X, Y) && getCell(X, Y) < 0){
          unOpenedCellCount += 1;
        }
      }
    }

    return unOpenedCellCount;
  }

  private int getFlagCountAround(int x, int y){
    int flagCount = 0;

    for(int X = x-1; X <= x+1; X++){
      for(int Y = y-1; Y <= y+1; Y++){
        if(isPosInField(X, Y) && flag.isSet(X, Y)){
          flagCount += 1;
        }
      }
    }

    return flagCount;
  }

  private boolean isPosInField(int x, int y){
    return 0 <= x && x < getWidth() && 0 <= y && y < getHeight();
  }
}
