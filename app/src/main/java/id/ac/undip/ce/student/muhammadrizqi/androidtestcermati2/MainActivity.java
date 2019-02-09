package id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Network.ApiInterface;
import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Network.ApiClient;
import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Model.Allusermodel;
import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Model.ItemModel;
import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Pagination;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

import static android.support.v4.view.MenuItemCompat.getActionView;

public class MainActivity extends AppCompatActivity implements AdapterItem.MessageAdapterListener {

    private static String queryParamfrofragmen;

    private AdapterItem adapter;
    LinearLayoutManager layoutManager;
    private List<ItemModel> itemModels = new ArrayList<>();
    private Allusermodel allusermodel;
    RecyclerView recyclerView;
    String querysearch=null;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public String getQuerysearch() {
        return querysearch;
    }

    public void setQuerysearch(String querysearch) {
        this.querysearch = querysearch;
    }





    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
       super.onResume();


    }




    @Override
    public void onIconClicked(int position, String id, AdapterItem.MyViewHolder holder) {

    }

    @Override
    public void onIconImportantClicked(int position) {

    }

    @Override
    public void onMessageRowClicked(int position) {

    }

    @Override
    public void onRowLongClicked(int position) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem serachItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) serachItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                itemModels.clear();

                setQuerysearch(query);

                queryParamfrofragmen= query;


                getSupportFragmentManager().beginTransaction().replace(R.id.contenthasil, FragmentHasil.newInstance(query),"MyFragment").commit();







                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                 return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("ShowToast")
    public void  getdata(String Query, int curent_page){
        Toast.makeText(getApplicationContext(),"ini"+curent_page,Toast.LENGTH_SHORT).show();
        ApiInterface service = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<Allusermodel> call=service.getdata(Query,curent_page,10);
        call.enqueue(new Callback<Allusermodel>() {
            @Override
            public void onResponse(Call<Allusermodel> call, Response<Allusermodel> response) {
                if (response.body().getTotalCount()!=null){

                    itemModels.addAll(response.body().getItems());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Allusermodel> call, Throwable t) {

            }
        });


    }
}