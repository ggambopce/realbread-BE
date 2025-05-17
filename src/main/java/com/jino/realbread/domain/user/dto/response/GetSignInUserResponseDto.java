import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jino.realbread.domain.user.dto.SignInUserDto;
import com.jino.realbread.domain.user.entity.UserEntity;
import com.jino.realbread.global.common.ResponseCode;
import com.jino.realbread.global.common.ResponseMessage;
import com.jino.realbread.global.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GetSignInUserResponseDto extends ResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String profileImage;

    public GetSignInUserResponseDto(SignInUserDto user) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto result = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }
}