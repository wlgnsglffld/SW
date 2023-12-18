package com.test.cinema.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CinemaManagement {
	
	private Scanner scan = new Scanner(System.in);
	private ArrayList<String> list = new ArrayList<String>(); //전체 파일 리스트 - 서울특별시■강남 경기도■수원...
	private ArrayList<String> regionList = new ArrayList<String>(); // 지역 리스트 - 서울, 경기 ...
	private ArrayList<String> cinemaList = new ArrayList<String>(); // 선택 지역의 상영관 리스트 - 잠실, 건대...
	
	//극장 목록 파일
	private File cinemaFile = new File("C:\\DDGCinema_data\\영화관목록.txt");
	//현재 강남점에서 상영중인 영화 정보 파일
	private File movieCinemaFile = new File("C:\\DDGCinema_data\\시스템_극장별 현재 상영 영화 목록.txt");
	//현재 상영중인 영화정보 파일
	private File movieFile = new File("C:\\DDGCinema_data\\시스템_현재 상영 영화 목록.txt");
	
	public void CinemaList() {
		// 1. 전국 극장 정보 불러오기
		// 2. 선택 지역 극장 불러오기
		// 3. 지정 극장 상영 영화 리스트 불러오기
		// 4. 기간 수정시 입력받기
		// 5. 영화 추가시 추가 메소드 불러오기

		try {
			BufferedReader reader = new BufferedReader(new FileReader(cinemaFile));

			String line = null;

			// 지역 리스트 뽑아서 저장
			while ((line = reader.readLine()) != null) {

				list.add(line);
//				String[] arrays = line.split("■");
//				regionList.add(arrays[0]);
			}
			

			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] regions = {"서울특별시","경기도","인천광역시", "강원도", "대전/충청시", "대구광역시", "부산광역시"
                                     ,"경상도", "광주광역시", "전라도", "제주도" };
		
		for(String region : regions) {
			regionList.add(region);
		}

		while (true) {

			System.out.println("극장에서 상영할 영화를 관리할 수 있습니다.");
			System.out.println("지역을 선택하세요.");
			System.out.println("==============================");

			// 지역 메뉴 출력
			for (int i = 0; i < regionList.size(); i++) {
				System.out.printf("%d. %s\n", i + 1, regionList.get(i));
			}

			System.out.println("==============================");
			System.out.println("0. 뒤로가기");
			System.out.println("==============================");
			System.out.print("입력 : ");

			String input = scan.nextLine();

			switch (input) {

			case "0":
				// 뒤로가기
				return;
			default:
				// 지역 선택시
				cinemaSelect(regionList.get(Integer.parseInt(input) - 1));
				break;
			}

		} // while
	}

	private void cinemaSelect(String selRegion) {
		//상영관 선택하는 메소드
		cinemaList.clear();
//		System.out.println("list size : "+list.size()); //157
		
		//선택 지역의 상영관 담기
	    for (int i = 0; i < list.size(); i++) {
	        String[] array = list.get(i).split("■");
	        if (array.length > 1 && array[0].equals(selRegion)) {
	            cinemaList.add(array[1]);
	        }
	    }//if
//			System.out.println("array::::"+array[0]);
//			System.out.println("array::::"+array[1]);
	    //for
		
//		System.out.println("selRegion:::"+selRegion);
//		System.out.println("cinemaList::::"+cinemaList);
		
		
		while (true) {
		    // 해당 지역 상영관 메뉴 출력
		    System.out.println("==============================");
		    System.out.println("상영관을 선택하세요.");
		    System.out.println("==============================");

		    for (int i = 0; i < cinemaList.size(); i++) {
		        System.out.printf("%d. %s\n", i + 1, cinemaList.get(i));
		    }

		    System.out.println("==============================");
		    System.out.println("0. 뒤로가기");
		    System.out.println("==============================");
		    System.out.print("입력 : ");

		    try {
		        int input = Integer.parseInt(scan.nextLine());

		        if (input == 0) {
		            // 뒤로가기
		            break;
		        } else if (input >= 1 && input <= cinemaList.size()) {
		            cinemaMovieList(cinemaList.get(input - 1));
		        } else {
		            System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
		        }
		    } catch (NumberFormatException e) {
		        // 숫자로 변환할 수 없는 입력이 들어왔을 경우
		        System.out.println("숫자를 입력해주세요.");
		    }
		}
	}

	private void cinemaMovieList(String cinema) {
		//선택한 극장의 현재 상영 중인 영화 목록 보여주는 메소드 
		while(true) {
			
			System.out.println("==============================");
			System.out.printf("[%s점] 현재 상영중인 영화 입니다.\n",cinema);
			System.out.println("==============================");
			
			try {
		        // 현재 상영 중인 영화 정보 파일 읽기
		        BufferedReader movieReader = new BufferedReader(new FileReader(movieCinemaFile));
		        String movieLine;

		        // 현재 상영 영화 담기
		        ArrayList<String> movieList = new ArrayList<String>();
		        while ((movieLine = movieReader.readLine()) != null) {
		            movieList.add(movieLine);
		        }
		        movieReader.close();
				
//				System.out.println("순위 ");
				for(int i=0; i< movieList.size(); i++) {
					String[] array = movieList.get(i).split("■");
					System.out.printf("%2d. %s\n",i+1, array[1]);
				}
				
				System.out.println("==============================");
				System.out.println("0. 뒤로가기");
				System.out.println("1. 영화 추가하기");
				System.out.println("2. 영화 삭제하기");
				System.out.println("==============================");
				System.out.print("입력 : ");
				String input = scan.nextLine();
				
				switch(input) {
				
				case "0":
					return;
				case "1":
					//영화 추가하기
					CinemaAdd(movieList);
					return;
				case "2":
					//영화 삭제하기
					CinemaDelete(movieList);
					return;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}//while
	}

	private void CinemaDelete(ArrayList<String> movieList) {
		//영화 삭제하는 메소드
		while(true) {
			
			System.out.println("삭제할 영화를 선택하세요.");
			System.out.println("==============================");
			System.out.println("번호\t장르\t영화제목");
			
			for(int i=0; i<movieList.size(); i++) {
				
				String[] array = movieList.get(i).split("■");
				
				

                                   System.out.printf("%2d\t%s\t%s\n", i+1, array[0],array[1]);		
			}
			System.out.println("==============================");
			System.out.println("0. 뒤로가기");
			System.out.println("==============================");
			System.out.print("입력 : ");
			String input = scan.nextLine();
			
			if(input.charAt(0) > '0' && Integer.parseInt(input) < movieList.size()) {
				movieList.remove(Integer.parseInt(input)-1);
				
				try {
				      BufferedWriter writer = new BufferedWriter(new FileWriter(movieCinemaFile));
					
					for(String movie : movieList) {
						writer.write(movie);
						writer.newLine();
					}
					
					writer.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				System.out.println("선택하신 영화가 삭제되었습니다.");
				break;
				
			}else if(input.charAt(0) == '0'){ //뒤로가기
				break;
			} else {
				System.out.println("정확한 메뉴를 입력하세요.");
				
			}
			
			
			
		}//while
		
	}

	public void CinemaAdd(ArrayList<String> movieList) {
		//영화 추가하는 메소드
		while(true){
			
			if(movieList.size() >= 10) {
				System.out.println("최대 상영 가능한 영화는 10개 입니다.");
				break;
			} else {
				
				System.out.println("추가할 영화를 골라주세요.");
				System.out.println("==============================");
				System.out.println("번호\t장르\t\t영화제목");
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader(movieFile));
					
					//현재 상영중인 총 영화 - 강남점 상영 영화
					ArrayList<String> addMovieList = new ArrayList<String>();
					String line = null;
					
					while((line = reader.readLine()) != null) {
						int flag = 0;
						for(String movie : movieList) {
							
							if(line.equals(movie)) {
								flag = 1;
							}
						}
						
						if(flag == 0) {
							addMovieList.add(line);		
						}
										}
										
					//추가 가능한 영화 출력
					for(int i=0; i<addMovieList.size(); i++) {
						String[] array = addMovieList.get(i).split("■");
						
						System.out.printf("%2d\t%-5s\t\t%s\n",i+1, array[0],array[1]);
					}
					
					System.out.println("==============================");
					System.out.println("0. 뒤로가기");
					System.out.println("==============================");
					System.out.print("입력 : ");
					String input = scan.nextLine();
					
					reader.close();
					
					if(input.equals("0")) {
						//뒤로가기
						break;
					} else if (
                                       Integer.parseInt(input)-1 > 0 && Integer.parseInt(input)-1 < addMovieList.size()) {
						//제대로 된 영화 골랐을 때
						
						BufferedWriter writer = new BufferedWriter(new FileWriter(
								movieCinemaFile));

						for (String movie : movieList) {
							writer.write(movie);
							writer.newLine();
						}

						writer.write(addMovieList.get(Integer.parseInt(input) - 1));
						writer.newLine();

						System.out.println("성공적으로 추가되었습니다.");

						writer.close();
						
						break;

					}else {
						System.out.println("메뉴를 다시 입력해주세요.");
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}//if-else
		}//while
	}
	
}