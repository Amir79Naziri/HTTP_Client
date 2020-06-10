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
    private HashMap<String,String> multiDescription;
    private HashMap<String,String> encodedDescription;


    private boolean isToggledHeadersDescription;
    private boolean isToggledQueriesDescription;
    private boolean isToggledMultiMapDescription;
    private boolean isToggleEncodedMapDescription;

    /**
     * creates new ExtraData
     */
    public ExtraData ()
    {
        deActiveEncodedMap = new HashMap<> ();
        deActiveHeaders = new HashMap<> ();
        deActiveMultiMap = new HashMap<> ();
        deActiveQueries = new HashMap<> ();

        encodedDescription = new HashMap<> ();
        multiDescription = new HashMap<> ();
        queriesDescription = new HashMap<> ();
        headersDescription = new HashMap<> ();


        isToggledHeadersDescription = false;
        isToggledMultiMapDescription = false;
        isToggledQueriesDescription = false;
        isToggleEncodedMapDescription = false;
    }



    /**
     * set isToggledHeadersDescription
     * @param toggledHeadersDescription isToggledHeadersDescription
     */
    public void setToggledHeadersDescription (boolean toggledHeadersDescription) {
        isToggledHeadersDescription = toggledHeadersDescription;
    }

    /**
     * set isToggledQueriesDescription
     * @param toggledQueriesDescription isToggledQueriesDescription
     */
    public void setToggledQueriesDescription (boolean toggledQueriesDescription) {
        isToggledQueriesDescription = toggledQueriesDescription;
    }

    /**
     * set isToggledMultiMapDescription
     * @param toggledMultiMapDescription isToggledMultiMapDescription
     */
    public void setToggledMultiMapDescription (boolean toggledMultiMapDescription) {
        isToggledMultiMapDescription = toggledMultiMapDescription;
    }

    /**
     * set isToggleEncodedMapDescription
     * @param toggleEncodedMapDescription isToggleEncodedMapDescription
     */
    public void setToggleEncodedMapDescription (boolean toggleEncodedMapDescription) {
        isToggleEncodedMapDescription = toggleEncodedMapDescription;
    }

    /**
     *
     * @return isToggledHeadersDescription
     */
    public boolean isToggledHeadersDescription () {
        return isToggledHeadersDescription;
    }

    /**
     *
     * @return isToggledMultiMapDescription
     */
    public boolean isToggledMultiMapDescription () {
        return isToggledMultiMapDescription;
    }

    /**
     *
     * @return isToggledQueriesDescription
     */
    public boolean isToggledQueriesDescription () {
        return isToggledQueriesDescription;
    }

    /**
     *
     * @return isToggleEncodedMapDescription
     */
    public boolean isToggleEncodedMapDescription () {
        return isToggleEncodedMapDescription;
    }

    /**
     * clear everything
     */
    public void clearHeadersExtraData ()
    {
        deActiveHeaders.clear ();
    }

    /**
     * clear everything
     */
    public void clearQueriesExtraData ()
    {
        deActiveQueries.clear ();
    }

    /**
     * clear everything
     */
    public void clearMultiExtraData ()
    {
        deActiveMultiMap.clear ();
    }

    /**
     * clear everything
     */
    public void clearEncodedExtraData ()
    {
        deActiveEncodedMap.clear ();
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
     * @return headersDescription
     */
    public HashMap<String, String> getHeadersDescription () {
        return headersDescription;
    }

    /**
     *
     * @return encodedDescription
     */
    public HashMap<String, String> getEncodedDescription () {
        return encodedDescription;
    }

    /**
     *
     * @return multiDescription
     */
    public HashMap<String, String> getMultiDescription () {
        return multiDescription;
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
    public void addMultiDescription (String key, String value)
    {
        multiDescription.put (key, value);
    }

    /**
     * add new element
     * @param key key
     * @param value value
     */
    public void addEncodedDescription (String key, String value)
    {
        encodedDescription.put (key, value);
    }



}
