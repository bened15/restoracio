package com.jbd.utils;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Effect {

	public Effect() {
		// TODO Auto-generated constructor stub
	}

	public void applyFadeTransitionToTextField(TextField txtField) {
		FadeTransition ft = new FadeTransition(Duration.millis(2000), txtField);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		// ft.setCycleCount(1);
		// ft.setAutoReverse(true);
		ft.play();

	}

	public void applyTranslateTransitionToButton(Button b, double from, double to) {
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), b);
		translateTransition.setFromX(from);
		translateTransition.setToX(to);

		translateTransition.play();

	}

	public void applyFadeTransitionToRectangle(Pane rec) {
		FadeTransition ft = new FadeTransition(Duration.millis(2000), rec);
		ft.setFromValue(1.0);
		ft.setToValue(0.8);
		ft.setCycleCount(Timeline.INDEFINITE);
		// ft.setAutoReverse(true);
		ft.play();

	}

	public void applyFadeTransitionToButton(Button b) {
		FadeTransition ft = new FadeTransition(Duration.millis(500), b);
		ft.setFromValue(0.7);
		ft.setToValue(1.0);
		// ft.setAutoReverse(true);
		ft.play();

	}

}
