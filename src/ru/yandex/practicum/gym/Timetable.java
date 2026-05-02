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

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        List<TrainingSession> result = new ArrayList<>();

        for (List<TrainingSession> sessions : daySchedule.values()) {
            result.addAll(sessions);
        }

        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay time) {

        return timetable.get(dayOfWeek).getOrDefault(time, List.of());
    }

    public List<Map.Entry<String, Integer>> getCountByCoaches() {

        Map<String, Integer> countMap = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> day : timetable.values()) {

            for (List<TrainingSession> sessions : day.values()) {

                for (TrainingSession session : sessions) {

                    String coach = session.getCoach().getFullName();

                    countMap.put(coach, countMap.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>(countMap.entrySet());

        result.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        return result;
    }
}
