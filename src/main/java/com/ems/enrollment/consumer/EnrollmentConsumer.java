package com.ems.enrollment.consumer;

import com.ems.enrollment.dto.request.CreateEnrollmentRequest;
import com.ems.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnrollmentConsumer {

    private final EnrollmentService enrollmentService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consumeEnrollmentRequest(CreateEnrollmentRequest request) {
        log.info("Enrollment message consumed. Student: {}, Course: {}, Semester: {}",
                request.getStudentId(), request.getCourseId(), request.getSemester());
        try {
            enrollmentService.create(request);
            log.info("Enrollment completed successfully via RabbitMQ");
        } catch (Exception e) {
            log.error("Enrollment failed: {}", e.getMessage());
        }
    }
}
