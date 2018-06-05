package idv.wallace.fun.device;

import idv.wallace.fun.util.UdpUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NOCO2Test {

    @Mock
    private Co2Component CO2;

    @Mock
    private HumidityComponent humidity;

    @Mock
    private TemperatureComponent temperature;

    @Mock
    private LqiComponent lqi;

    private final UdpUtil util = new UdpUtil();

    private final NOCO2 noco2 = new NOCO2();

    @Before
    public void setup() {
        this.noco2.setHumidity(this.humidity);
        this.noco2.setTemperature(this.temperature);
        this.noco2.setCO2(this.CO2);
        this.noco2.setLqi(this.lqi);
        this.noco2.setUdpUtil(this.util);
    }

    @Test
    public void parse() {
        final byte[] bytes = new byte[49];
        bytes[12] = 9;
        bytes[13] = -57;
        bytes[14] = 16;
        bytes[15] = 120;
        bytes[16] = 6;
        bytes[17] = 18;
        bytes[45] = 127;

        this.noco2.parse(bytes);

        verify(this.CO2, times(1)).setValue(anyString());
        verify(this.humidity, times(1)).setValue(anyString());
        verify(this.temperature, times(1)).setValue(anyString());
        verify(this.lqi, times(1)).setValue(anyInt());
    }
}