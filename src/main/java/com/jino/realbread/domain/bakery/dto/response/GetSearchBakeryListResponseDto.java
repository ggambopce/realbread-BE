package com.jino.realbread.domain.bakery.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jino.realbread.domain.bakery.dto.BakeryListItem;
import com.jino.realbread.domain.view.BakeryListViewEntity;
import com.jino.realbread.global.common.ResponseCode;
import com.jino.realbread.global.common.ResponseMessage;
import com.jino.realbread.global.dto.response.ResponseDto;

public class GetSearchBakeryListResponseDto extends ResponseDto {

    private List<BakeryListItem> searchList;

    private GetSearchBakeryListResponseDto(List<BakeryListViewEntity> bakeryListViewEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.searchList = BakeryListItem.getList(bakeryListViewEntities);
    }

    public static ResponseEntity<GetSearchBakeryListResponseDto> success(
            List<BakeryListViewEntity> bakeryListViewEntities) {
        GetSearchBakeryListResponseDto result = new GetSearchBakeryListResponseDto(bakeryListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
