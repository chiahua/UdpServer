package idv.wallace.fun.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import idv.wallace.fun.device.Device;
import idv.wallace.fun.device.NOCO2;
import idv.wallace.fun.device.NOTH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for dealing with pass-in UDP data.
 */
@Service
public class UdpService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UdpService.class);

    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    @Autowired
    private NOTH noth;

    @Autowired
    private NOCO2 noco2;

    /**
     * Entry point for handling UDP data
     *
     * @param message pass-in UDP message
     */
    public void receive(final Message message) {

        final byte[] bytes = byte[].class.cast(message.getPayload());
        LOGGER.info("Received msg: {}", bytes);

        this.validate(bytes);

        final Optional<Device> deviceOptional = this.decideDevice(bytes);
        if (deviceOptional.isPresent()) {
            final Device device = deviceOptional.get();
            device.parse(bytes);

            final String json = this.toJson(device);
            LOGGER.info("Received msg in json:\n {}", json);
        } else {
            LOGGER.info("No corresponding device for pass-in UDP data.");
        }

    }

    /**
     * Validation for pass-in UDP data. Will throw RuntimeException if validation failed.
     *
     * The validation contains:
     * 1. Length validation: Correct length of UDP data should be 49 bytes.
     *
     * 2. Starting value validation: The value of first byte should be 2.
     *
     * @param bytes pass-in UDP data in byte
     */
    private void validate(final byte[] bytes) {

        if (bytes.length != 49) {
            throw new RuntimeException(String.format("Data corrupted. Expected length: 49 bytes, actual: %s bytes", bytes.length));
        }

        if (bytes[0] != 2) {
            throw new RuntimeException(String.format("Expected value of first byte: 2, actual: %s", bytes[0]));
        }
    }

    /**
     * Decide pass-in data belonging to which device. Use the value of the 10th byte to decide device.
     * If the value is -104, then it's NOCO2; -124 for NOTH.
     *
     * @param bytes pass-in UDP data in byte
     * @return device
     */
    private Optional<Device> decideDevice(final byte[] bytes) {
        final int value = bytes[9];
        Device device = null;

        LOGGER.info("The value of 10th byte  is {}", value);

        if (value == -104) {
            device = this.noco2;
        } else if (value == -124) {
            device = this.noth;
        }

        return Optional.ofNullable(device);
    }

    /**
     * Converts the pass-in device into json string.
     *
     * @param device {@link Device} instance
     * @return device data in JSON format
     */
    @VisibleForTesting
    String toJson(final Device device) {

        return this.gson.toJson(device);
    }

    public void setNoth(NOTH noth) {
        this.noth = noth;
    }

    public void setNoco2(NOCO2 noco2) {
        this.noco2 = noco2;
    }
}

