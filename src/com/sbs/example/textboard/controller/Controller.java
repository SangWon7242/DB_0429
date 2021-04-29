package com.sbs.example.textboard.controller;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
	protected Connection conn;
	protected Scanner sc;
	// 상속 받은 놈들만 쓸 수 있도록
	
	public void setConn(Connection conn) {
		this.conn = conn;		
	}

	public void setScanner(Scanner sc) {
		this.sc = sc;		
	}
}
