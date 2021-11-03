package com.azhar.pendaftaranvaksin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.azhar.pendaftaranvaksin.repository.InputRepository;
import com.azhar.pendaftaranvaksin.ui.inputdata.ModelInput;

import java.util.List;

/**
 * Created by Azhar Rivaldi on 18-10-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class InputViewModel extends AndroidViewModel {

    private InputRepository repository;
    private LiveData<List<ModelInput>> allData;

    public InputViewModel(@NonNull Application application) {
        super(application);
        repository = new InputRepository(application);
        allData = repository.getAllData();
    }

    public void insert(ModelInput modelInput) {
        repository.insert(modelInput);
    }

    public void update(ModelInput modelInput) {
        repository.update(modelInput);
    }

    public void delete(ModelInput modelInput) {
        repository.delete(modelInput);
    }

    public void deleteAllData() {
        repository.deleteAllNotes();
    }

    public LiveData<List<ModelInput>> getAllData() {
        return allData;
    }

}
