package helpers;

import enums.*;

public class ParseHelper {

    public static int tryParseNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    public static MainMenuOptions parseMainMenuOptions(String input) {

        MainMenuOptions[] values = MainMenuOptions.values();
        int parsedNumber = tryParseNumber(input);

        if (parsedNumber < 0 || values.length <= parsedNumber) {
            return MainMenuOptions.None;
        }

        return values[parsedNumber];
    }

    public static Difficulty parseDifficulty(String input) {

        Difficulty[] values = Difficulty.values();
        int parsedNumber = tryParseNumber(input);

        if (parsedNumber < 0 || values.length <= parsedNumber) {
            return Difficulty.None;
        }

        return values[parsedNumber];
    }

    public static Category parseCategory(String input) {

        Category[] values = Category.values();
        int parsedNumber = tryParseNumber(input);

        if (parsedNumber < 0 || values.length <= parsedNumber) {
            return Category.None;
        }

        return values[parsedNumber];
    }
}
