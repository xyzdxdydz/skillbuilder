package nameerror.skillbuilder;

import net.md_5.bungee.api.ChatColor;

public class Verbose {

    private static final String announce = "Announce";
    private static final String success = "success";
    private static final String warning = "warning";
    private static final String error = "error";
    private static final String info = "default";

    public static String generatePrefixChat(String word, String mode) {
        String sign;
        ChatColor colorMode;
        switch (mode) {
            case success:
                sign = "✓";
                colorMode = ChatColor.GREEN;
                break;
            case warning:
                sign = "!!";
                colorMode = ChatColor.of("#FFAD54");
                break;
            case error:
                sign = "X";
                colorMode = ChatColor.RED;
                break;
            default:
                sign = "..";
                colorMode = ChatColor.of("#FFFF8B");
        }

        return ChatColor.of("#FFFF33") + "[" + word + "]" + "[" + colorMode + sign + ChatColor.of("#FFFF33") + "] : " +
                colorMode;
    }

    public static String commandNotExist() {
        return generatePrefixChat(announce, warning) + "The command doesn't exist!";
    }

    public static String invalidCommandSyntax() {
        return generatePrefixChat(announce, warning) + "Invalid Syntax. Type: \"/skillbuilder help\" for help.";
    }

    public static String configReloadSuccess() {
        return generatePrefixChat(announce, success) + "All config files has been reloaded!";
    }

    public static String configReloadFail(String path) {
        return generatePrefixChat(announce, error) + "Error occoured while reloading \"" + path + "\" please recheck syntax.";
    }

    public static String skillSectionNotFound(String path) {
        return generatePrefixChat(announce, error) + "Skill doesn't exist, maybe the internal server problem or " +
                "check spelling in " + path;
    }

    public static String skillSequenceError(String skillName, String path) {
        return generatePrefixChat(announce, error) + "Cannot find Sequence section in" + "or the section." +
                "is blank, please recheck in " + path;
    }

    public static String skillLaunch(String skillName) {
        return generatePrefixChat(announce, info) + "Launching skill "  + "\"" + ChatColor.ITALIC + skillName + "\"";
    }

    public static String skillLaunchSuccess() {
        return generatePrefixChat(announce, success) + "Launching success!";
    }

    public static String objectSectionNotFound(String objectName, String path) {
        return generatePrefixChat(announce, error) + "Object" + "\"" + objectName + "\"" + "doesn't exist, maybe the " +
                "internal server problem or check spelling in " + path;
    }

    public static String objectTypeNotFound(String objectName, String path) {
        return generatePrefixChat(announce, error) + "Cannot find ObjectType at" + "\"" + objectName + "\"" +
                "or the section is blank, please recheck in " + path;
    }

    public static String featureUnderMaintenance(String featureName) {
        return generatePrefixChat(announce, error) + "Feature '" + featureName + "' is under maintenance.";
    }

    public static String featureWarnning(String featureName, String msg) {
        return generatePrefixChat(warning, warning) + "Feature '" + featureName + "': " + msg;
    }

    public static String commandArgumentNotFound() {
        return generatePrefixChat(announce, error) + "That argument doesn't exists";
    }
}
