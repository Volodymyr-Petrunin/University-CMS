package com.university.universitycms.controller.impl;

import com.university.universitycms.controller.impl.ScheduleController;
import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Lesson;
import com.university.universitycms.security.WebSecurityConfiguration;
import com.university.universitycms.service.LessonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScheduleController.class)
@Import(WebSecurityConfiguration.class)
class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    private final Course expectedCourse = new Course(1L, "IT", null);
    private final Group expectedGroup = new Group(1L, "A12");

    private final List<Lesson> lessons = List.of(
            new Lesson(1L, "ENG group 1", "A105", DayOfWeek.WEDNESDAY, LocalTime.of(11, 0), LocalTime.of(12, 0), expectedCourse, expectedGroup),
            new Lesson(2L, "ENG group 2", "A106", DayOfWeek.WEDNESDAY, LocalTime.of(9, 30), LocalTime.of(11, 30), expectedCourse, expectedGroup),
            new Lesson(3L, "ENG group 3", "A107", DayOfWeek.WEDNESDAY, LocalTime.of(15, 0), LocalTime.of(17, 0), expectedCourse, expectedGroup)
    );

    private final Map<String, List<Lesson>> expectedLesson = Map.of(
            "WEN/01/11", lessons
            );

    @Test
    @WithMockUser(roles = "STUDENT")
    void testDaySchedule_ShouldRenderOneDayScheduleViewWithExpectedLesson() throws Exception {
        when(lessonService.getLessonsByDayOfWeek()).thenReturn(expectedLesson);

        String urlTemplate = "/one-day-schedule";
        String attributeName = "oneDaySchedule";
        String viewName = "one-day-schedule";

        defaultPerform(urlTemplate, attributeName, viewName, expectedLesson);

        verify(lessonService, times(1)).getLessonsByDayOfWeek();
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void testWeekSchedule_ShouldRenderWeekScheduleViewWithExpectedLesson() throws Exception {
        when(lessonService.getLessonsForWeek()).thenReturn(expectedLesson);

        String urlTemplate = "/week-schedule";
        String attributeName = "weekSchedule";
        String viewName = "week-schedule";

        defaultPerform(urlTemplate, attributeName, viewName, expectedLesson);

        verify(lessonService, times(1)).getLessonsForWeek();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testMonthSchedule_ShouldRenderMonthScheduleViewWithExpectedLesson() throws Exception {
        when(lessonService.getLessonsForMonth()).thenReturn(expectedLesson);

        String urlTemplate = "/month-schedule";
        String attributeName = "monthSchedule";
        String viewName = "month-schedule";

        defaultPerform(urlTemplate, attributeName, viewName, expectedLesson);

        verify(lessonService, times(1)).getLessonsForMonth();
    }

    private void defaultPerform(String urlTemplate, String attributeName, String viewName, Map<String, List<Lesson>> expectedLesson) throws Exception{
        mockMvc.perform(get("/schedule" + urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("University-CMS")))
                .andExpect(model().attributeExists(attributeName))
                .andExpect(model().attribute(attributeName, expectedLesson))
                .andExpect(view().name(viewName));
    }
}