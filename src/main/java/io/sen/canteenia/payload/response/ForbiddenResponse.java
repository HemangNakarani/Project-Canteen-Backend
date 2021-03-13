package io.sen.canteenia.payload.response;

import org.springframework.stereotype.Component;

@Component
public class ForbiddenResponse {

    String messsage = "Forbidden For You";

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
