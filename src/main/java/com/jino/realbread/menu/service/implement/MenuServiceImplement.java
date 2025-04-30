package com.jino.realbread.menu.service.implement;

import com.jino.realbread.menu.Menu;
import com.jino.realbread.menu.MenuDto;
import com.jino.realbread.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImplement {

    private final MenuRepository menuRepository;

    public void saveAllMenus(List<MenuDto> menuDtoList) {
        List<Menu> entities = menuDtoList.stream()
                .map(dto -> Menu.builder()
                        .menuName(dto.getMenuName())
                        .price(dto.getPrice())
                        .imageUrl(dto.getImageUrl())
                        .description(dto.getDescription())
                        .build())
                .collect(Collectors.toList());

        menuRepository.saveAll(entities);
    }
}
