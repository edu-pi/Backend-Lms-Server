package soma.edupilms.progress.service.emitters;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseEmitters {

    private final ConcurrentHashMap<String, SseEmitter> sseMap = new ConcurrentHashMap<>();

    public Optional<SseEmitter> findSseEmitter(String classroomId) {
        return Optional.ofNullable(sseMap.get(classroomId));
    }

    public void create(String classroomId, SseEmitter sseEmitter) {
        sseMap.put(classroomId, sseEmitter);
    }

    public void delete(String classroomId) {
        sseMap.remove(classroomId);
    }
}
