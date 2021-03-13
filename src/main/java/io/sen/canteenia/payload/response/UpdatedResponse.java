package io.sen.canteenia.payload.response;

import org.springframework.stereotype.Component;

@Component
public class UpdatedResponse {

    String messsage = "Updated";

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
