package idv.wallace.fun.device;

import com.google.gson.annotations.Expose;
import org.springframework.stereotype.Component;

/**
 * Represents for LQI component of a device.
 */
@Component
public class LqiComponent {

    @Expose
    private int number = 0;

    @Expose
    private int value;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
