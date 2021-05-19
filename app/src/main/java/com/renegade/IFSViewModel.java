package com.renegade;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class IFSViewModel extends ViewModel {
    public MutableLiveData<String> idEncuentroLiveData = new MutableLiveData<>();
    public MutableLiveData<String> nombreRival1LiveData = new MutableLiveData<>();
    public MutableLiveData<String> nombreRival2LiveData = new MutableLiveData<>();
    public MutableLiveData<String> hora1lLiveData = new MutableLiveData<>();
    public MutableLiveData<String> hora2LiveData = new MutableLiveData<>();
    public MutableLiveData<String> diasSeleccionadosLiveData = new MutableLiveData<>();
    public MutableLiveData<String> uidRival1LiveData = new MutableLiveData<>();
    public MutableLiveData<String> uidRival2LiveData = new MutableLiveData<>();
}
