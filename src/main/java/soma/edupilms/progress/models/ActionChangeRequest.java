package soma.edupilms.progress.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import soma.edupilms.progress.service.models.ActionStatus;

@Getter
@NoArgsConstructor
public class ActionChangeRequest {

    private Long classroomId;
    private Long accountId;
    private ActionStatus action;
}