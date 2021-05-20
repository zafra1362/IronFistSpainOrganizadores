package com.renegade;

import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class IFSViewModel extends ViewModel {
    public MutableLiveData<String> idEncuentroLiveData = new MutableLiveData<>();
    public MutableLiveData<String> nombreRival1LiveData = new MutableLiveData<>();
    public MutableLiveData<String> nombreRival2LiveData = new MutableLiveData<>();
    public MutableLiveData<Long> puntuacionRival1LiveData = new MutableLiveData<>();
    public MutableLiveData<Long> puntuacionRival2LiveData = new MutableLiveData<>();
    public MutableLiveData<String> hora1lLiveData = new MutableLiveData<>();
    public MutableLiveData<String> hora2LiveData = new MutableLiveData<>();
    public MutableLiveData<String> diasSeleccionadosLiveData = new MutableLiveData<>();
    public MutableLiveData<String> estadoRetoLiveData = new MutableLiveData<>();
}
