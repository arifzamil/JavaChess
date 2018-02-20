package notationConverter;

public class Converter {

    public int[] notationToCoord(String notation) {
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        char[] brokenNotation = notation.toCharArray();
        int[] boardCoord = new int[2];
        for (int i = 0; i < letters.length; i++) {
            if (brokenNotation[0] == letters[i]) {
                boardCoord[1] = i;
                break;
            }
        }
        boardCoord[0] = 8 - Character.getNumericValue(brokenNotation[1]);
        return boardCoord;
    }

    public String coordToNotation(int rank, int file) {
        String letters = "abcdefgh";
        String num = String.valueOf(8 - rank);
        String letter = letters.substring(file, file + 1);
        return letter + num;
    }
}
