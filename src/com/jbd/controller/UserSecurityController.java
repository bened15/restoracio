package com.jbd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.jbd.hibernate.interfaces.ISysUserRolManagement;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UserSecurityController {

	@Autowired
	private static ISysUserRolManagement manageUserRol;
	private static boolean isMgr = false;

	public UserSecurityController() {
		AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
	}

	public static boolean verifyIsMgr(AnchorPane ap) {

		Pane p = new Pane();

		TextField txtUserCode = new TextField();
		TextField txtPassCode = new TextField();

		p.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
					acbFactory.autowireBean(UserSecurityController.class);

					if (manageUserRol.findSysUserRol(txtUserCode.getText(), txtPassCode.getText())) {
						isMgr = true;
						ap.getChildren().remove(p);
						return;

					} else {

						isMgr = false;
						return;
					}

				}

				if (event.getCode().equals(KeyCode.ESCAPE)) {
					isMgr = false;
					return;
				}

			}
		});

		p.getChildren().add(txtUserCode);
		p.getChildren().add(txtPassCode);

		ap.getChildren().add(p);
		return isMgr;

	}

}
