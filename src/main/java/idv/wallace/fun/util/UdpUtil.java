package idv.wallace.fun.util;

import org.springframework.stereotype.Component;

/**
 * Utility class
 */
@Component
public class UdpUtil {

    private static final String HEX_REPRESENTATION = "%02x";

    /**
     * Gets hex representation for specified indexes.
     *
     * @param bytes
     * @param high index of high byte
     * @param low index of low byte
     * @return hex representation
     */
    public String getHex(final byte[] bytes, final int high, final int low) {
        return String.format(HEX_REPRESENTATION, bytes[high] ) + String.format(HEX_REPRESENTATION, bytes[low]);
    }

    /**
     * Converts temperature in hex representation to decimal string format.
     *
     * The reason to divide by 100 is due to pass-in temperature data had been multiply by 100 before sending.
     *
     * @param temperatureHex temperature in hex
     * @return temperature string in decimal format
     */
    public String getTemperature(final String temperatureHex) {
        return String.valueOf((float) Integer.parseInt(temperatureHex, 16) / 100);
    }

    /**
     * Converts humidity in hex representation to decimal string format.
     *
     * The reason to divide by 100 is due to pass-in humidity data had been multiply by 100 before sending.
     *
     * @param humidityHex humidity in hex
     * @return humidity string in decimal format
     */
    public String getHumidity(final String humidityHex) {
        return String.valueOf((float) Integer.parseInt(humidityHex, 16) / 100);
    }

}
