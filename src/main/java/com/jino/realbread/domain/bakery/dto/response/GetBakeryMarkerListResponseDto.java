package com.jino.realbread.domain.bakery.dto.response;

import com.jino.realbread.domain.bakery.dto.BakeryMarkerListItem;
import com.jino.realbread.global.common.ResponseCode;
import com.jino.realbread.global.common.ResponseMessage;
import com.jino.realbread.global.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetBakeryMarkerListResponseDto extends ResponseDto {

    private List<BakeryMarkerListItem> markerList;

    private GetBakeryMarkerListResponseDto(List<BakeryMarkerListItem> markerList) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.markerList = markerList;
    }

    public static ResponseEntity<GetBakeryMarkerListResponseDto> success(List<BakeryMarkerListItem> markerList) {
        GetBakeryMarkerListResponseDto result = new GetBakeryMarkerListResponseDto(markerList);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistMarker() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_MARKER, ResponseMessage.NOT_EXISTED_MARKER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
