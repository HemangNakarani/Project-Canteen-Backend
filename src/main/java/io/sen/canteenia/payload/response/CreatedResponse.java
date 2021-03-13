package io.sen.canteenia.payload.response;

import org.springframework.stereotype.Component;

@Component
public class CreatedResponse {

    String messsage = "Created";

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
