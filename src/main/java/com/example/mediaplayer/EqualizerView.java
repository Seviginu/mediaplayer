package com.example.mediaplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioEqualizer;
import javafx.scene.media.EqualizerBand;

import java.net.URL;
import java.util.ResourceBundle;

public class EqualizerView implements Initializable {
    @FXML
    private Slider band0, band1, band2, band3, band4, band5, band6, band7, band8, band9;
    @FXML
    private CheckBox isEnabledCheckBox;
    private AudioEqualizer equalizer;
    private Slider[] bands;

    public void setEqualizer(AudioEqualizer equalizer){
        this.equalizer = equalizer;
        for (int i = 0; i < 10; ++i){
            Slider band = bands[i];
            EqualizerBand equalizerBand = equalizer.getBands().get(i);
            band.setValue(equalizerBand.getGain());
            band.valueProperty().addListener((observableValue, number, t1) -> equalizerBand.setGain(band.getValue()));
        }
        isEnabledCheckBox.setSelected(equalizer.isEnabled());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bands = new Slider[]{band0, band1, band2, band3, band4, band5, band6, band7, band8, band9};

    }


    public void onEnable(ActionEvent event) {
        equalizer.setEnabled(isEnabledCheckBox.isSelected());
    }
}
