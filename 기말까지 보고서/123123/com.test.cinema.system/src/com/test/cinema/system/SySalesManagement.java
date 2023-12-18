package com.test.cinema.system;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SySalesManagement {

    private Scanner scan = new Scanner(System.in);
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> regionList = new ArrayList<>();
    private List<String[]> salesList = new ArrayList<>();

    

    private File cinemaFile = new File("C:\\DDGCinema_data\\영화관목록.txt");
    private File salesFile = new File("C:\\DDGCinema_data\\시스템_극장별 매출 데이터.txt");

    public void salesPrint() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(cinemaFile));

            String line = null;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] regions = {"서울특별시", "경기도", "인천광역시", "강원도", "대전/충청시", "대구광역시", "부산광역시",
                "경상도", "광주광역시", "전라도", "제주도"};

        for (String region : regions) {
            regionList.add(region);
        }

        while (true) {
            System.out.println("지역을 선택하세요.");
            System.out.println("==============================");

            for (int i = 0; i < regionList.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, regionList.get(i));
            }

            System.out.println("==============================");
            System.out.println("0. 뒤로가기");
            System.out.println("==============================");
            System.out.print("입력 : ");

            String input = scan.nextLine();

            if (input.equals("0")) {
                break;
            } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) < regionList.size()) {
                for (String region : regionList) {
                    boolean flag = false;
                    for (int i = 0; i < list.size(); i++) {
                        String[] array = list.get(i).split("■");

                        if (region.equals(regionList.get(Integer.parseInt(input) - 1))) {
                            salesOutput(array[1]);
                            flag = true;
                            break;
                        }
                    }
                    if (flag) break;
                }
            } else {
                System.out.println("메뉴를 다시 입력하세요.");
            }
        }
    }

    private void salesSearch() {
        while (true) {
            System.out.println("==============================");
            System.out.println("찾으실 지점명을 입력하세요.");
            System.out.println("==============================");
            System.out.println("0. 뒤로가기");
            System.out.println("==============================");
            System.out.print("입력 : ");
            String input = scan.nextLine();

            if (input.equals("0")) {
                break;
            }

            for (String cinema : list) {
                if (cinema.contains(input)) {
                    System.out.println("=========================================================================================");
                    System.out.println("지점명\t영화매출액\t상품매출액\t총 매출액\n");

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(salesFile));
                        String line;
                        String[] array;

                        while ((line = reader.readLine()) != null) {
                            array = line.split("■");
                            if (array[1].equals(input)) {
                                int movieSales = Integer.parseInt(array[2]);
                                int foodSales = Integer.parseInt(array[3]);
                                int totalSales = movieSales + foodSales;
                                System.out.printf("%-7s\t%,5d\t%,5d\t%,5d\n", array[1], movieSales, foodSales, totalSales);
                            }
                        }

                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("=========================================================================================");
                    System.out.println("지점 검색 결과입니다.");
                    break;
                } else {
                    System.out.println("해당 지점이 없습니다.");
                }
            }
        }
    }

    private void salesOutput(String region) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(salesFile));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] array = line.split("■");
                salesList.add(array);
            }

            salesList = new ArrayList<>(salesList.stream()
                    .sorted(Comparator.comparing((String[] arr) ->
                            Integer.parseInt(arr[2]) + Integer.parseInt(arr[3]))
                            .reversed())
                    .collect(Collectors.toList()));


            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        movePage();

        while (true) {
            System.out.println("==============================");
            System.out.println("0. 뒤로가기");
            System.out.println("==============================");
            System.out.print("선택 : ");
            String num = scan.nextLine();

            switch (num) {
                case "0":
                    System.out.println("뒤로가기");
                    return;
                default:
                    System.out.println("올바른 선택지를 입력하세요.");
                    break;
            }
        }
    }

    private void movePage() {
        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) salesList.size() / pageSize);
        int currentPage = 1;

        while (true) {
            displayPage(currentPage, pageSize);

            System.out.println("==============================");
            System.out.println("0. 뒤로가기");
            System.out.println("1. 이전 페이지");
            System.out.println("2. 다음 페이지");
            System.out.println("==============================");
            System.out.print("선택 : ");
            String choice = scan.nextLine();

            switch (choice) {
                case "0":
                    return;
                case "1":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("첫 페이지입니다. 더 이상 이전 페이지로 이동할 수 없습니다.");
                    }
                    break;
                case "2":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("마지막 페이지입니다. 더 이상 다음 페이지로 이동할 수 없습니다.");
                    }
                    break;
                default:
                    System.out.println("올바른 선택지를 입력하세요.");
                    break;
            }
        }
    }

    private void displayPage(int currentPage, int pageSize) {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, salesList.size());

        System.out.println("==============================");
        System.out.println("[매출순위]\t[지점명]\t\t[영화매출액]\t[상품매출액]\t[총 매출액]");

        for (int i = startIndex; i < endIndex; i++) {
            System.out.printf("%3d\t\t%-7s\t\t%,10d\t%,10d\t%,10d\n", i + 1,
                    salesList.get(i)[1],
                    Integer.parseInt(salesList.get(i)[2]),
                    Integer.parseInt(salesList.get(i)[3]),
                    Integer.parseInt(salesList.get(i)[2]) + Integer.parseInt(salesList.get(i)[3]));
        }
    }
}
