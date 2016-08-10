package com.jbd.controller;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.jbd.hibernate.dao.SysUserRolManagementDAO;
import com.jbd.hibernate.interfaces.ISysUserRolManagement;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class UserSecurityController {

	@Autowired
	private ISysUserRolManagement manageSysUserRol;

	public UserSecurityController() {
		AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
	}

	public boolean verifyIsAuto() {

		String usA = JOptionPane.showInputDialog("Ingrese un usuario autorizado");
		String passA = JOptionPane.showInputDialog("Ingrese su password");
		if (manageSysUserRol.findSysUserRol(usA, passA)) {

			return true;
		} else {
			return false;
		}

	}

	public String verifyWaitress() {

		String usA = JOptionPane.showInputDialog("Ingrese su codigo de empleado");

		if (manageSysUserRol.findWaitress(usA)) {

			return usA;
		} else {
			return "";
		}

	}

	public void loadConfig() {

		AutowireCapableBeanFactory acbFactory = Main.context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);

	}

}
