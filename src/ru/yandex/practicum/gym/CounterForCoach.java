package ru.yandex.practicum.gym;

public class CounterForCoach implements Comparable<CounterForCoach> {

    private Coach coach;
    private int count;

    public CounterForCoach(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    @Override
    public int compareTo(CounterForCoach other) {
        return Integer.compare(other.count, this.count);
    }
}