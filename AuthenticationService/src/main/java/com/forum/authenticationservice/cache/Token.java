package com.forum.authenticationservice.cache;

import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

import static com.forum.authenticationservice.util.DateUtil.addHoursToDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Token {
    public String token;
    public Date dateCreated;
    public Date dateExpired;

    public Token() {
        this.token = RandomStringUtils.randomNumeric(6);
        this.dateCreated = new Date();
        this.dateExpired = addHoursToDate(this.dateCreated, 3);
    }

    public boolean isExpired() {
        return new Date().after(this.dateExpired);
    }

}
