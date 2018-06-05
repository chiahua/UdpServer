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
public class NOTHTest {

    @Mock
    private HumidityComponent humidity;

    @Mock
    private TemperatureComponent temperature;

    @Mock
    private VoltageComponent voltage;

    @Mock
    private LqiComponent lqi;

    private final UdpUtil util = new UdpUtil();

    private final NOTH noth = new NOTH();

    @Before
    public void setup() {
        this.noth.setHumidity(this.humidity);
        this.noth.setTemperature(this.temperature);
        this.noth.setVoltage(this.voltage);
        this.noth.setLqi(this.lqi);
        this.noth.setUdpUtil(this.util);
    }

    @Test
    public void parse() {
        final byte[] bytes = new byte[49];
        bytes[12] = 35;
        bytes[13] = 15;
        bytes[14] = 121;
        bytes[15] = 20;
        bytes[16] = 23;
        bytes[17] = -3;
        bytes[45] = 127;

        this.noth.parse(bytes);

        verify(this.humidity, times(1)).setValue(anyString());
        verify(this.temperature, times(1)).setValue(anyString());
        verify(this.voltage, times(1)).setValue(anyString());
        verify(this.lqi, times(1)).setValue(anyInt());


    }
}