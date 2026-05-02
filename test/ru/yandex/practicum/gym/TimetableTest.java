package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TimetableTest {

        @Test
        void testGetTrainingSessionsForDaySingleSession() {
                Timetable timetable = new Timetable();

                Group group = new Group("Акробатика для детей", Age.CHILD, 60);
                Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

                TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

                timetable.addNewTrainingSession(session);

                List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
                List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

                Assertions.assertEquals(1, monday.size());
                Assertions.assertEquals(session, monday.get(0));
                Assertions.assertTrue(tuesday.isEmpty());
        }

        @Test
        void testGetTrainingSessionsForDayMultipleSessions() {
                Timetable timetable = new Timetable();

                Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

                Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
                Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

                TrainingSession thursday20 = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY,
                                new TimeOfDay(20, 0));

                TrainingSession monday13 = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY,
                                new TimeOfDay(13, 0));

                TrainingSession thursday13 = new TrainingSession(groupChild, coach, DayOfWeek.THURSDAY,
                                new TimeOfDay(13, 0));

                TrainingSession saturday10 = new TrainingSession(groupChild, coach, DayOfWeek.SATURDAY,
                                new TimeOfDay(10, 0));

                timetable.addNewTrainingSession(thursday20);
                timetable.addNewTrainingSession(monday13);
                timetable.addNewTrainingSession(thursday13);
                timetable.addNewTrainingSession(saturday10);

                // MONDAY
                List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
                Assertions.assertEquals(1, monday.size());
                Assertions.assertEquals(monday13, monday.get(0));

                // THURSDAY (проверка сортировки)
                List<TrainingSession> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
                Assertions.assertEquals(2, thursday.size());
                Assertions.assertEquals(thursday13, thursday.get(0)); // 13:00
                Assertions.assertEquals(thursday20, thursday.get(1)); // 20:00

                // TUESDAY
                List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
                Assertions.assertTrue(tuesday.isEmpty());
        }

        @Test
        void testGetTrainingSessionsForDayAndTime() {
                Timetable timetable = new Timetable();

                Group group = new Group("Акробатика для детей", Age.CHILD, 60);
                Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

                TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

                timetable.addNewTrainingSession(session);

                List<TrainingSession> at13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                                new TimeOfDay(13, 0));

                List<TrainingSession> at14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                                new TimeOfDay(14, 0));

                Assertions.assertEquals(1, at13.size());
                Assertions.assertEquals(session, at13.get(0));
                Assertions.assertTrue(at14.isEmpty());
        }

        @Test
        void testMultipleSessionsSameTime() {
                Timetable timetable = new Timetable();

                Coach coach = new Coach("Иванов", "Иван", "Иванович");
                Group group = new Group("Акробатика", Age.CHILD, 60);

                TrainingSession s1 = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));
                TrainingSession s2 = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(10, 0));

                timetable.addNewTrainingSession(s1);
                timetable.addNewTrainingSession(s2);

                List<TrainingSession> result = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                                new TimeOfDay(10, 0));

                Assertions.assertEquals(2, result.size());
        }

        @Test
        void testEmptyTimetable() {
                Timetable timetable = new Timetable();

                List<TrainingSession> result = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

                Assertions.assertTrue(result.isEmpty());
        }

        @Test
        static void testDifferentCoaches() {
                Timetable timetable = new Timetable();

                Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
                Coach coach2 = new Coach("Петров", "Пётр", "Петрович");

                Group group = new Group("Акробатика", Age.ADULT, 60);

                timetable.addNewTrainingSession(
                                new TrainingSession(group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

                timetable.addNewTrainingSession(
                                new TrainingSession(group, coach2, DayOfWeek.FRIDAY, new TimeOfDay(12, 0)));

                List<TrainingSession> result = timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);

                Assertions.assertEquals(2, result.size());
        }
}