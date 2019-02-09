package id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import  id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Model.Allusermodel;

import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users")
    Call<Allusermodel> getdata(@Query("q") String q,@Query("page")int page, @Query("per_page")int perpage);

//    @GET("userpict")
//    Call<Allusermodel> getdata(@Query())

}
