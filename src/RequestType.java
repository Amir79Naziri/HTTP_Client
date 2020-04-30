import java.awt.*;

public enum RequestType
{
    GET(new Color (122, 122, 255, 249), "GET"),
    DELETE(new Color (217, 42, 10), "DEL"),
    POST(new Color (20, 145,0), "POST"),
    PUT(new Color (206, 103, 15), "PUT"),
    PATCH(new Color (209, 192,0) , "PTCH");

    private Color color;
    private String name;


    RequestType (Color color, String name)
    {
        this.color = color;
        this.name = name;
    }

    public Color getColor () {
        return color;
    }

    public String getName () {
        return name;
    }
}
