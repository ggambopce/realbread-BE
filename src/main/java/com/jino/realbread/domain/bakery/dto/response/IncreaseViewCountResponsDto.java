package com.jino.realbread.domain.bakery.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jino.realbread.global.common.ResponseCode;
import com.jino.realbread.global.common.ResponseMessage;
import com.jino.realbread.global.dto.response.ResponseDto;

public class IncreaseViewCountResponsDto extends ResponseDto {

    private IncreaseViewCountResponsDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    public static ResponseEntity<IncreaseViewCountResponsDto> success() {
        IncreaseViewCountResponsDto result = new IncreaseViewCountResponsDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistBoard() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BAKERY, ResponseMessage.NOT_EXISTED_BAKERY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}
