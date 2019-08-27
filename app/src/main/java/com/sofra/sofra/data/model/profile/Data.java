
package com.sofra.sofra.data.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sofra.sofra.data.model.Generated.GeneratedDataUser;

public class Data {

    @SerializedName("user")
    @Expose
    private GeneratedDataUser user;

    public GeneratedDataUser getUser() {
        return user;
    }

    public void setUser(GeneratedDataUser user) {
        this.user = user;
    }

}
