package research.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by akila on 8/23/16.
 */
public class SpecificationItem {

    private String key;
    private String keyFormatter;
    private String valueFormatter;
    private String valueType;
    private String valueInputFormat;
    private String updatedKey;
    private String operationKey;
    private String customValueNames;


    private static final Logger logger= LoggerFactory.getLogger(SpecificationItem.class);
    public SpecificationItem(){

    }

    public SpecificationItem(String key, String keyFormatter, String valueFormatter, String valueType, String valueInputFormat, String updatedKey, String operationKey, String customValueNames) {
        this.key = key;
        this.keyFormatter = keyFormatter;
        this.valueFormatter = valueFormatter;
        this.valueType = valueType;
        this.valueInputFormat = valueInputFormat;
        this.updatedKey = updatedKey;
        this.operationKey = operationKey;
        this.customValueNames = customValueNames;
    }

    @JsonProperty
    public String getOperationKey() {
        return operationKey;
    }

    public void setOperationKey(String operationKey) {
        this.operationKey = operationKey;
    }

    @JsonProperty
    public String getUpdatedKey() {
        return updatedKey;
    }

    public void setUpdatedKey(String updatedKey) {
        this.updatedKey = updatedKey;
    }

    @JsonProperty
    public String getValueInputFormat() {
        return valueInputFormat;
    }

    public void setValueInputFormat(String valueInputFormat) {
        this.valueInputFormat = valueInputFormat;
    }

    @JsonProperty
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty
    public String getKeyFormatter() {
        return keyFormatter;
    }

    public void setKeyFormatter(String keyFormatter) {
        this.keyFormatter = keyFormatter;
    }
    @JsonProperty
    public String getValueFormatter() {
        return valueFormatter;
    }

    public void setValueFormatter(String valueFormatter) {
        this.valueFormatter = valueFormatter;
    }

    @JsonProperty
    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    @JsonProperty
    public String getCustomValueNames() {
        return customValueNames;
    }

    public void setCustomValueNames(String customValueNames) {
        this.customValueNames = customValueNames;
    }
}
