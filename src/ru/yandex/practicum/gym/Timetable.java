package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new EnumMap<>(DayOfWeek.class);

    public Timetable() {
        for (DayOfWeek d : DayOfWeek.values()) {
            timetable.put(d, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);

        List<TrainingSession> sessionsAtTime = daySchedule.computeIfAbsent(time, k -> new ArrayList<>());

        sessionsAtTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        return daySchedule;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay time) {

        return timetable.get(dayOfWeek).getOrDefault(time, List.of());
    }

    public List<CounterForCoach> getCountByCoaches() {

        Map<Coach, Integer> countMap = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> day : timetable.values()) {
            for (List<TrainingSession> sessions : day.values()) {
                for (TrainingSession session : sessions) {

                    Coach coach = session.getCoach();

                    countMap.put(coach, countMap.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterForCoach> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : countMap.entrySet()) {
            result.add(new CounterForCoach(entry.getKey(), entry.getValue()));
        }

        Collections.sort(result);

        return result;
    }
}
