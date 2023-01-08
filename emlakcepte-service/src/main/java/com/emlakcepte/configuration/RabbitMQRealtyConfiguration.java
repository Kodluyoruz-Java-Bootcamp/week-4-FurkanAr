package com.emlakcepte.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQRealtyConfiguration {
	private String queueName = "emlakcepte.notification.realty";

	private String exchange = "emlakcepte.notification.realty";

	@Bean(name="emlakcepte.realty")
	public Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean(name="emlakcepte.realty")
	public DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean(name="emlakcepte.realty")
	public Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("");
	}

	@Bean(name="emlakcepte.realty")
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

}
