import java.util.*;

public class InputProcessor
{
    private Scanner systemInput;
    private String[] commands;
    private TreeMap<ReservedWord,ArrayList<String>> tasks;
    private String url;
    private int inputType;


    public InputProcessor ()
    {
        systemInput = new Scanner (System.in);
    }


    public void getLine ()
    {
        commands = systemInput.nextLine ().trim ().
                replaceAll ("\\s+"," ").trim ().split (" ");
        tasks = new TreeMap<> ();
        url = null;
        inputType = 0;
        process ();
    }

    private void process ()
    {
        if (commands.length < 2)
        {
            System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
            getLine ();
        }
        if (!(commands[0].equals ("jurl")))
        {
            System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
            getLine ();
        }

        ReservedWord reservedWordStart;
        if ((reservedWordStart = isReserveWord (commands[1])) != null)
        {
            if (reservedWordStart != ReservedWord.FIRE && reservedWordStart != ReservedWord.HELP_V2
            && reservedWordStart != ReservedWord.LIST && reservedWordStart != ReservedWord.CLOSE)
            {
                System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
                getLine ();
            }
            inputType = 1;
            tasks.put (reservedWordStart,new ArrayList<> ());
        }
        else
        {
            inputType = 2;
            url = commands[1];
            for (int i = 2; i < commands.length; i++)
            {
                ReservedWord reservedWord;
                if ((reservedWord = isReserveWord (commands[i])) != null)
                {
                    if (reservedWord != ReservedWord.FIRE && reservedWord != ReservedWord.LIST &&
                            reservedWord != ReservedWord.CLOSE)
                    {
                        tasks.put (reservedWord,findArgumentForReserveWord (reservedWord,i + 1));
                    }

                }
            }
        }
    }

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

        else if (command.equals (ReservedWord.SHOW_HEADER_ARG.getCommandString ()))
            return ReservedWord.SHOW_HEADER_ARG;

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
            case JSON_V2:
            case NAME:
            case UPLOAD:
            case HEADER_V2:
            case METHOD_V2:
            case OUTPUT_V2:
            case FORM_DATA_V2: args.add (commands[nextIndex]);
                break;

            case FIRE:
                args.add (commands[nextIndex]);
                int i = nextIndex + 1;
                while (i < commands.length)
                {
                    if (isReserveWord (commands[i]) == null)
                    {
                        args.add (commands[i]);
                        i++;
                    } else
                        break;
                }
        }
        return args;
    }

    public TreeMap<ReservedWord, ArrayList<String>> getTasks () {
        return tasks;
    }

    public String getUrl () {
        return url;
    }

    public int getInputType () {
        return inputType;
    }

    public void print ()
    {
        System.out.println (Arrays.toString (commands));
        System.out.println (url);
        System.out.println (inputType);
        for (ReservedWord reservedWord : tasks.keySet ())
            System.out.println (reservedWord + "/" + tasks.get (reservedWord).toString ());
    }
}
