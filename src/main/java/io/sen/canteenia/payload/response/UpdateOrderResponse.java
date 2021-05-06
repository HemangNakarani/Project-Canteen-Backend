package io.sen.canteenia.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.util.Date;

public class UpdateOrderResponse {

    private Long id;

    private String status;

    private String undatedAt;

    public UpdateOrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUndatedAt() {
        return undatedAt;
    }

    public void setUndatedAt(String undatedAt) {
        this.undatedAt = undatedAt;
    }
}
