package racingcar;

import java.util.ArrayList;
import java.util.List;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

public class Application {

    public static void main(String[] args) {
    	List<Car> cars = inputCars();
        int tryCount = inputTryCount();

        playGame(cars, tryCount);
        printWinners(cars); 
    }

    private static List<String> inputNames() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String input = Console.readLine();

        if (input == null || input.contains(" ")) {
            throw new IllegalArgumentException();
        }

        if (input.isBlank() || input.startsWith(",") || input.endsWith(",")) {
            throw new IllegalArgumentException();
        }

        String[] split = input.split(",", -1);
        List<String> names = new ArrayList<>();
        Set<String> duplicateCheck = new HashSet<>();

        for (String name : split) {
            if (name.isEmpty() || name.length() > 5 || !duplicateCheck.add(name)) {
                throw new IllegalArgumentException();
            }
            names.add(name);
        }
        
        return names;
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

    private static void playGame(List<String> names, int[] positions, int tryCount) {
        System.out.println();
        System.out.println("실행 결과");

        for (int i = 0; i < tryCount; i++) {
            moveCars(positions);
            printRound(names, positions);
        }
    }

    private static void moveCars(int[] positions) {
        for (int i = 0; i < positions.length; i++) {
            int rand = Randoms.pickNumberInRange(0, 9);
            if (rand >= 4) {
                positions[i]++;
            }
        }
    }

    private static void printRound(List<String> names, int[] positions) {
        for (int i = 0; i < names.size(); i++) {
            System.out.print(names.get(i) + " : ");
            for (int j = 0; j < positions[i]; j++) {
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

	public void increase() {
		position++;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}
}