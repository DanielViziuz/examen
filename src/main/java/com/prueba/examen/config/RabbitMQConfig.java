package com.prueba.examen.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	public static final String EXCHANGE = "payment.exchange"; //Exchange de tipo topic por los dos condumidores
    public static final String ROUTING_KEY = "payment.status.changed";

    public static final String QUEUE_NOTIFICATION = "payments.notification.queue";//La cola donde se reporta el cambio
    public static final String QUEUE_AUDIT = "payments.audit.queue";//Una cola de registre el cambio
    
    @Bean
    public TopicExchange paymentsExchange() {
        return new TopicExchange(EXCHANGE);
    }
    
    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NOTIFICATION, true); // true = durable
    }
    
    @Bean
    public Queue auditQueue() {
        return new Queue(QUEUE_AUDIT, true);
    }
    
    @Bean
    public Binding bindingNotification(@Qualifier("notificationQueue") Queue notificacionQueue, TopicExchange pagosExchange) {
        return BindingBuilder.bind(notificacionQueue).to(pagosExchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding bindingAudit(@Qualifier("auditQueue") Queue auditoriaQueue, TopicExchange pagosExchange) {
        return BindingBuilder.bind(auditoriaQueue).to(pagosExchange).with(ROUTING_KEY);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {//Configuracion para pasarlo a JSON los mensajes
        return new JacksonJsonMessageConverter();
    }
    /*
    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }*/
}
