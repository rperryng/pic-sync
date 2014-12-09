package com.rperryng.picsync.facebook;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Ryan PerryNguyen on 2014-12-08.
 */
public class FacebookContactModel {

    public static final String TAG = FacebookContactModel.class.getSimpleName();

    @SerializedName("first_name")
    private String mFirstName;

    @SerializedName("last_name")
    private String mLastName;

    @SerializedName("id")
    private String mId;

    @SerializedName("picture")
    private Picture mPicture;

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public String getId() {
        return mId;
    }

    public Picture getPicture() {
        return mPicture;
    }

    public static Comparator<FacebookContactModel> getAlhabeticalComparator() {
        return new Comparator<FacebookContactModel>() {
            @Override
            public int compare(FacebookContactModel lhs, FacebookContactModel rhs) {
                Character lhsFirstLetter = lhs.getFirstName().charAt(0);
                Character rhsFirstLetter = rhs.getFirstName().charAt(0);
                return lhsFirstLetter.compareTo(rhsFirstLetter);
            }
        };
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
