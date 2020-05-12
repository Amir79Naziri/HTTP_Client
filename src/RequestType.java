import java.awt.*;

/**
 * this enum has variety of requests type
 *
 * @author Amir Naziri
 */
public enum RequestType
{
    GET(new Color (122, 122, 255, 249), "GET"),
    DELETE(new Color (217, 42, 10), "DEL"),
    POST(new Color (20, 145,0), "POST"),
    PUT(new Color (206, 103, 15), "PUT"),
    PATCH(new Color (209, 192,0) , "PTCH");

    private Color color; // color of requestsType
    private String name; // name of requestsType

    /**
     * create a request type
     * @param color color
     * @param name name
     */
    RequestType (Color color, String name)
    {
        this.color = color;
        this.name = name;
    }

    /**
     * @return color of requestType
     */
    public Color getColor () {
        return color;
    }

    /**
     * @return name of requestType
     */
    public String getName () {
        return name;
    }
}
