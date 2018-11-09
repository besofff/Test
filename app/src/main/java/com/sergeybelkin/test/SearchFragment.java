package com.sergeybelkin.test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sergeybelkin.test.pojo.Category;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener, Observable {

    private static final String ERROR_MESSAGE = "Поле ввода не должно быть пустым";
    private static final String EMPTY_STRING = "";

    TextInputLayout mTextInputLayout;
    EditText mSearchEditText;
    Spinner mCategoriesSpin;
    ProgressDialog pd;

    private SearchPresenter mSearchPresenter;

    List<Observer> mObservers = new ArrayList<>();

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
        pd.setCancelable(false);
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

        mTextInputLayout = view.findViewById(R.id.textInputLayout);
        mSearchEditText = view.findViewById(R.id.searchEditText);
        mCategoriesSpin = view.findViewById(R.id.categoriesSpin);
        view.findViewById(R.id.submitBtn).setOnClickListener(this);
        mSearchPresenter.viewIsReady();

        return view;
    }

    @Override
    public void onClick(View v) {
        String keywords = mSearchEditText.getText().toString()
                .replace(" ", ",");
        String category = mCategoriesSpin.getSelectedItem().toString()
                .toLowerCase().replace(" ", "_");

        if (keywords.isEmpty()){
            showError();
            return;
        } else {
            hideError();
        }

        switch (v.getId()){
            case R.id.submitBtn:
                notifyObservers(keywords, category);
                break;
        }
    }

    @Override
    public void addObserver(Observer o) {
        mObservers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        mObservers.remove(o);
    }

    @Override
    public void notifyObservers(String keywords, String category) {
        for (Observer o : mObservers){
            o.handleEvent(keywords, category);
        }
    }

    private void showError(){
        mTextInputLayout.setError(ERROR_MESSAGE);
    }

    private void hideError(){
        mTextInputLayout.setError(EMPTY_STRING);
    }
}
