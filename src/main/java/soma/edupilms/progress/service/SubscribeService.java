package soma.edupilms.progress.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import soma.edupilms.progress.service.redis.RedisService;
import soma.edupilms.progress.service.sse.SseEmitterService;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SseEmitterService sseEmitterService;
    private final RedisService redisService;

    public SseEmitter subscribe(String classroomId) {
        SseEmitter connection = sseEmitterService.createOrGetConnection(redisService, classroomId);

        redisService.subscribe(classroomId);

        return connection;
    }

}