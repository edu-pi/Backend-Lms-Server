package soma.haeya.lms.common.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import soma.haeya.lms.classroom.model.request.ClassroomCreateRequest;
import soma.haeya.lms.follower.model.request.RegisterFollowerRequest;

@Component
@HttpExchange("/v1")
public interface DbServerApiClient {

    @PostExchange("/classroom")
    void createClassroom(@RequestBody ClassroomCreateRequest createClassroomRequest);

    @PostExchange("/classroom-account")
    void registerFollower(@RequestBody RegisterFollowerRequest registerFollowerRequest);

}
