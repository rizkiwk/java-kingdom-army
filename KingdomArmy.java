package kingdom_army;

import java.io.LineNumberReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;
import java.lang.*;

@SuppressWarnings({"unchecked", "unsafe"})
public class KingdomArmy {
  int row, col;
  char[][] kingdomBlock;
  TreeMap<String, Integer> armies;
  ArrayList<String> contested;

  public static void main(String[] args) throws Exception {
      KingdomArmy p = new KingdomArmy();
      p.kingdomArmy();
  }

  private void kingdomArmy() throws IOException{
      Object[] input = convertDataToArray(new File("kingdom_army/input.txt").getAbsolutePath());

      int T       = (Integer) Integer.valueOf((String) input[0]);
      int point   = 0;
      row         = 0;

      for (int i = 1; i <= T; i++) {
          armies        = new TreeMap<String, Integer>();
          contested     = new ArrayList<String>();
          point++;
          row           = (Integer) Integer.valueOf((String) input[point]);
          point++;
          col           = (Integer) Integer.valueOf((String) input[point]);
          kingdomBlock  = new char[row][col];

          System.out.println("Case "+i+":");

          for (int j = 0; j < kingdomBlock.length; j++) {
              point++;
              kingdomBlock[j] = input[point].toString().toCharArray();
          }

          for (int r = 0; r < row; r++) {
              for (int c = 0; c < col; c++) {
                  showArmyContested(kingdomBlock, r, c);
              }
          }

          for (int c = 0; c < contested.size(); c++) {
              armies.remove(contested.get(c));
          }

          for ( Object key : armies.keySet() ) {
              String arrKey = (String) key;
              System.out.println(arrKey+" "+armies.get(arrKey));
          }

          // Remove duplicate value conested
          contested = removeDuplicateContested(contested);

          if ((contested.size() / 2) > 0) {
              System.out.println("contested "+String.valueOf((contested.size() / 2)));
          }
      }
  }

