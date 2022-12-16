package model;

import java.awt.*;

public final class FontSet {
    public static final Font fontType = new Font("Roboto", Font.BOLD, 28),
            fontLeaderboard = new Font("Roboto", Font.PLAIN, 18),
            buttonFont = fontType.deriveFont(22f),
            infoFont = fontType.deriveFont(21f),
            warningFont = fontType.deriveFont(16f),
            mediumFont = fontType.deriveFont(30f),
            scorFont = fontLeaderboard.deriveFont(20f),
            tileFont = fontType.deriveFont(36f),
            leaderboardTitle = fontType.deriveFont(48f),
            endGameTitle = fontType.deriveFont(60f);
}