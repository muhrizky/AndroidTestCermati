package id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Model.Allusermodel;
import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Model.ItemModel;
import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Network.ApiClient;
import id.ac.undip.ce.student.muhammadrizqi.androidtestcermati2.Network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHasil extends Fragment implements AdapterItem.MessageAdapterListener {


    private String mparam1;

    public FragmentHasil() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    AdapterItem adapter;
    LinearLayoutManager layoutManager ;
    private List<ItemModel> itemModels = new ArrayList<>();
    static String queryParamfrofragmen;
    static String ARG_PARAM1;

    //i get the query param in this part
    public static FragmentHasil newInstance(String param1) {
        FragmentHasil fragment = new FragmentHasil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_hasil, container, false);

        Toast.makeText(getActivity(),"queryParamfrofragmen "+queryParamfrofragmen,Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(),"ARGS "+ARG_PARAM1,Toast.LENGTH_SHORT).show();

        if (getArguments() != null) {
            mparam1 = getArguments().getString(ARG_PARAM1);
            Toast.makeText(getActivity(),"param1 "+mparam1,Toast.LENGTH_SHORT).show();

        }

        recyclerView = view.findViewById(R.id.hasil);
        adapter = new AdapterItem(Objects.requireNonNull(getActivity()).getApplicationContext(),itemModels, this) ;
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new Pagination(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
               // Toast.makeText(getActivity(),"di onload more",Toast.LENGTH_SHORT).show();
                getData(current_page);

            }
        });

        getData(1);

        return view;

    }

    private void getData(int current_page) {
        Toast.makeText(getActivity(),"page"+current_page,Toast.LENGTH_SHORT).show();
        ApiInterface service = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<Allusermodel> call=service.getdata(mparam1,current_page,15);
        call.enqueue(new Callback<Allusermodel>() {
            @Override
            public void onResponse(Call<Allusermodel> call, Response<Allusermodel> response) {
                if (response.body().getTotalCount()!=null){
//                    for (ItemModel messagemodel:response.body().getItems()) {
//                        itemModels.add(messagemodel);
//                    }
                    itemModels.addAll(response.body().getItems());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Allusermodel> call, Throwable t) {
                Toast.makeText(getActivity(),"unable to fetch JSON",Toast.LENGTH_SHORT).show();

            }
        });
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
        return false;
    }
}
