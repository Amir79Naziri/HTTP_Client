package Client;

/**
 * this class make size readable
 *
 * @author Amir Naziri
 */
public class MakeSizeReadable
{
    /**
     * change the the data size to humanReadable form
     * @param size input size
     * @return humanReadable form
     */
    public static String makeSizeReadable (long size)
    {
        if (size < 1024)
            return size + " B";

        String[] scales = {"k", "M", "G"};
        int i = -1;
        while (size > 1024)
        {
            size /= 1024;
            i++;
        }
        float newSize = (size / 1f);
        return String.format ("%.1f %s",newSize,scales[i]);

    }
}
