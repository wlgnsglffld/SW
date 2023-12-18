package com.test.cinema.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class CustomerManagement {

	Scanner scan = new Scanner(System.in);
	
	//고객의 전체 정보를 저장하는 배열
	ArrayList<String> list = new ArrayList<String>();
	
	//고객정보를 담은 파일
	File file = new File("C:\\DDGCinema_data\\고객정보.txt");
	
	public void customerList() {
		// 1. 회원 정보 불러오기
		// 2. 삭제시 삭제 메소드 불러오기
		
		try { //회원정보 불러서 저장
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		int page = 1;
		movePage(page,list);
		
		while(true) {

			System.out.println("========================================================================================");
			System.out.println("1. 다음 페이지");
			System.out.println("2. 이전 페이지");
			System.out.println("3. 회원 정보 검색");
			System.out.println("4. 회원 정보 삭제");
			System.out.println("5. 뒤로가기");
			


System.out.println("=========================================================================================");
			System.out.print("메뉴 선택 : ");

			String select = scan.nextLine();

			System.out.println("=========================================================================================");

			if (select.equals("1")) {
				// 1. 다음 페이지
				page++;
				movePage(page,list);
				continue;

			} else if (select.equals("2")) {
				// 2. 이전 페이지
				page--;
				movePage(page,list);
				continue;

			} else if (select.equals("3")) {
				// 3. 회원 정보 검색
				customerSearch();
				break;

			} else if (select.equals("4")) {
				// 4. 회원 정보 삭제
				customerDelete();
				continue;

			} else if (select.equals("5")) {
				//5. 로그아웃
				break;
			} else {
				// 잘못 누르셨습니다.
				System.out.println("메뉴를 다시 입력해주세요.");
				continue;
			}
		}//while	
	}
	
	private void customerSearch() {
		//회원 정보 검색하는 메소드
		
		System.out.println("검색할 필드를 고르세요.");
		System.out.println("1. 회원 번호");
		System.out.println("2. 아이디");
		System.out.println("3. 이름");
		System.out.println("4. 등급");

		System.out.println("==============================");
		System.out.print("선택 : ");
		String searchInput = scan.nextLine();
		
		System.out.println("==============================");
		System.out.print("검색어 : ");
		String search = scan.nextLine();
		
		//검색 결과를 담을 ArrayList
		ArrayList<String> searchResult = new ArrayList<String>();
		
		for(String s : list) {
			String[] line = s.split("■");
			
			switch(searchInput) {
			
			case "1" : //회원정보
				if(line[0].equals(search)) {
					searchResult.add(s);
				}
				break;
			case "2" : //아이디
				if(line[1].equals(search)) {
					searchResult.add(s);
				}
				break;
			case "3" : //이름
				if(line[3].contains(search)) {
					searchResult.add(s);
				}
				break;
			case "4" : //등급
				if(line[9].contains(search)) {
					searchResult.add(s);
				}
				break;
			}// switch
		} // for
		

		int page = 1;
		movePage(page, searchResult);
		
		while (true) {
			System.out.println("=========================================================================================");
			System.out.println("1. 다음페이지");
			System.out.println("2. 이전페이지");
			System.out.println("3. 삭제");
			System.out.println("4. 메뉴로 돌아가기");
			System.out.println("=========================================================================================");
			System.out.print("선택 : ");
			String num = scan.nextLine();

			switch (num) {

			case "1":
				page++;
				movePage(page, searchResult);
				break;
				
			case "2":
				page--;
				movePage(page, searchResult);
				break;
				
			case "3":
				customerDelete();
				return;
			case "4":
				return;
			}
		}//while

	}

	private void customerDelete() {
		//회원 정보 삭제하는 메소드
		
		System.out.println("삭제할 회원 번호를 선택해주세요.");
		System.out.println("==============================");
		System.out.print("번호 : ");
		String delNum = scan.nextLine();
		
		//삭제하려는 회원 번호 제외하고 다시 저장
		ArrayList<String> delList = new ArrayList<String>();
		
		for(String s : list) {
			
			String[] line = s.split("■");
//			list.removeIf(num -> line[0].equals(num+""));
			if(line[0].contains(delNum)) {
				continue;
				
			}
			
			delList.add(s);
		}
		
		list = delList;
		
		//회원정보 덮어쓰기
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			for(String s : delList) {
				writer.write(s);
				writer.newLine();
			}
			
			System.out.println("삭제가 완료되었습니다.");
			
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private boolean movePage(int pageNum, ArrayList<String> list) {
		// 페이지 출력하는 메소드

		// 검색결과 0일 경우 true
		if (list.size() == 0) {
			System.out.println("검색 결과가 없습니다.");
			return true;
		} else {

			// 최대 페이지 계산하기
			int maxPage = list.size() % 10 > 0 ? list.size() / 10 + 1 : list.size() / 10;

			// 유효한 페이지 아닐 경우
			if (pageNum <= 0) {
				pageNum = maxPage;
			} else if (pageNum > maxPage) {
				pageNum = 1;
			}

			System.out.printf("현재 %d 페이지 입니다.\n", pageNum);
			System.out.println("=========================================================================================");
			System.out.println("[회원번호]\t\t[아이디]\t[이름]\t[나이]\t[성별]\t[주소]\t\t\t[휴대폰번호]\t[이메일]\t\t\t[마일리지]\t\t[등급]");

			for (int i = (pageNum - 1) * 10; i < pageNum * 10; i++) {		          
                             //회원번호■아이디■비밀번호■이름■주민번호■주소■휴대폰번호■이메일주소■마일리지■등급
				

				if (i >= list.size()) {
					break;
				} else {

					String[] s = list.get(i).split("■");

					// 나이 구하기
					String year = s[4].substring(0, 2);
					Calendar c = Calendar.getInstance();
					int age = 0;
					
					if (year.indexOf("0") == 0) {
						// 00년생 ~
						age = c.get(Calendar.YEAR) - Integer.parseInt("20" + year) + 1;

					} else {
						age = c.get(Calendar.YEAR) - Integer.parseInt("19"+year) + 1;
					}
					


					// 성별 구하기
					int index = s[4].indexOf("-");
			    String gender = s[4].charAt(index + 1) == '1' || s[4].charAt(index + 1) == '3' ? "남" : "여";

					// 출력
				System.out.printf("%6s\t\t%5s \t%-3s\t%3d\t%-1s\t%-15s\t%s\t%s\t%d\t\t%s\n"
					, s[0],s[1], s[3], age, gender, s[5], s[6], s[7], Integer.parseInt(s[8]), s[9]);
				}
			} // for
		}
		return false;
	}//if	
}