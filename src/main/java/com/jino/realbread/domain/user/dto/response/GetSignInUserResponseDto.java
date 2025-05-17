import com.jino.realbread.domain.user.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetSignInUserResponseDto {
    private Long userId;
    private String nickname;
    private String profileImage;

    public GetSignInUserResponseDto(UserEntity entity) {
        this.userId = entity.getUserId();
        this.nickname = entity.getNickname();
        this.profileImage = entity.getProfileImage();
    }
}