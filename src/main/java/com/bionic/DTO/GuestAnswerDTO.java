package com.bionic.DTO;

import java.util.List;

/**
 * Created by rondo104 on 29.12.2015.
 */
public class GuestAnswerDTO {
    private String email;
    private String nickName;
    private String name;
    private List<UserAnswerDTO> userAnswerDTO;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserAnswerDTO> getUserAnswerDTO() {
        return userAnswerDTO;
    }

    public void setUserAnswerDTO(List<UserAnswerDTO> userAnswerDTO) {
        this.userAnswerDTO = userAnswerDTO;
    }
}
