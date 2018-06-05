package idv.wallace.fun.device;

import com.google.gson.annotations.Expose;
import org.springframework.stereotype.Component;

/**
 * Represents for voltage component of a device.
 */
@Component
public class VoltageComponent {

    @Expose
    private String value;

    @Expose
    private int number = 3;

    @Expose
    private String unit = "V";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


}
