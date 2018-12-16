package com.qdemy.clase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class VariantaRaspuns {

    @Id (autoincrement = true) private Long id;
    @NotNull private String text;
    @NotNull private Boolean corect; // true=corect, false=gresit

    @NotNull private long intrebareId;

    //region Constructori

    public VariantaRaspuns() {}

    public VariantaRaspuns(String text, Boolean corect) {
        this.text = text;
        this.corect = corect;
    }

    @Generated(hash = 1343979047)
    public VariantaRaspuns(Long id, @NotNull String text, @NotNull Boolean corect,
            long intrebareId) {
        this.id = id;
        this.text = text;
        this.corect = corect;
        this.intrebareId = intrebareId;
    }

    //endregion

    //region GET, SET

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorect() {
        return corect;
    }

    public void setCorect(Boolean corect) {
        this.corect = corect;
    }

    public long getIntrebareId() {
        return intrebareId;
    }

    public void setIntrebareId(long intrebareId) {
        this.intrebareId = intrebareId;
    }

    //endregion
}
