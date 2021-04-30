package com.sbs.example.textboard.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.sbs.example.textboard.service.MemberService;

public class MemberController extends Controller {

	private MemberService memberService;

	public MemberController(Connection conn, Scanner sc) {
		super(sc);

		memberService = new MemberService(conn);
	}

	public void join(String cmd) {
		String loginId;
		String loginPw;
		String loginPwConfirm;
		String name;

		System.out.println("==회원 가입==");
		// 아이디 입력
		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup) {
				System.out.printf("%s는 이미 사용중인 아이디입니다. 다시 입력해주세요.\n", loginId);
				continue;
			}

			break;
		}

		// 비밀번호 입력
		while (true) {
			System.out.printf("비밀번호 입력 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}

			boolean loginPwConfirmIsSame = true;

			while (true) {
				System.out.printf("로그인 비밀번호 확인 : ");
				loginPwConfirm = sc.nextLine().trim();

				if (loginPwConfirm.length() == 0) {
					System.out.println("비밀번호 확인을 입력해주세요.");
					continue;
				}

				if (loginPw.equals(loginPwConfirm) == false) {
					System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
					loginPwConfirmIsSame = false;
					break;
				}

				break;

			}
			if (loginPwConfirmIsSame) {
				break;
			}
		}
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();

			if (name.length() == 0) {
				System.out.println("이름을 입력해주세요.");
				continue;
			}
			break;
		}

		int id = memberService.join(loginId, loginPw, name);

		System.out.printf("%s님 환영합니다.\n", name);

	}

}
