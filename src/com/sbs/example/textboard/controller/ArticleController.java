package com.sbs.example.textboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.textboard.Article;
import com.sbs.example.textboard.util.DBUtil;
import com.sbs.example.textboard.util.SecSql;

public class ArticleController extends Controller {
	
	// add-------------------------------------------------
	public void add(String cmd) {
		String title;
		String body;

		System.out.println("===게시물 생성===");
		System.out.printf("제목 : ");
		title = sc.nextLine();
		System.out.printf("내용 : ");
		body = sc.nextLine();

		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append(" SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", title = ?", title); // ? -> 치환이 된다.
		sql.append(", body = ?", body);

		int id = DBUtil.insert(conn, sql);

		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);	
	}
	
	// modify-------------------------------------------------
	public void modify(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]); // 정수로 만든 후 쪼개고 인덱스가 두번째를 id에 넣음
		String title;
		String body;

		System.out.printf("==%d번 게시글 수정==\n", id);
		System.out.printf("새 제목 : ");
		title = sc.nextLine();
		System.out.printf("새 내용 : ");
		body = sc.nextLine();

		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append(" SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", body = ?", body);
		sql.append("WHERE id = ?", id);

		DBUtil.update(conn, sql);

		System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
		
	}
	
	// showList -------------------------------------------------
	public void showList(String cmd) {
		System.out.println("==게시물 리스트==");

		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append(" FROM article");
		sql.append(" ORDER BY id DESC;");
		// select 는 모든 데이터가 List로 들어온다.

		List<Map<String, Object>> articlesListMap = DBUtil.selectRows(conn, sql);
		// Map<key, vlaue> 이런식의 형태
		// 쓰는 이유 : 편하게 쓰려고

		for (Map<String, Object> articleMap : articlesListMap) {
			articles.add(new Article(articleMap));
		}

		if (articles.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
			return;
		}
		System.out.println("번호  /  제목");

		for (Article article : articles) { // articles의 사이즈를 가져와서 article의 넣어라
			System.out.printf("%d  /  %s\n", article.id, article.title);
		}
		
	}
	
	// delete -------------------------------------------------
	public void delete(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("==%d번 게시글 삭제==\n", id);

		SecSql sql = new SecSql();

		sql.append("SELECT COUNT(*) AS cnt"); // 카운트가 article 카운트로 들어감
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		int articlesCount = DBUtil.selectRowIntValue(conn, sql);

		if (articlesCount == 0) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}

		sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		DBUtil.delete(conn, sql);

		System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
		
	}

	// showDetail -------------------------------------------------
	public void showDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("==%d번 게시글 상세보기==\n", id);

		SecSql sql = new SecSql();

		sql.append("SELECT *"); // all 로 받으면 int로 못받는다. 그러므로 셀렉로우로 받음
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

		if (articleMap.isEmpty()) {
			System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
			return;
		}

		Article article = new Article(articleMap);
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("등록날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		
	}
	
	
	

}
