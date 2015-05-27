package c03.ppl.hidupsehat.Client;

import java.util.List;

import c03.ppl.hidupsehat.Entity.BahanMakanan;
import c03.ppl.hidupsehat.Entity.ResepDanBahan;
import c03.ppl.hidupsehat.Entity.ResepMakanan;
import c03.ppl.hidupsehat.Entity.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by wahyuoi on 06/05/15.
 */
public interface HidupSehatClient {
    @GET("/users")
    public void getAllUsers(Callback<List<User>> call);

    @GET("/resepMakanan")
    public void getAllResep(Callback<List<ResepMakanan>> call);

    @POST("/addUser")
    public void postUser(@Body User user, Callback<User> call);

    @GET("/bahanMakananList")
    public void getAllBahan(Callback<List<BahanMakanan>> call);

    @GET("/resepDanBahan")
    public void getAllResepDanBahan(Callback<List<ResepDanBahan>> call);
}
