package com.ems.enrollment.producer;

import com.ems.enrollment.dto.request.CreateEnrollmentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnrollmentProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public void sendEnrollmentRequest(CreateEnrollmentRequest request) {
        rabbitTemplate.convertAndSend(exchange, routingKey, request);
        log.info("Enrollment message published. Student: {}, Course: {}, Semester: {}",
                request.getStudentId(), request.getCourseId(), request.getSemester());
    }
}
