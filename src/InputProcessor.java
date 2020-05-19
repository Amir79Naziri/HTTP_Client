import java.util.*;

public class InputProcessor
{
    private Scanner systemInput;
    private String[] commands;
    private TreeMap<ReservedWord,ArrayList<String>> tasks;
    private String url;


    public InputProcessor ()
    {
        systemInput = new Scanner (System.in);
    }


    public void getLine ()
    {
        System.out.print ("> ");
        commands = systemInput.nextLine ().trim ().
                replaceAll ("\\s+"," ").trim ().split (" ");
        tasks = new TreeMap<> ();
        url = null;
        process ();
    }

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
                getLine ();
                return;
            }
            tasks.put (reservedWordStart,findArgumentForReserveWord (reservedWordStart,2));
        }
        else
        {
            url = commands[1];
            for (int i = 2; i < commands.length; i++)
            {
                ReservedWord reservedWord;
                if ((reservedWord = isReserveWord (commands[i])) != null)
                {
                    if (reservedWord != ReservedWord.FIRE && reservedWord != ReservedWord.LIST &&
                            reservedWord != ReservedWord.CLOSE)
                    {
                        ArrayList<String> args = findArgumentForReserveWord (reservedWord,i + 1);
                        if (args == null)
                        {
                            getLine ();
                            return;
                        }

                        tasks.put (reservedWord,args);
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
                if (nextIndex < commands.length && isReserveWord (commands[nextIndex]) == null)
                    args.add (commands[nextIndex]);
                break;
            case JSON_V2:
            case NAME:
            case HEADER_V2:
            case METHOD_V2:
            case OUTPUT_V2:
            case FORM_DATA_V2:
                if (nextIndex < commands.length && isReserveWord (commands[nextIndex]) == null)
                    args.add (commands[nextIndex]);
                else
                {
                    System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
                    return null;
                }
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
                    System.out.println ("jurl: try 'jurl --help' / 'jurl -h' for more information");
                    return null;
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



    public void print ()
    {
        System.out.println (Arrays.toString (commands));
        System.out.println (url);
        for (ReservedWord reservedWord : tasks.keySet ())
            System.out.println (reservedWord + "/" + tasks.get (reservedWord).toString ());
    }
}
