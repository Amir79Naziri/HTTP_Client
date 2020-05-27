package Jurl;

public enum ReservedWord
{

    HELP_v1("-h"),
    HELP_V2("--help"),
    METHOD_V1("-M"),
    METHOD_V2("--method"),
    HEADER_V1("-H"),
    HEADER_V2("--headers"),
    SHOW_HEADER_ARG_V2 ("-i"),
    SHOW_HEADER_ARG_v1 ("--include"),
    FOLLOW_REDIRECT("-f"),
    OUTPUT_V1("-O"),
    OUTPUT_V2("--output"),
    SAVE_V1("-S"),
    SAVE_V2("--save"),
    FORM_DATA_V1("-d"),
    FORM_DATA_V2("--data"),
    FORM_DATA_ENCODED ("--dataEncoded"),
    JSON_V1("-j"),
    JSON_V2("--json"),
    UPLOAD("--upload"),
    LIST("list"),
    FIRE("fire"),
    NAME("--name"),
    CLOSE ("--close"),
    QUERY ("-Q");


    private String commandString;


    ReservedWord (String commandString)
    {
        this.commandString = commandString;
    }

    public String getCommandString () {
        return commandString;
    }
}
