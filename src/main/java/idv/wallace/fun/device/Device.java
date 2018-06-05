package idv.wallace.fun.device;

/**
 * Represents for a device.
 *
 */
public interface Device {

    /**
     * Get the needed data from pass-in UDP data and set them into corresponding fields.
     *
     * @param bytes pass-in UDP bytes
     */
    void parse(final byte[] bytes);
}
