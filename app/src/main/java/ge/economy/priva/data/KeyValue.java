package ge.economy.priva.data;

/**
 * Created by nl on 7/14/2015.
 */
public class KeyValue {

    private String key;
    private String value;

    public KeyValue(String k, String v) {
        key = k;
        value = v;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
