package com.cleverit.springboottest.pubsub;

import com.cleverit.springboottest.core.service.PubSubService;
import com.google.cloud.spring.pubsub.core.PubSubOperations;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.IOException;

@Configuration
@Slf4j
@EnableIntegration
@RequiredArgsConstructor
public class PubSub {


    private final PubSubService pubSubService;

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubsubInputChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "suscripcion-entrada");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {

        return message -> {
            log.info("[+] Message arrived! Payload: " + new String((byte[]) message.getPayload()));

            try {
                pubSubService.savePokemon(new String((byte[]) message.getPayload()));
                BasicAcknowledgeablePubsubMessage originalMessage =
                        message.getHeaders()
                                .get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
                originalMessage.ack();
            } catch (Exception e) {
                log.error("[-] Error in messageReceiver: {} ", e.getClass().toString());
            }
        };
    }

    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
    public interface ReportPubSubOutboundGateway {
        void sendToPubsub(String text);
    }

    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    public MessageHandler messageSender(PubSubOperations pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, "tema-salida");
    }


}
