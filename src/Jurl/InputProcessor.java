package Jurl;

import java.util.*;

/**
 * this class represents a processor for parsing input by user
 *
 * @author Amir Naziri
 */
public class InputProcessor
{
    private String[] commands; // parses of input
    private TreeMap<ReservedWord,ArrayList<String>> tasks; // map of reserveWords to
                                                           // their arguments
    private String url; // url
    private int inputType; // type of input  1 means line without url
                           // 2 means line with url

    /**
     * gets a new line and process on it
     */
    protected void getLine ()
    {
        tasks = new TreeMap<> ();
        url = null;

        Scanner systemInput = new Scanner (System.in);
        commands = systemInput.nextLine ().trim ().
                replaceAll ("\\s+"," ").trim ().split (" ");
        process ();
    }

    /**
     * process commands
     */
    private void process ()
    {
        if (commands.length < 2)
        {
            System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
            getLine ();
            return;
        }
        if (!(commands[0].equals ("jurl")))
        {
            System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
            getLine ();
            return;
        }

        ReservedWord reservedWordStart;
        if ((reservedWordStart = isReserveWord (commands[1])) != null)
        {
            if (reservedWordStart != ReservedWord.FIRE && reservedWordStart != ReservedWord.HELP_V2
            && reservedWordStart != ReservedWord.LIST && reservedWordStart != ReservedWord.CLOSE)
            {
                System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
                getLine ();
                return;
            }
            ArrayList<String> args = findArgumentForReserveWord (reservedWordStart,2);
            if (args == null)
            {
                System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
                getLine ();
                return;
            }
            tasks.put (reservedWordStart,args);
            inputType = 1;
        }
        else
        {
            url = commands[1];
            inputType = 2;
            for (int i = 2; i < commands.length; i++)
            {
                ReservedWord reservedWord;
                if ((reservedWord = isReserveWord (commands[i])) != null)
                {
                    if (reservedWord != ReservedWord.FIRE && reservedWord != ReservedWord.LIST &&
                            reservedWord != ReservedWord.CLOSE)
                    {
                        ArrayList<String> args = findArgumentForReserveWord
                                (reservedWord,i + 1);
                        if (args == null)
                        {
                            System.out.println
                                    ("jurl: try 'jurl --help' / 'jurl -h' for more information");
                            getLine ();
                            return;
                        }

                        tasks.put (reservedWord,args);
                    }

                }
            }
        }
    }

    /**
     * is input reserve word
     * @param command input command
     * @return if is reserveWord returns ReserveWord  else null
     */
    private ReservedWord isReserveWord (String command)
    {
        if (command == null)
            return null;
        else if (command.equals (ReservedWord.HELP_v1.getCommandString ()) ||
                 command.equals (ReservedWord.HELP_V2.getCommandString ()))
            return ReservedWord.HELP_V2;

        else if (command.equals (ReservedWord.HEADER_V1.getCommandString ()) ||
                 command.equals (ReservedWord.HEADER_V2.getCommandString ()))
            return ReservedWord.HEADER_V2;

        else if (command.equals (ReservedWord.METHOD_V1.getCommandString ()) ||
                command.equals (ReservedWord.METHOD_V2.getCommandString ()))
            return ReservedWord.METHOD_V2;

        else if (command.equals (ReservedWord.FIRE.getCommandString ()))
            return ReservedWord.FIRE;

        else if (command.equals (ReservedWord.FOLLOW_REDIRECT.getCommandString ()))
            return ReservedWord.FOLLOW_REDIRECT;

        else if (command.equals (ReservedWord.FORM_DATA_V1.getCommandString ()) ||
                command.equals (ReservedWord.FORM_DATA_V2.getCommandString ()))
            return ReservedWord.FORM_DATA_V2;

        else if (command.equals (ReservedWord.SHOW_HEADER_ARG_V2.getCommandString ()) ||
                command.equals (ReservedWord.SHOW_HEADER_ARG_v1.getCommandString ()))
            return ReservedWord.SHOW_HEADER_ARG_V2;

        else if (command.equals (ReservedWord.LIST.getCommandString ()))
            return ReservedWord.LIST;

        else if (command.equals (ReservedWord.JSON_V1.getCommandString ()) ||
                command.equals (ReservedWord.JSON_V2.getCommandString ()))
            return ReservedWord.JSON_V2;

        else if (command.equals (ReservedWord.OUTPUT_V1.getCommandString ()) ||
                command.equals (ReservedWord.OUTPUT_V2.getCommandString ()))
            return ReservedWord.OUTPUT_V2;

        else if (command.equals (ReservedWord.SAVE_V1.getCommandString ()) ||
                command.equals (ReservedWord.SAVE_V2.getCommandString ()))
            return ReservedWord.SAVE_V2;

        else if (command.equals (ReservedWord.UPLOAD.getCommandString ()))
            return ReservedWord.UPLOAD;

        else if (command.equals (ReservedWord.NAME.getCommandString ()))
            return ReservedWord.NAME;

        else if (command.equals (ReservedWord.CLOSE.getCommandString ()))
            return ReservedWord.CLOSE;

        else if (command.equals (ReservedWord.QUERY.getCommandString ()))
            return ReservedWord.QUERY;

        else if (command.equals (ReservedWord.FORM_DATA_ENCODED.getCommandString ()))
            return ReservedWord.FORM_DATA_ENCODED;
        else
            return null;
    }


    private ArrayList<String> findArgumentForReserveWord (ReservedWord reservedWord,
                                                          int nextIndex)
    {
        if (nextIndex < 0)
            throw new IndexOutOfBoundsException ("index should be 0 to " + commands.length);
        ArrayList<String> args = new ArrayList<> ();

        switch (reservedWord)
        {
            case UPLOAD:
            case JSON_V2:
            case NAME:
            case HEADER_V2:
            case QUERY:
            case METHOD_V2:
            case FORM_DATA_ENCODED:
            case FORM_DATA_V2:
                if (nextIndex < commands.length && isReserveWord (commands[nextIndex]) == null)
                    args.add (commands[nextIndex]);
                else
                {
                    return null;
                }
                break;
            case OUTPUT_V2:
                if (nextIndex < commands.length && isReserveWord (commands[nextIndex]) == null)
                    args.add (commands[nextIndex]);
                break;
            case FIRE:
                int i = nextIndex;
                while (i < commands.length)
                {
                    if (isReserveWord (commands[i]) == null)
                    {
                        args.add (commands[i]);
                        i++;
                    } else
                        break;
                }
                if (args.size () <= 0) {
                    return null;
                }
        }
        return args;
    }

    protected TreeMap<ReservedWord, ArrayList<String>> getTasks () {
        return tasks;
    }


    protected String getUrl () {
        return url;
    }


    protected int getInputType () {
        return inputType;
    }
}
