package racingcar;

import java.util.*;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

public class Application {

    public static void main(String[] args) {
    	List<Car> cars = inputCars();
        int tryCount = inputTryCount();

        playGame(cars, tryCount);
        printWinners(cars); 
    }

    private static List<Car> inputCars() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String input = Console.readLine();

        CheckNamesInput(input);

        String[] split = input.split(",", -1);
        Set<String> usedNames = new HashSet<>();
        List<Car> cars = new ArrayList<>();

        for (String name : split) {
            CheckName(name, usedNames);
            cars.add(new Car(name));
        }

        return cars;
    }
    
    private static void CheckNamesInput(String input) {
        if (input == null || input.contains(" ")) {
            throw new IllegalArgumentException();
        }

        if (input.isBlank() || input.startsWith(",") || input.endsWith(",")) {
            throw new IllegalArgumentException();
        }
    }

    private static void CheckName(String name, Set<String> usedNames) {
        if (name.isEmpty() || name.length() > 5 || !usedNames.add(name)) {
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

    private static void playGame(List<Car> cars, int tryCount) {
        System.out.println();
        System.out.println("실행 결과");

        for (int i = 0; i < tryCount; i++) {
            moveCars(cars);
            printRound(cars);
        }
    }

    private static void moveCars(List<Car> cars) {
        for (Car car : cars) {
            car.move();
        }
    }

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

    private static void printWinners(List<String> names, int[] positions) {
        int max = Arrays.stream(positions).max().orElse(0);

        List<String> winners = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] == max) {
                winners.add(names.get(i));
            }
        }

        System.out.print("최종 우승자 : ");
        System.out.println(String.join(", ", winners));
    }
}

class Car {
	private final String name;
	private int position = 0;

	public Car(String name) {
		this.name = name;
	}

	public void move() {
        int rand = Randoms.pickNumberInRange(0, 9);
        if (rand >= 4) {
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