  void showArmyContested(char kingdomBlock[][], int r, int c) {
    if (Character.isLetter(kingdomBlock[r][c])) {
        String kingdomArea  = String.valueOf(kingdomBlock[r][c]);
        String armyValue    = "";
        Integer armyCount   = 0;

        countArmySameArea(kingdomArea, "", armies);

        // Top army move.
        for (int ii = r - 1 ; ii >= 0; ii--) {
            String indexRow = String.valueOf(kingdomBlock[ii][c]);
            if (indexRow.equalsIgnoreCase("#")) break;
            if (Character.isLetter(kingdomBlock[ii][c])) countArmySameArea(kingdomArea, indexRow, armies);

            // Indexing top => left.
            for (int jj = c - 1; jj + 1 > 0; jj--) {
                String indexCol = String.valueOf(kingdomBlock[ii][jj]);
                if (indexCol.equalsIgnoreCase("#")) break;

                // Indexing top => left => top.
                for (int tlt = ii - 1; tlt + 1 > 0; tlt--) {
                    String indexColTlt = String.valueOf(kingdomBlock[tlt][jj]);
                    if (indexColTlt.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[tlt][jj])) {
                        countArmySameArea(kingdomArea, indexColTlt, armies);
                    }
                }

                // Indexing top => left => bottom.
                for (int tlb = ii + 1; tlb < row; tlb++) {
                    String indexColTlb = String.valueOf(kingdomBlock[tlb][jj]);
                    if (indexColTlb.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[tlb][jj])) {
                        countArmySameArea(kingdomArea, indexColTlb, armies);
                    }
                }
            }

            // Indexing top => right.
            for (int jj = c + 1; jj < col; jj++) {
                String indexCol = String.valueOf(kingdomBlock[ii][jj]);
                if (indexCol.equalsIgnoreCase("#")) break;

                // Indexing top => right => top.
                for (int trt = ii - 1; trt + 1 > 0; trt--) {
                    String indexColTrt = String.valueOf(kingdomBlock[trt][jj]);
                    if (indexColTrt.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[trt][jj])) {
                        countArmySameArea(kingdomArea, indexColTrt, armies);
                    }
                }

                // Indexing top => right => bottom.
                for (int trb = ii + 1; trb < row; trb++) {
                    String indexColTrb = String.valueOf(kingdomBlock[trb][jj]);
                    if (indexColTrb.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[trb][jj])) {
                        countArmySameArea(kingdomArea, indexColTrb, armies);
                    }
                }
            }
        }

        // Bottom army move.
        for (int ii = r + 1; ii < row; ii++) {
            String indexRow = String.valueOf(kingdomBlock[ii][c]);
            if (indexRow.equalsIgnoreCase("#")) break;
            if (Character.isLetter(kingdomBlock[ii][c])) countArmySameArea(kingdomArea, indexRow, armies);

            // Indexing bottom => left.
            for (int jj = c - 1; jj + 1 > 0; jj--) {
                String indexCol = String.valueOf(kingdomBlock[ii][jj]);
                if (indexCol.equalsIgnoreCase("#")) break;

                // Indexing bottom => left => top.
                for (int blt = ii - 1; blt + 1 > 0; blt--) {
                    String indexColBlt = String.valueOf(kingdomBlock[blt][jj]);
                    if (indexColBlt.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[blt][jj])) {
                        countArmySameArea(kingdomArea, indexColBlt, armies);
                    }
                }

                // Indexing bottom => left => bottom.
                for (int blb = ii + 1; blb < row; blb++) {
                    String indexColBlb = String.valueOf(kingdomBlock[blb][jj]);
                    if (indexColBlb.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[blb][jj])) {
                        countArmySameArea(kingdomArea, indexColBlb, armies);
                    }
                }
            }

            // Indexing bottom => right.
            for (int jj = c + 1; jj < col; jj++) {
                String indexCol = String.valueOf(kingdomBlock[ii][jj]);
                if (indexCol.equalsIgnoreCase("#")) break;

                // Indexing bottom => right => top.
                for (int brt = ii - 1; brt + 1 > 0; brt--) {
                    String indexColBrt = String.valueOf(kingdomBlock[brt][jj]);
                    if (indexColBrt.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[brt][jj])) {
                        countArmySameArea(kingdomArea, indexColBrt, armies);
                    }
                }

                // Indexing bottom => right => bottom.
                for (int brb = ii + 1; brb < row; brb++) {
                    String indexColBrb = String.valueOf(kingdomBlock[brb][jj]);
                    if (indexColBrb.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[brb][jj])) {
                        countArmySameArea(kingdomArea, indexColBrb, armies);
                    }
                }
            }
        }

        // Left army move.
        for (int jj = c - 1; jj + 1 > 0; jj--) {
            String indexCol = String.valueOf(kingdomBlock[r][jj]);
            if (indexCol.equalsIgnoreCase("#")) break;
            if (Character.isLetter(kingdomBlock[r][jj])) countArmySameArea(kingdomArea, indexCol, armies);

            // Indexing left => top.
            for (int ii = r - 1; ii + 1 > 0; ii--) {
                String indexRow = String.valueOf(kingdomBlock[ii][jj]);
                if (indexRow.equalsIgnoreCase("#")) break;

                // Indexing left => top => left.
                for (int ltl = jj - 1; ltl + 1 > 0; ltl--) {
                    String indexColLtl = String.valueOf(kingdomBlock[ii][ltl]);
                    if (indexColLtl.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[ii][ltl])) {
                        countArmySameArea(kingdomArea, indexColLtl, armies);
                    }
                }

                // Indexing left => top => right.
                for (int ltr = jj + 1; ltr < col; ltr++) {
                    String indexColLtr = String.valueOf(kingdomBlock[ii][ltr]);
                    if (indexColLtr.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[ii][ltr])) {
                        countArmySameArea(kingdomArea, indexColLtr, armies);
                    }
                }
            }

            // Indexing left => bottom.
            for (int ii = r + 1; ii < row; ii++) {
                String indexRow = String.valueOf(kingdomBlock[ii][jj]);
                if (indexRow.equalsIgnoreCase("#")) break;

                // Indexing left => bottom => left.
                for (int lbl = jj - 1; lbl + 1 > 0; lbl--) {
                    String indexColLbl = String.valueOf(kingdomBlock[ii][lbl]);
                    if (indexColLbl.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[ii][lbl])) {
                        countArmySameArea(kingdomArea, indexColLbl, armies);
                    }
                }

                // Indexing left => bottom => right.
                for (int lbr = jj + 1; lbr < col; lbr++) {
                    String indexColLbr = String.valueOf(kingdomBlock[ii][lbr]);
                    if (indexColLbr.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[ii][lbr])) {
                        countArmySameArea(kingdomArea, indexColLbr, armies);
                    }
                }
            }
        }

        // Right army move.
        for (int jj = c + 1; jj < col; jj++) {
            String indexCol = String.valueOf(kingdomBlock[r][jj]);
            if (indexCol.equalsIgnoreCase("#")) break;
            if (Character.isLetter(kingdomBlock[r][jj])) countArmySameArea(kingdomArea, indexCol, armies);

            // Indexing right => top.
            for (int ii = r - 1; ii + 1 > 0; ii--) {
                String indexRow = String.valueOf(kingdomBlock[ii][jj]);
                if (indexRow.equalsIgnoreCase("#")) break;

                // Indexing right => top => left.
                for (int rtl = jj - 1; rtl + 1 > 0; rtl--) {
                    String indexColRtl = String.valueOf(kingdomBlock[ii][rtl]);
                    if (indexColRtl.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[ii][rtl])) {
                        countArmySameArea(kingdomArea, indexColRtl, armies);
                    }
                }

                // Indexing right => top => right.
                for (int rtr = jj + 1; rtr < col; rtr++) {
                    String indexColRtr = String.valueOf(kingdomBlock[ii][rtr]);
                    if (indexColRtr.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[ii][rtr])) {
                        countArmySameArea(kingdomArea, indexColRtr, armies);
                    }
                }
            }

            // Indexing right => bottom.
            for (int ii = r + 1; ii < row; ii++) {
                String indexRow = String.valueOf(kingdomBlock[ii][jj]);
                if (indexRow.equalsIgnoreCase("#")) break;

                // Indexing right => bottom => left.
                for (int rbl = jj - 1; rbl + 1 > 0; rbl--) {
                    String indexColRbl = String.valueOf(kingdomBlock[ii][rbl]);
                    if (indexColRbl.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[ii][rbl])) {
                        countArmySameArea(kingdomArea, indexColRbl, armies);
                    }
                }

                // Indexing right => bottom => right.
                for (int rbr = jj + 1; rbr < col; rbr++) {
                    String indexColRbr = String.valueOf(kingdomBlock[ii][rbr]);
                    if (indexColRbr.equalsIgnoreCase("#")) break;
                    if (Character.isLetter(kingdomBlock[ii][rbr])) {
                        countArmySameArea(kingdomArea, indexColRbr, armies);
                    }
                }
            }
        }
    }
  }

  void countArmySameArea(String kingdomArea, String indexArea, TreeMap armies) {
    String armyValue    = "";
    int armyKingdom     = 0;
    int armyCount       = 0;

    if (!indexArea.equalsIgnoreCase("")) {
        if (kingdomArea.equalsIgnoreCase(indexArea)) {
            if (armies.containsKey(indexArea)) {
                armyKingdom = (armies.get(indexArea) == null) ? 0 : (Integer) armies.get(indexArea);
                armyCount   = armyKingdom - 1;
                armies.put(indexArea, (armyKingdom - armyCount));
            }
        }
        else if (!kingdomArea.equalsIgnoreCase(indexArea)) {
            if (armies.containsKey(indexArea)) {
                armyKingdom = (armies.get(indexArea) == null) ? 0 : (Integer) armies.get(indexArea);
                armyCount   = armyKingdom - 1;
                armies.put(indexArea, armyCount);
            } else {
                armyKingdom = (armies.get(kingdomArea) == null) ? 0 : (Integer) armies.get(kingdomArea);
                armyCount   = armyKingdom + 1;
                armies.put(kingdomArea, armyCount);
            }
            contested.add(indexArea);
       }
    }
    else {
        if (armies.containsKey(kingdomArea)) {
            armyKingdom = (armies.get(kingdomArea) == null) ? 0 : (Integer) armies.get(kingdomArea);
            armyCount       = armyKingdom + 1;
            armies.put(kingdomArea, armyCount);
        } else {
            armyKingdom = (armies.get(kingdomArea) == null) ? 0 : (Integer) armies.get(kingdomArea);
            armyCount   = armyKingdom + 1;
            armies.put(kingdomArea, armyCount);
        }
    }
  }

  ArrayList<String> removeDuplicateContested(ArrayList<String> contestedList) {
    ArrayList<String> newContested  = new ArrayList<>();
    HashSet<String>   setContested  = new HashSet<>();

    for (String item : contestedList) {
        if (!setContested.contains(item)) {
            newContested.add(item);
            setContested.add(item);
        }
    }
    return newContested;
  }

  Object[] convertDataToArray(String file) throws IOException {
    FileReader fr = null;
    LineNumberReader lnr = null;
    String str;
    int i;

    ArrayList<Object> arrObj  = new ArrayList<Object>();

    try {
      // create new reader
      fr  = new FileReader(file);
      lnr = new LineNumberReader(fr);

      // read lines till the end of the stream
      while((str = lnr.readLine())!=null) {
        i = lnr.getLineNumber();
        arrObj.add(str);
      }
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      if(fr!=null)
        fr.close();
      if(lnr!=null)
        lnr.close();
    }
    return arrObj.toArray();
  }
}
