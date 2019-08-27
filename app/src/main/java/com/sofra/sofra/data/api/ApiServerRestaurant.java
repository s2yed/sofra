package com.sofra.sofra.data.api;


import com.sofra.sofra.data.model.UpdateOffer.UpdateOffer;
import com.sofra.sofra.data.model.acceptOrder.AcceptOrder;
import com.sofra.sofra.data.model.addContact.AddContact;
import com.sofra.sofra.data.model.addOffer.AddOffer;
import com.sofra.sofra.data.model.addReview.AddReview;
import com.sofra.sofra.data.model.allRestaurants.AllRestaurants;
import com.sofra.sofra.data.model.categories.Categories;
import com.sofra.sofra.data.model.cities.Cities;
import com.sofra.sofra.data.model.clientRegister.ClientRegister;
import com.sofra.sofra.data.model.commissions.Commissions;
import com.sofra.sofra.data.model.deleteItem.DeleteItem;
import com.sofra.sofra.data.model.informationRestaurant.InformationRestaurant;
import com.sofra.sofra.data.model.loginRestaurant.LoginRestaurant;
import com.sofra.sofra.data.model.myItems.MyItems;
import com.sofra.sofra.data.model.myOffers.MyOffers;
import com.sofra.sofra.data.model.myOrders.MyOrders;
import com.sofra.sofra.data.model.newPassword.NewPassword;
import com.sofra.sofra.data.model.notifications.Notifications;
import com.sofra.sofra.data.model.notifyToken.NotifyToken;
import com.sofra.sofra.data.model.profile.Profile;
import com.sofra.sofra.data.model.regions.Regions;
import com.sofra.sofra.data.model.rejectOrder.RejectOrder;
import com.sofra.sofra.data.model.resetPassword.ResetPassword;
import com.sofra.sofra.data.model.restaurantRegister.RestauranttRegister;
import com.sofra.sofra.data.model.showOrder.ShowOrder;
import com.sofra.sofra.data.model.showReviews.ShowReviews;
import com.sofra.sofra.data.model.updateItem.UpdateItem;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServerRestaurant {

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<LoginRestaurant> onLogin(@Field("email") String phone, @Field("password") String password);

    @Multipart
    @POST("restaurant/register")
    Call<RestauranttRegister> getRestaurantRegister(@Part("name") RequestBody name, @Part("email") RequestBody email
            , @Part("password") RequestBody password, @Part("password_confirmation") RequestBody password_confirmation
            , @Part("phone") RequestBody phone, @Part("whatsapp") RequestBody whatsapp, @Part("region_id") RequestBody region_id
            , @Part("categories[]") List<RequestBody> categories, @Part("minimum_charger") RequestBody minimum_charger, @Part("delivery_cost") RequestBody delivery_cost,
                                                    @Part MultipartBody.Part image);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> resetPassword(@Field("email") String email);

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<NewPassword> newPassword(@Field("password") String password, @Field("password_confirmation") String password_confirmation
            , @Field("code") String pin_code);

    @GET("cities")
    Call<Cities> getCities();


    @GET("categories")
    Call<Categories> getCategories();

    @GET("restaurant/my-items")
    Call<MyItems> getItemRestaurant(@Query("api_token") String api_token, @Query("page") int page);


    @Multipart
    @POST("restaurant/update-item")
    Call<UpdateItem> UpdateItems(@Part("description") RequestBody description, @Part("price") RequestBody price
            , @Part("preparing_time") RequestBody preparing_time, @Part("name") RequestBody name
            , @Part("item_id") RequestBody item_id, @Part("offer_price") RequestBody offer_price, @Part("api_token") RequestBody api_token,
                                 @Part MultipartBody.Part image);

    @Multipart
    @POST("restaurant/new-item")
    Call<UpdateItem> NewItem(@Part("description") RequestBody description, @Part("price") RequestBody price
            , @Part("offer_price") RequestBody offer_price
            , @Part("preparing_time") RequestBody preparing_time, @Part("name") RequestBody name
            , @Part("api_token") RequestBody api_token,
                             @Part MultipartBody.Part image);


    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<DeleteItem> DeleteItemRestaurant(@Field("api_token") String api_token, @Field("item_id") int item_id);


    @GET("restaurant/my-orders")
    Call<MyOrders> getMyOrders(@Query("api_token") String api_token, @Query("state") String state, @Query("page") int page);


    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<AcceptOrder> acceptOrder(@Field("api_token") String api_token, @Field("order_id") int order_id);


    @POST("restaurant/reject-order")
    @FormUrlEncoded
    Call<RejectOrder> rejectOrder(@Field("api_token") String api_token, @Field("order_id") int order_id);


    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<RejectOrder> confirmOrder(@Field("api_token") String api_token, @Field("order_id") int order_id);


    @GET("restaurant/show-order")
    Call<ShowOrder> myShowOrder(@Query("api_token") String api_token, @Query("order_id") int order_id);


    @GET("restaurant/commissions")
    Call<Commissions> myShowOrder(@Query("api_token") String api_token);


    @GET("restaurant/my-offers")
    Call<MyOffers> getMyOffers(@Query("api_token") String api_token, @Query("page") int page);


    @Multipart
    @POST("restaurant/new-offer")
    Call<AddOffer> newOffer(@Part("description") RequestBody description, @Part("price") RequestBody price
            , @Part("starting_at") RequestBody starting_at, @Part("name") RequestBody name,
                            @Part MultipartBody.Part image, @Part("ending_at") RequestBody ending_at,
                            @Part("api_token") RequestBody api_token, @Part("offer_price") RequestBody offer_price);


    @POST("restaurant/delete-offer")
    @FormUrlEncoded
    Call<DeleteItem> deleteOffer(@Field("api_token") String api_token, @Field("offer_id") int offer_id);


    @Multipart
    @POST("restaurant/update-offer")
    Call<UpdateOffer> updateOffer(@Part("description") RequestBody description, @Part("price") RequestBody price
            , @Part("starting_at") RequestBody starting_at, @Part("name") RequestBody name,
                                  @Part MultipartBody.Part image, @Part("ending_at") RequestBody ending_at,
                                  @Part("api_token") RequestBody api_token, @Part("offer_id") RequestBody offer_id);

    @Multipart
    @POST("restaurant/profile")
    Call<RestauranttRegister> getEditRestaurantRegister(@Part("name") RequestBody name, @Part("email") RequestBody email
            , @Part("password") RequestBody password, @Part("password_confirmation") RequestBody password_confirmation
            , @Part("phone") RequestBody phone, @Part("whatsapp") RequestBody whatsapp, @Part("region_id") RequestBody region_id
            , @Part("categories[]") List<RequestBody> categories, @Part("minimum_charger") RequestBody minimum_charger
            , @Part("delivery_cost") RequestBody delivery_cost, @Part MultipartBody.Part image,
                                                        @Part("api_token") RequestBody api_token,@Part("availability") RequestBody availability);

    @POST("restaurant/profile")
    @FormUrlEncoded
    Call<Profile> getProfile(@Field("api_token") String api_token);



    @POST("restaurant/register-token")
    @FormUrlEncoded
    Call<NotifyToken>RegisterToken(@Field("token") String token
            , @Field("api_token") String api_token, @Field("type") String type);

    @POST("restaurant/remove-token")
    @FormUrlEncoded
    Call<NotifyToken>RemoveToken(@Field("token") String token, @Field("api_token") String api_token);

    @GET("restaurants")
    Call<AllRestaurants> getAllRestaurant(@Query("page")int page);

    @GET("restaurants")
    Call<AllRestaurants> getAllRestaurantFilter(@Query("keywork")String keywork,@Query("region_id")int region_id,@Query("page")int page);

    @GET("items")
    Call<MyItems> getItemRestaurantClient(@Query("restaurant_id") Integer restaurant_id, @Query("page") int page);


    @GET("restaurant/reviews")
    Call<ShowReviews> getReviewsRestaurantClient(@Query("api_token") String api_token, @Query("restaurant_id")
            int restaurant_id, @Query("page") int page);


    @GET("restaurant")
    Call<InformationRestaurant> getInformationRestaurantClient(@Query("restaurant_id")int restaurant_id);


    @GET("regions")
    Call<Regions> getRegions(@Query("city_id") int city_id);

    @POST("contact")
    @FormUrlEncoded
    Call<AddContact>addContact(@Field("name") String name, @Field("email") String email, @Field("phone") String phone
            , @Field("type") String type, @Field("content") String content);




    @GET("restaurant/notifications")
    Call<Notifications> getNotifications(@Query("api_token") String api_token, @Query("page") int page);

}
