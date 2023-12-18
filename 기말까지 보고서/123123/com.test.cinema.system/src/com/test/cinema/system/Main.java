package com.test.cinema.system;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CinemaManagement cinemaManagement = new CinemaManagement();
        CustomerManagement customerManagement = new CustomerManagement();
        SySalesManagement sySalesManagement = new SySalesManagement();

        while (true) {
            System.out.println("==============================");
            System.out.println("1. 관리자 모드");
            System.out.println("2. 사용자 모드");
            System.out.println("0. 종료");
            System.out.println("==============================");
            System.out.print("모드 선택 : ");

            String modeInput = scanner.nextLine();

            switch (modeInput) {
                case "1":
                    adminMode(cinemaManagement, customerManagement, sySalesManagement, scanner);
                    break;
                case "2":
                    userMode(cinemaManagement, customerManagement, scanner);
                    break;
                case "0":
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("잘못된 모드를 선택하셨습니다. 다시 선택해주세요.");
            }
        }
    }

    private static void adminMode(
            CinemaManagement cinemaManagement,
            CustomerManagement customerManagement,
            SySalesManagement sySalesManagement,
            Scanner scanner
    ) {
        while (true) {
            System.out.println("==============================");
            System.out.println("1. 극장 관리");
            System.out.println("2. 회원 관리");
            System.out.println("0. 뒤로가기");
            System.out.println("==============================");
            System.out.print("메뉴 선택 : ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    cinemaManagement.CinemaList();
                    break;
                case "2":
                    customerManagement.customerList();
                    break;
                case "0":
                    System.out.println("뒤로 갑니다.");
                    return;
                default:
                    System.out.println("잘못된 메뉴를 선택하셨습니다. 다시 선택해주세요.");
            }
        }
    }

    private static void userMode(
            CinemaManagement cinemaManagement,
            CustomerManagement customerManagement,
            Scanner scanner
    ) {
        while (true) {
            System.out.println("==============================");
            System.out.println("1. 극장 정보 조회");
            System.out.println("2. 예매하기");
            System.out.println("3. 예매 조회");
            System.out.println("0. 뒤로가기");
            System.out.println("==============================");
            System.out.print("메뉴 선택 : ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    cinemaManagement.CinemaList(); // You may want to create a separate method for user cinema info
                    break;
                case "2":
                    // Implement user booking functionality
                    break;
                case "3":
                    // Implement user booking history retrieval
                    break;
                case "0":
                    System.out.println("뒤로 갑니다.");
                    return;
                default:
                    System.out.println("잘못된 메뉴를 선택하셨습니다. 다시 선택해주세요.");
            }
        }
    }
}
