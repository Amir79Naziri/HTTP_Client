package Storage;

import java.io.Serializable;
import java.util.HashMap;

/**
 * this class built for storing some extraData which a clientRequest doesn't need for send
 * and just GUI needs them, or may be in future will use it
 *
 * @author Amir Naziri
 */
public class ExtraData implements Serializable
{

    private HashMap<String,String> deActiveHeaders;
    private HashMap<String,String> deActiveQueries;
    private HashMap<String,String> deActiveMultiMap;
    private HashMap<String,String> deActiveEncodedMap;

    private HashMap<String,String> headersDescription;
    private HashMap<String,String> queriesDescription;
    private HashMap<String,String> multiMapDescription;
    private HashMap<String,String> encodedMapDescription;

//    private boolean isToggledHeadersDescription;
//    private boolean isToggledHeadersDescription;
//    private boolean isToggledHeadersDescription;
//    private boolean

    /**
     * creates new ExtraData
     */
    public ExtraData ()
    {
        deActiveEncodedMap = new HashMap<> ();
        deActiveHeaders = new HashMap<> ();
        deActiveMultiMap = new HashMap<> ();
        deActiveQueries = new HashMap<> ();

        headersDescription = new HashMap<> ();
        queriesDescription = new HashMap<> ();
        multiMapDescription = new HashMap<> ();
        encodedMapDescription = new HashMap<> ();
    }

    /**
     * clear everything
     */
    public void clearHeadersExtraData ()
    {
        deActiveHeaders.clear ();
        headersDescription.clear ();
    }

    /**
     * clear everything
     */
    public void clearQueriesExtraData ()
    {
        deActiveQueries.clear ();
        queriesDescription.clear ();
    }

    /**
     * clear everything
     */
    public void clearMultiExtraData ()
    {
        deActiveMultiMap.clear ();
        multiMapDescription.clear ();
    }

    /**
     * clear everything
     */
    public void clearEncodedExtraData ()
    {
        deActiveEncodedMap.clear ();
        encodedMapDescription.clear ();
    }


    /**
     *
     * @return deActiveEncodedMap
     */
    public HashMap<String, String> getDeActiveEncodedMap () {
        return deActiveEncodedMap;
    }

    /**
     *
     * @return deActiveHeaders
     */
    public HashMap<String, String> getDeActiveHeaders () {
        return deActiveHeaders;
    }

    /**
     *
     * @return deActiveMultiMap
     */
    public HashMap<String, String> getDeActiveMultiMap () {
        return deActiveMultiMap;
    }

    /**
     *
     * @return deActiveQueries
     */
    public HashMap<String, String> getDeActiveQueries () {
        return deActiveQueries;
    }

    /**
     *
     * @return encodedMapDescription
     */
    public HashMap<String, String> getEncodedMapDescription () {
        return encodedMapDescription;
    }

    /**
     *
     * @return headersDescription
     */
    public HashMap<String, String> getHeadersDescription () {
        return headersDescription;
    }

    /**
     *
     * @return multiMapDescription
     */
    public HashMap<String, String> getMultiMapDescription () {
        return multiMapDescription;
    }

    /**
     *
     * @return queriesDescription
     */
    public HashMap<String, String> getQueriesDescription () {
        return queriesDescription;
    }

    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addDeActiveHeaders (String key, String value)
    {
        deActiveHeaders.put (key, value);
    }

    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addDeActiveQueries (String key, String value)
    {
        deActiveQueries.put (key, value);
    }

    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addDeActiveMultiMap (String key, String value)
    {
        deActiveMultiMap.put (key, value);
    }

    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addDeActiveEncodedMap (String key, String value)
    {
        deActiveEncodedMap.put (key, value);
    }


    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addHeadersDescription (String key, String value)
    {
        headersDescription.put (key, value);
    }

    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addQueriesDescription (String key, String value)
    {
        queriesDescription.put (key, value);
    }

    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addMultiMapDescription (String key, String value)
    {
        multiMapDescription.put (key, value);
    }

    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addEncodedMapDescription (String key, String value)
    {
        encodedMapDescription.put (key, value);
    }

}
