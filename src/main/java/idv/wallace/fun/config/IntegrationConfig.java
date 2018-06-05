package idv.wallace.fun.config;

import idv.wallace.fun.service.UdpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

/**
 * Configuration class for UDP server application.
 */
@Configuration
public class IntegrationConfig {

    @Autowired
    private UdpProperty udpProperty;

    @Bean
    public UdpService udpService() {
        return new UdpService();
    }

    @Bean
    public IntegrationFlow processUdpMessage() {
        return IntegrationFlows
                .from(new UnicastReceivingChannelAdapter(udpProperty.getPort()))
                .handle("udpService", "receive")
                .get();
    }
}
