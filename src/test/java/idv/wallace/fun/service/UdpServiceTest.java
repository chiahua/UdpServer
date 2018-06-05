package idv.wallace.fun.service;

import idv.wallace.fun.device.NOCO2;
import idv.wallace.fun.device.NOTH;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.Message;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UdpServiceTest {

    @Mock
    private NOTH noth;

    @Mock
    private NOCO2 noco2;

    @Mock
    private Message message;

    private UdpService service;

    @Before
    public void setup() {
        this.service = spy(new UdpService());
        this.service.setNoth(this.noth);
        this.service.setNoco2(this.noco2);
    }

    @Test(expected = RuntimeException.class)
    public void receive_wrongLength() {
        final byte[] bytes = new byte[48];

        when(this.message.getPayload()).thenReturn(bytes);

        this.service.receive(this.message);
    }

    @Test(expected = RuntimeException.class)
    public void receive_wrongFirstByteValue() {
        final byte[] bytes = new byte[49];
        bytes[0] = 3;

        when(this.message.getPayload()).thenReturn(bytes);

        this.service.receive(this.message);
    }

    @Test
    public void receive_NOTH() {
        final byte[] bytes = new byte[49];
        bytes[0] = 2;
        bytes[9] = -124;


        when(this.message.getPayload()).thenReturn(bytes);
        doReturn("test").when(this.service).toJson(this.noth);

        this.service.receive(this.message);

        verify(this.message, times(1)).getPayload();
        verify(this.noth, times(1)).parse(bytes);
        verify(this.noco2, times(0)).parse(bytes);
    }

    @Test
    public void receive_NOCO2() {
        final byte[] bytes = new byte[49];
        bytes[0] = 2;
        bytes[9] = -104;


        when(this.message.getPayload()).thenReturn(bytes);
        doReturn("tester").when(this.service).toJson(this.noco2);

        this.service.receive(this.message);

        verify(this.message, times(1)).getPayload();
        verify(this.noth, times(0)).parse(bytes);
        verify(this.noco2, times(1)).parse(bytes);
    }
}