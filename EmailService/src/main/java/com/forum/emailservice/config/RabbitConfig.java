package com.forum.emailservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${mq.address}")
    private String address;
    @Value("${mq.username}")
    private String username;
    @Value("${mq.password}")
    private String password;
    @Value("${mq.virtualhost}")
    private String virtualhost;

    @Bean
    ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(address);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);

        rabbitAdmin.declareExchange(new DirectExchange("forum.direct.exchange", false, false, null));
        rabbitAdmin.declareQueue(new Queue("direct.queue", false, false, false, null));

        rabbitAdmin.declareBinding(new Binding(
                "direct.queue",
                Binding.DestinationType.QUEUE,
                "forum.direct.exchange",
                "direct.routing.key",
                null
        ));
        return rabbitAdmin;
    }


}
