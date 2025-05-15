package com.jino.realbread.domain.bakery.dto.response;

import com.jino.realbread.domain.bakery.repository.resultSet.GetBakeryResultSet;
import com.jino.realbread.global.common.ResponseCode;
import com.jino.realbread.global.common.ResponseMessage;
import com.jino.realbread.global.dto.response.ResponseDto;
import com.jino.realbread.menu.dto.MenuListItem;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GetBakeryResponseDto extends ResponseDto {

    private int bakeryNumber;
    private String title;
    private String roadAddress;
    private int favoriteCount;
    private int commentCount;
    private List<MenuListItem> menuList;

    private GetBakeryResponseDto(List<GetBakeryResultSet> resultSet) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        GetBakeryResultSet first = resultSet.get(0);
        this.bakeryNumber = first.getBakeryNumber();
        this.title = first.getTitle();
        this.roadAddress = first.getRoadAddress();
        this.favoriteCount = first.getFavoriteCount();
        this.commentCount = first.getCommentCount();

        List<MenuListItem> menuList = new ArrayList<>();
        for (GetBakeryResultSet row : resultSet) {
            MenuListItem item = new MenuListItem(
                    row.getMenuNumber(),
                    row.getMenuName(),
                    row.getPrice(),
                    row.getImageUrl(),
                    row.getDescription());
            menuList.add(item);
        }
        this.menuList = menuList;

    }

    public static ResponseEntity<GetBakeryResponseDto> success(List<GetBakeryResultSet> resultSet) {
        GetBakeryResponseDto result = new GetBakeryResponseDto(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistBakery() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BAKERY, ResponseMessage.NOT_EXISTED_BAKERY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
