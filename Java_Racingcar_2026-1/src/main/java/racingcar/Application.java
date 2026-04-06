package racingcar;

import java.util.*;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Application {

    public static void main(String[] args) {
        List<Car> cars = inputCars(); // [리팩토링] 객체로 변경
        int tryCount = inputTryCount();

        playGame(cars, tryCount);
        printWinners(cars);
    }

    // ---------------- 입력 ----------------

    private static List<Car> inputCars() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String input = Console.readLine();

        validateNamesInput(input); // [리팩토링] 검증 분리

        String[] split = input.split(",", -1);
        Set<String> duplicateCheck = new HashSet<>();
        List<Car> cars = new ArrayList<>();

        for (String name : split) {
            validateName(name, duplicateCheck); // [리팩토링] 개별 검증 분리
            cars.add(new Car(name)); // [리팩토링] 객체 생성
        }

        return cars;
    }

    // [리팩토링] 기존 검증 로직 그대로 분리
    private static void validateNamesInput(String input) {
        if (input == null || input.contains(" ")) {
            throw new IllegalArgumentException();
        }

        if (input.isBlank() || input.startsWith(",") || input.endsWith(",")) {
            throw new IllegalArgumentException();
        }
    }

    // [리팩토링] 기존 조건 그대로 유지
    private static void validateName(String name, Set<String> duplicateCheck) {
        if (name.isEmpty() || name.length() > 5 || !duplicateCheck.add(name)) {
            throw new IllegalArgumentException();
        }
    }

    private static int inputTryCount() {
        System.out.println("시도할 회수는 몇회인가요?");
        String input = Console.readLine();

        try {
            int count = Integer.parseInt(input);
            if (count <= 0) {
                throw new IllegalArgumentException("시도 횟수는 1 이상이어야 합니다.");
            }
            return count;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }

    // ---------------- 게임 진행 ----------------

    private static void playGame(List<Car> cars, int tryCount) {
        System.out.println();
        System.out.println("실행 결과");

        for (int i = 0; i < tryCount; i++) {
            moveCars(cars);   // [리팩토링] 이동 분리
            printRound(cars); // [리팩토링] 출력 분리
        }
    }

    // [리팩토링] 이동만 담당
    private static void moveCars(List<Car> cars) {
        for (Car car : cars) {
            car.move();
        }
    }

    // [리팩토링] 출력만 담당
    private static void printRound(List<Car> cars) {
        for (Car car : cars) {
            System.out.print(car.getName() + " : ");
            for (int j = 0; j < car.getPosition(); j++) {
                System.out.print("-");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ---------------- 결과 ----------------

    private static void printWinners(List<Car> cars) {
        int max = 0;

        for (Car car : cars) {
            if (car.getPosition() > max) {
                max = car.getPosition();
            }
        }

        List<String> winners = new ArrayList<>();
        for (Car car : cars) {
            if (car.getPosition() == max) {
                winners.add(car.getName());
            }
        }

        System.out.print("최종 우승자 : ");
        System.out.println(String.join(", ", winners));
    }
}

// ---------------- 객체 ----------------

// [리팩토링] positions 배열 → 객체로 변경
class Car {
    private final String name;
    private int position = 0;

    public Car(String name) {
        this.name = name;
    }

    // [리팩토링] 이동 로직을 객체 내부로 이동
    public void move() {
        int rand = Randoms.pickNumberInRange(0, 9);
        if (rand >= 4) { // 기존 로직 유지
            position++;
        }
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}