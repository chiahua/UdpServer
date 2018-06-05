package idv.wallace.fun.device;

import com.google.gson.annotations.Expose;
import idv.wallace.fun.util.UdpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Represents for NO-CO2 device.
 */
@Component
public class NOCO2 implements Device {

    private final static Logger LOGGER = LoggerFactory.getLogger(NOCO2.class);

    @Autowired
    @Expose
    private Co2Component CO2;

    @Autowired
    @Expose
    private HumidityComponent humidity;

    @Autowired
    @Expose
    private TemperatureComponent temperature;

    @Autowired
    @Expose
    private LqiComponent lqi;

    @Autowired
    @Expose(serialize = false)
    private UdpUtil udpUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    public void parse(final byte[] bytes) {

        final String temperatureHex = this.udpUtil.getHex(bytes, 12, 13);
        final String humidityHex = this.udpUtil.getHex(bytes, 14, 15);
        final String co2Hex = this.udpUtil.getHex(bytes, 16, 17);
        final Integer lqi = Integer.valueOf(bytes[45]);

        LOGGER.info("temperatureHex: {}, humidityHex: {}, co2Hex: {}, lqi: {}", temperatureHex, humidityHex, co2Hex, lqi);

        final String temperature = this.udpUtil.getTemperature(temperatureHex);
        final String humidity = this.udpUtil.getHumidity(humidityHex);
        final String co2 = String.valueOf((float)Integer.parseInt(temperatureHex, 16));

        this.CO2.setValue(co2);
        this.humidity.setValue(humidity);
        this.temperature.setValue(temperature);
        this.lqi.setValue(lqi);

    }

    public Co2Component getCO2() {
        return CO2;
    }

    public void setCO2(Co2Component CO2) {
        this.CO2 = CO2;
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
