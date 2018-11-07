package com.sergeybelkin.test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sergeybelkin.test.pojo.Category;

import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener {

    TextInputEditText mSearchTextInput;
    Spinner mCategoriesSpin;
    ProgressDialog pd;

    private SearchPresenter mSearchPresenter;

    public void showCategories(List<Category> categories){
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategoriesSpin.setAdapter(adapter);

        hideProgress();
    }

    private void showProgress(){
        pd = new ProgressDialog(getContext());
        pd.setMessage("Загрузка категорий");
        pd.show();
    }

    private void hideProgress(){
        if (pd != null) {
            pd.dismiss();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBHelper dbHelper = DBHelper.getInstance(getContext());

        Model model = new Model(dbHelper);
        mSearchPresenter = new SearchPresenter(model);
        mSearchPresenter.attachView(this);

        showProgress();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchTextInput = view.findViewById(R.id.searchTextInput);
        mCategoriesSpin = view.findViewById(R.id.categoriesSpin);
        view.findViewById(R.id.submitBtn).setOnClickListener(this);
        mSearchPresenter.viewIsReady();

        return view;
    }

    @Override
    public void onClick(View v) {
        String keywords = mSearchTextInput.getText().toString().replace(" ", ",");
        String category = mCategoriesSpin.getSelectedItem().toString();
        //добавить проверку корректности ввода

        switch (v.getId()){
            case R.id.submitBtn:
                //todo передать
                break;
        }
    }
}
