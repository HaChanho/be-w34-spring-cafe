package com.kakao.cafe.application.user.service;

import com.kakao.cafe.application.user.dto.UpdateRequest;
import com.kakao.cafe.application.user.port.in.UpdateUserInfoUseCase;
import com.kakao.cafe.application.user.port.out.GetUserEntityPort;
import com.kakao.cafe.application.user.port.out.UpdateUserInfoPort;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.exceptions.IllegalEmailException;
import com.kakao.cafe.domain.user.exceptions.IllegalPasswordException;
import com.kakao.cafe.domain.user.exceptions.IllegalUserIdException;
import com.kakao.cafe.domain.user.exceptions.IllegalUserNameException;
import com.kakao.cafe.domain.user.exceptions.UserNotExistException;
import com.kakao.cafe.domain.user.exceptions.WrongPasswordException;

public class UpdateUserInfoService implements UpdateUserInfoUseCase {

    private final UpdateUserInfoPort updateUserInfoPort;
    private final GetUserEntityPort getUserEntityPort;

    public UpdateUserInfoService(UpdateUserInfoPort updateUserInfoPort, GetUserEntityPort getUserEntityPort) {
        this.updateUserInfoPort = updateUserInfoPort;
        this.getUserEntityPort = getUserEntityPort;
    }

    @Override
    public void updateUserInfo(String userId, UpdateRequest updateRequest)
        throws UserNotExistException, IllegalUserIdException, IllegalPasswordException, IllegalUserNameException, IllegalEmailException, WrongPasswordException {
        User user = getUserEntityPort.findUserByUserId(userId);
        user.checkPasswordMatching(updateRequest.getPassword());
        updateUserInfoPort.updateUser(userId, updateRequest);
    }
}
