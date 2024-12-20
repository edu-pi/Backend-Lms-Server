package soma.edupilms.progress.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import soma.edupilms.progress.models.ActionChangeRequest;
import soma.edupilms.progress.service.emitters.SseEmitters;

@Slf4j
@Service
public class RedisService {

    private final RedisMessageListenerContainer container;
    private final RedisSubscriber subscriber;
    private final RedisTemplate<String, Object> template;

    public RedisService(RedisMessageListenerContainer container, SseEmitters sseEmitters,
        RedisTemplate<String, Object> template) {
        this.container = container;
        this.subscriber = new RedisSubscriber(sseEmitters);
        this.template = template;
    }

    public static final String CHANNEL_PREFIX = "classroomId:";

    public void subscribe(String classroomId) {
        container.addMessageListener(subscriber, ChannelTopic.of(getChannelName(classroomId)));
    }

    public void publish(ActionChangeRequest actionChangeRequest) {
        template.convertAndSend(getChannelName(String.valueOf(actionChangeRequest.getClassroomId())),
            actionChangeRequest);
    }

    public void removeSubscribe(String classroomId) {
        container.removeMessageListener(subscriber, ChannelTopic.of(getChannelName(classroomId)));
    }

    private String getChannelName(String classroomId) {
        return CHANNEL_PREFIX + classroomId;
    }

    public static class RedisSubscriber implements MessageListener {

        private final ObjectMapper objectMapper = new ObjectMapper();
        private final SseEmitters sseEmitters;

        public RedisSubscriber(SseEmitters sseEmitters) {
            this.sseEmitters = sseEmitters;
        }

        @Override
        public void onMessage(Message message, byte[] pattern) {
            try {
                String channel = new String(message.getChannel())
                    .substring(CHANNEL_PREFIX.length());

                ActionChangeRequest actionChangeRequest = objectMapper.readValue(
                    message.getBody(),
                    ActionChangeRequest.class
                );

                // 클라이언트에게 event 데이터 전송
                sseEmitters.findSseEmitter(channel).ifPresent(sseEmitter -> {
                    try {
                        sseEmitter.send(SseEmitter.event().name("action").data(actionChangeRequest));
                    } catch (IOException e) {
                        sseEmitters.delete(channel);
                    }
                });
            } catch (IOException e) {
                log.error("[RedisSubscriber] IOException is occurred. ", e);
            }
        }
    }

}
