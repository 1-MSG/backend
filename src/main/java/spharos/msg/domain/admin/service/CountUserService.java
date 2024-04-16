package spharos.msg.domain.admin.service;

import org.springframework.data.domain.Pageable;
import spharos.msg.domain.admin.dto.AdminResponseDto;

import java.util.List;

public interface CountUserService {
    public List<AdminResponseDto.SearchAllInfo> SearchUsersInfo(Pageable pageable);

    public AdminResponseDto.ConnectCount countConnectUser();

    public AdminResponseDto.UsersCount usersCount();

    public AdminResponseDto.UsersCount todaySignupCount();

    public AdminResponseDto.SecessionCount secessionCount();

    public List<List<AdminResponseDto.MonthlySignupCount>> monthSignupCount();

    public List<AdminResponseDto.SearchAllInfo> SearchUsersInfoByUserName(String userName);
}
