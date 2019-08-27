
package com.sofra.sofra.data.model.updateProfileClient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sofra.sofra.data.model.Generated.GeneratedDataUser;

public class DataProfile {

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
