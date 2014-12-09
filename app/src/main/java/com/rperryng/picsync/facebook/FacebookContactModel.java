package com.rperryng.picsync.facebook;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ryan PerryNguyen on 2014-12-08.
 */
public class FacebookContactModel {

    public static final String TAG = FacebookContactModel.class.getSimpleName();

    @SerializedName("name")
    private String mName;

    @SerializedName("id")
    private String mId;

    @SerializedName("picture")
    private Picture mPicture;

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public Picture getPicture() {
        return mPicture;
    }

    public static List<FacebookContactModel> parseFacebookResponse(String rawJson) {
        return ApiResponse.parse(rawJson);
    }

    public static class Picture {

        @SerializedName("data")
        private Data mData;

        public String getUrl() {
            return mData.mUrl;
        }

        public boolean isSilhouette() {
            return mData.mIsSilhouette;
        }

        private static class Data {
            @SerializedName("is_silhouette")
            public boolean mIsSilhouette;

            @SerializedName("url")
            public String mUrl;
        }

    }

    private static class ApiResponse {

        @SerializedName("data")
        List<FacebookContactModel> listOfUsers;

        public static List<FacebookContactModel> parse(String rawJson) {
            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(rawJson, ApiResponse.class);
            return apiResponse.listOfUsers;
        }
    }
}
