package ru.vsu.cs.course1.game;

public class RankGiver {
    private static final String[] ranks = new String[]{"S", "A", "B", "C", "D", "E", "F"};
    private static final int[] lvlMoves = new int[]{7, 44, 48, 55, 33, 7, 7, 7, 7, 7};

    public static String takeRank(int moves, int level){
        level -= 1;
        System.out.println(moves + " " + level +  " " + lvlMoves[level]);
        if (moves > lvlMoves[level] + 14){
            return ranks[6];
        }
        if(moves >= lvlMoves[level] + 11){
            return ranks[5];
        }
        if(moves >= lvlMoves[level] + 9){
            return ranks[4];
        }
        if(moves >= lvlMoves[level] + 6){
            return ranks[3];
        }
        if(moves>= lvlMoves[level] + 3){
            return ranks[2];
        }
        if(moves > lvlMoves[level]){
            return ranks[1];
        }
        return ranks[0];
    }
}
