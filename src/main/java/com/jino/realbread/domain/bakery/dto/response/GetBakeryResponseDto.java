package com.jino.realbread.domain.bakery.dto.response;

import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryResultSet;
import com.jino.realbread.global.common.ResponseCode;
import com.jino.realbread.global.common.ResponseMessage;
import com.jino.realbread.global.dto.response.ResponseDto;
import com.jino.realbread.menu.dto.MenuListItem;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetBakeryResponseDto extends ResponseDto {

    private int bakeryNumber;
    private String title;
    private String roadAddress;
    private int favoriteCount;
    private int commentCount;
    private List<MenuListItem> menuList;

    private GetBakeryResponseDto(GetBakeryResultSet resultSet, List<MenuListItem> menuListItems) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        this.bakeryNumber = resultSet.getBakeryNumber();
        this.title = resultSet.getTitle();
        this.roadAddress = resultSet.getRoadAddress();
        this.favoriteCount = resultSet.getFavoriteCount();
        this.commentCount = resultSet.getCommentCount();
        this.menuList = menuListItems;
    }

    public static ResponseEntity<GetBakeryResponseDto> success(GetBakeryResultSet resultSet, List<MenuListItem> menuListItems) {
        GetBakeryResponseDto result = new GetBakeryResponseDto(resultSet, menuListItems);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistBakery() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BAKERY, ResponseMessage.NOT_EXISTED_BAKERY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
