package ru.vsu.cs.course1.game;

import java.util.Arrays;

public enum Skins {
    SKIN_JUST_BLUE,
    SKIN_UKRAINE,
    SKIN_CHINA,
    SKIN_SMILE,
    SKIN_FRIED_EGG,
    SKIN_PIG,
    SKIN_24_MARK,
    SKIN_45_MARK;

    private static final Skins[] skins = Skins.values();

    public static Skins getSkin(int i){
        System.out.println(Arrays.toString(skins));
        for (int j = 0; j < skins.length; j++) {
            if (skins[j] == Game.getSkin()){
                if (j + i == skins.length){
                    return skins[0];
                }
                else if (j + i == -1){
                    return skins[skins.length - 1];
                }
                return skins[j + i];
            }
        }
        return Game.getSkin();
    }



}
