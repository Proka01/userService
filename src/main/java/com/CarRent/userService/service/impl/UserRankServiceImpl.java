package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.UserRankCreateDto;
import com.CarRent.userService.dto.UserRankUpdateDto;
import com.CarRent.userService.model.UserRank;
import com.CarRent.userService.repository.UserRankRepository;
import com.CarRent.userService.service.UserRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRankServiceImpl implements UserRankService {

    private final UserRankRepository userRankRepository;

    public UserRankServiceImpl(UserRankRepository userRankRepository) {
        this.userRankRepository = userRankRepository;
    }

    @Override
    public String update(UserRankUpdateDto userRankUpdateDto) {
        UserRank userRank = userRankRepository.findById(userRankUpdateDto.getId()).get();

        if(userRankUpdateDto.getDiscount()!=null)userRank.setDiscount(userRankUpdateDto.getDiscount());
        if(userRankUpdateDto.getLowerBound()!=null)userRank.setLowerBound(userRankUpdateDto.getLowerBound());
        if(userRankUpdateDto.getUpperBound()!=null)userRank.setUpperBound(userRankUpdateDto.getUpperBound());
        if(userRankUpdateDto.getName()!=null)userRank.setName(userRankUpdateDto.getName());

        userRankRepository.save(userRank);

        return "Successfully updated userRank";
    }

    @Override
    public String create(UserRankCreateDto userRankCreateDto) {
        UserRank userRank = new UserRank();
        userRank.setName(userRankCreateDto.getName());
        userRank.setDiscount(userRankCreateDto.getDiscount());
        userRank.setUpperBound(userRankCreateDto.getUpperBound());
        userRank.setLowerBound(userRankCreateDto.getLowerBound());

        userRankRepository.save(userRank);

        return "Successfully created rank";
    }

    @Override
    public String delete(Long id) {

        userRankRepository.delete(userRankRepository.findById(id).get());

        return "Successfully deleted rank";
    }
}
