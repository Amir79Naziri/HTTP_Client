public enum ReservedWords
{

    HELP_v1("-h"),
    HELP_V2("--help"),
    METHOD_V1("-M"),
    METHOD_V2("--method"),
    HEADER_V1("-H"),
    HEADER_V2("--headers"),
    SHOW_HEADER_ARG("-i"),
    FOLLOW_REDIRECT("-f"),
    OUTPUT_V1("-O"),
    OUTPUT_V2("--output"),
    SAVE_V1("-S"),
    SAVE_V2("--save"),
    FORM_DATA_V1("-d"),
    FORM_DATA_V2("--data"),
    JSON_V1("-j"),
    JSON_V2("--json"),
    UPLOAD("--upload"),
    LIST("list"),
    FIRE("fire"),
    NAME("--name");


    private String commandString;


    ReservedWords (String commandString)
    {
        this.commandString = commandString;
    }

    public String getCommandString () {
        return commandString;
    }
}
