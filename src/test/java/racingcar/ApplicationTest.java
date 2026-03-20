package racingcar;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    private static final int MOVING_FORWARD = 4;
    private static final int STOP = 3;

    @Test
    void 전진_정지() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi,woni", "1");
                assertThat(output()).contains("pobi : -", "woni : ", "최종 우승자 : pobi");
            },
            MOVING_FORWARD, STOP
        );
    }

    @Test
    void 이름에_대한_예외_처리() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,javaji", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 자동차_이름_5자_초과_예외() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,overfive", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 자동차_이름_공백_포함_예외() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi, woni", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 자동차_이름_빈값_예외() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,,woni", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 자동차_이름_중복_예외() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,pobi", "1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 시도_횟수_0_이하_예외() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,woni", "0"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 시도_횟수_숫자_아님_예외() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("pobi,woni", "abc"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 단독_우승자() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi,woni,jun", "1");
                assertThat(output()).contains("최종 우승자 : pobi");
            },
            MOVING_FORWARD, STOP, STOP
        );
    }

    @Test
    void 공동_우승자() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi,woni", "1");
                assertThat(output()).contains("최종 우승자 : pobi, woni");
            },
            MOVING_FORWARD, MOVING_FORWARD
        );
    }

    @Test
    void 모든_자동차_멈춤() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi,woni", "1");
                assertThat(output()).contains("pobi : ", "woni : ", "최종 우승자 : pobi, woni");
            },
            STOP, STOP
        );
    }

    @Test
    void 여러_라운드_진행() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi,woni", "3");
                String output = output();
                assertThat(output).contains("실행 결과");
                assertThat(output).contains("최종 우승자 :");
            },
            MOVING_FORWARD, STOP,
            MOVING_FORWARD, STOP,
            MOVING_FORWARD, STOP
        );
    }

    @Test
    void 한대의_자동차만_참가_전진() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi", "1");
                assertThat(output()).contains("pobi : -", "최종 우승자 : pobi");
            },
            MOVING_FORWARD
        );
    }

    @Test
    void 한대의_자동차만_참가_정지() {
        assertRandomNumberInRangeTest(
            () -> {
                run("pobi", "1");
                assertThat(output()).contains("pobi : ", "최종 우승자 : pobi");
            },
            STOP
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
