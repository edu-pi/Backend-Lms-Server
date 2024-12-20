package soma.edupilms.classroom.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soma.edupilms.classroom.account.models.CheckClassroomAccountRole;
import soma.edupilms.classroom.account.models.ClassroomAccountResponse;
import soma.edupilms.classroom.account.models.GuestsResponse;
import soma.edupilms.classroom.account.models.RegisterGuestRequest;
import soma.edupilms.classroom.account.service.ClassroomAccountService;
import soma.edupilms.web.models.SuccessResponse;

@RestController
@RequiredArgsConstructor
public class ClassroomAccountController {

    private final ClassroomAccountService classroomAccountService;

    @PostMapping("/v1/classroom/account")
    public ResponseEntity<SuccessResponse> registerClassroomAccount(
            @RequestBody RegisterGuestRequest registerGuestRequest
    ) {
        ClassroomAccountResponse classroomAccountResponse = classroomAccountService.registerClassroomAccount(
                registerGuestRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.withDetailAndResult(
                        "success create classroom account",
                        classroomAccountResponse
                ));

    }

    @GetMapping("/v1/classroom/account")
    public ResponseEntity<SuccessResponse> getClassroomAccount(@RequestParam Long classroomId) {
        GuestsResponse classroomAccountResponses = classroomAccountService.getAllClassroomAccounts(classroomId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.withDetailAndResult(
                        "success retrieved classroom account by classroomId",
                        classroomAccountResponses
                ));
    }

    @GetMapping("/v1/classroom/account/progress")
    public ResponseEntity<SuccessResponse> getClassroomAccountWithoutDefaultAction(@RequestParam Long classroomId) {
        GuestsResponse classroomAccountResponses =
                classroomAccountService.getClassroomAccountsWithoutDefaultAction(classroomId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.withDetailAndResult(
                        "success retrieved classroom account without default action by classroomId",
                        classroomAccountResponses
                ));
    }

    @DeleteMapping("/v1/classroom/account")
    public ResponseEntity<SuccessResponse> deleteClassroomAccount(@RequestParam Long classroomAccountId) {
        classroomAccountService.deleteClassroomAccount(classroomAccountId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.withDetail(
                        "success delete classroom account"
                ));
    }

    @GetMapping("/v1/classroom/account/role")
    public ResponseEntity<SuccessResponse> checkClassroomAccountRole(
            @RequestHeader("X-Account-Id") Long accountId,
            @RequestParam Long classroomId
    ) {
        CheckClassroomAccountRole checkClassroomAccountRole = classroomAccountService.checkClassroomAccountRole(
                accountId, classroomId
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.withDetailAndResult(
                        "success check classroom account role",
                        checkClassroomAccountRole
                ));
    }

    @GetMapping("/v1/classroom/account/code")
    public ResponseEntity<SuccessResponse> code(
            @RequestParam Long classroomAccountId
    ) {
        String code = classroomAccountService.getClassroomAccountCode(classroomAccountId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.withDetailAndResult(
                        "success find classroom account code",
                        code
                ));
    }

}
