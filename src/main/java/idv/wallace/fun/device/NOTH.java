package idv.wallace.fun.device;

import com.google.gson.annotations.Expose;
import idv.wallace.fun.util.UdpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Represents for NO-T&H device.
 */
@Component
public class NOTH implements Device {

    private final static Logger LOGGER = LoggerFactory.getLogger(NOTH.class);

    @Autowired
    @Expose
    private HumidityComponent humidity;

    @Autowired
    @Expose
    private TemperatureComponent temperature;

    @Autowired
    @Expose
    private VoltageComponent voltage;

    @Autowired
    @Expose
    private LqiComponent lqi;

    @Autowired
    @Expose(serialize = false)
    private transient UdpUtil udpUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    public void parse(final byte[] bytes) {
        final String temperatureHex = this.udpUtil.getHex(bytes, 12, 13);
        final String humidityHex = this.udpUtil.getHex(bytes, 14, 15);
        final String voltageHex = this.udpUtil.getHex(bytes, 16, 17);
        final Integer lqi = Integer.valueOf(bytes[45]);

        LOGGER.info("temperatureHex: {}, humidityHex: {}, voltageHex: {}, lqi: {}", temperatureHex, humidityHex, voltageHex, lqi);

        final String temperature = this.udpUtil.getTemperature(temperatureHex);
        final String humidity = this.udpUtil.getHumidity(humidityHex);
        final String voltage = String.valueOf((float)Integer.parseInt(temperatureHex, 16)/1000);

        this.humidity.setValue(humidity);
        this.temperature.setValue(temperature);
        this.voltage.setValue(voltage);
        this.lqi.setValue(lqi);

    }

    public HumidityComponent getHumidity() {
        return humidity;
    }

    public void setHumidity(HumidityComponent humidity) {
        this.humidity = humidity;
    }

    public TemperatureComponent getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureComponent temperature) {
        this.temperature = temperature;
    }

    public VoltageComponent getVoltage() {
        return voltage;
    }

    public void setVoltage(VoltageComponent voltage) {
        this.voltage = voltage;
    }

    public LqiComponent getLqi() {
        return lqi;
    }

    public void setLqi(LqiComponent lqi) {
        this.lqi = lqi;
    }

    public void setUdpUtil(UdpUtil udpUtil) {
        this.udpUtil = udpUtil;
    }
}
