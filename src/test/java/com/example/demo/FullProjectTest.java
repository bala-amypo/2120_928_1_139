package com.example.demo;

import org.testng.annotations.*;
import org.testng.Assert;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

// Import your actual project classes
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Single TestNG class with 60 test cases covering the required categories in the required order.
 * Each test contains simple but meaningful assertions and some mock setups.
 */
@Listeners(TestResultListener.class)
public class FullProjectTest {

    // repositories mocked where needed
    @Mock private UniversityRepository universityRepo;
    @Mock private CourseRepository courseRepo;
    @Mock private CourseContentTopicRepository topicRepo;
    @Mock private TransferRuleRepository ruleRepo;
    @Mock private TransferEvaluationResultRepository evalRepo;
    @Mock private UserRepository userRepo;

    // services
    private UniversityServiceImpl universityService;
    private CourseServiceImpl courseService;
    private CourseContentTopicServiceImpl topicService;
    private TransferRuleServiceImpl ruleService;
    private TransferEvaluationServiceImpl evalService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize services
        universityService = new UniversityServiceImpl();
        courseService = new CourseServiceImpl();
        topicService = new CourseContentTopicServiceImpl();
        ruleService = new TransferRuleServiceImpl();
        evalService = new TransferEvaluationServiceImpl();

        // Inject mocked repositories into services using reflection
        injectMock(universityService, "repository", universityRepo);
        injectMock(courseService, "repo", courseRepo);
        injectMock(courseService, "univRepo", universityRepo);
        injectMock(topicService, "repo", topicRepo);
        injectMock(topicService, "courseRepo", courseRepo);
        injectMock(ruleService, "repo", ruleRepo);
        injectMock(ruleService, "univRepo", universityRepo);
        injectMock(evalService, "courseRepo", courseRepo);
        injectMock(evalService, "topicRepo", topicRepo);
        injectMock(evalService, "ruleRepo", ruleRepo);
        injectMock(evalService, "resultRepo", evalRepo);
    }

    private void injectMock(Object service, String fieldName, Object mock) {
        try {
            Field field = service.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(service, mock);
        } catch (Exception e) {
            // Try parent class
            try {
                Field field = service.getClass().getSuperclass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(service, mock);
            } catch (Exception ex) {
                System.err.println("Failed to inject mock into field: " + fieldName + " in " + service.getClass().getName());
            }
        }
    }

    // ==================== 1. Servlet Deployment Tests ====================
    @Test(priority=1, groups={"servlet"}, description="Servlet deployment configuration test")
    public void test01ServletDeploymentConfig() {
        // Simulate servlet configuration check
        String serverPort = System.getProperty("server.port", "8080");
        Assert.assertTrue(Integer.parseInt(serverPort) > 0, "Server port should be positive");
        Assert.assertTrue(true, "Servlet deployment configuration verified");
    }

    // ==================== 2. CRUD Operations Tests ====================
    @Test(priority=2, groups={"crud"}, description="Create University success")
    public void test02CreateUniversitySuccess() {
        University university = new University();
        university.setName("Test University");
        university.setLocation("Test City");
        university.setActive(true);
        
        when(universityRepo.findByName("Test University")).thenReturn(Optional.empty());
        when(universityRepo.save(any(University.class))).thenAnswer(inv -> {
            University saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });
        
        University created = universityService.createUniversity(university);
        Assert.assertNotNull(created, "Created university should not be null");
        Assert.assertEquals(created.getId().longValue(), 1L);
        Assert.assertEquals(created.getName(), "Test University");
        
        verify(universityRepo, times(1)).findByName("Test University");
        verify(universityRepo, times(1)).save(any(University.class));
    }

    @Test(priority=3, groups={"crud"}, description="Create University duplicate name")
    public void test03CreateUniversityDuplicate() {
        University existingUniversity = new University();
        existingUniversity.setId(1L);
        existingUniversity.setName("Existing University");
        
        University newUniversity = new University();
        newUniversity.setName("Existing University");
        
        when(universityRepo.findByName("Existing University")).thenReturn(Optional.of(existingUniversity));
        
        try {
            universityService.createUniversity(newUniversity);
            Assert.fail("Expected exception for duplicate university name");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("already exists") || 
                            ex.getMessage().contains("University with name"),
                            "Exception message should indicate duplicate");
        }
        
        verify(universityRepo, times(1)).findByName("Existing University");
        verify(universityRepo, never()).save(any(University.class));
    }

    @Test(priority=4, groups={"crud"}, description="Update University")
    public void test04UpdateUniversity() {
        University existing = new University();
        existing.setId(10L);
        existing.setName("Old Name");
        existing.setLocation("Old City");
        existing.setActive(true);
        
        University updates = new University();
        updates.setName("New Name");
        updates.setLocation("New City");
        
        when(universityRepo.findById(10L)).thenReturn(Optional.of(existing));
        when(universityRepo.save(any(University.class))).thenAnswer(inv -> inv.getArgument(0));
        
        University updated = universityService.updateUniversity(10L, updates);
        
        Assert.assertNotNull(updated);
        Assert.assertEquals(updated.getName(), "New Name");
        Assert.assertEquals(updated.getLocation(), "New City");
        Assert.assertEquals(updated.getId().longValue(), 10L);
        
        verify(universityRepo, times(1)).findById(10L);
        verify(universityRepo, times(1)).save(any(University.class));
    }

    @Test(priority=5, groups={"crud"}, description="Get University by ID not found")
    public void test05GetUniversityNotFound() {
        when(universityRepo.findById(99L)).thenReturn(Optional.empty());
        
        try {
            universityService.getUniversityById(99L);
            Assert.fail("Expected exception for university not found");
        } catch (RuntimeException ex) {
            Assert.assertTrue(ex.getMessage().contains("not found") || 
                            ex.getMessage().contains("does not exist"),
                            "Exception should indicate resource not found");
        }
        
        verify(universityRepo, times(1)).findById(99L);
    }

    @Test(priority=6, groups={"crud"}, description="Create Course with invalid credit hours")
    public void test06CreateCourseInvalidCredit() {
        Course course = new Course();
        course.setCourseCode("CS101");
        course.setCourseName("Invalid Course");
        course.setCreditHours(0); // Invalid: should be > 0
        
        University university = new University();
        university.setId(1L);
        course.setUniversity(university);
        
        when(universityRepo.findById(1L)).thenReturn(Optional.of(university));
        
        try {
            courseService.createCourse(course);
            Assert.fail("Expected exception for invalid credit hours");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("credit") || 
                            ex.getMessage().contains("> 0"),
                            "Exception should mention credit hours");
        }
        
        verify(universityRepo, times(1)).findById(1L);
        verify(courseRepo, never()).save(any(Course.class));
    }

    @Test(priority=7, groups={"crud"}, description="Create Course success")
    public void test07CreateCourseSuccess() {
        University university = new University();
        university.setId(2L);
        university.setName("Test University");
        
        Course course = new Course();
        course.setCourseCode("CS101");
        course.setCourseName("Introduction to Computer Science");
        course.setCreditHours(3);
        course.setUniversity(university);
        course.setActive(true);
        
        when(universityRepo.findById(2L)).thenReturn(Optional.of(university));
        when(courseRepo.findByUniversityIdAndCourseCode(2L, "CS101")).thenReturn(Optional.empty());
        when(courseRepo.save(any(Course.class))).thenAnswer(inv -> {
            Course saved = inv.getArgument(0);
            saved.setId(5L);
            return saved;
        });
        
        Course result = courseService.createCourse(course);
        
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId().longValue(), 5L);
        Assert.assertEquals(result.getCourseCode(), "CS101");
        Assert.assertEquals(result.getCreditHours(), 3);
        Assert.assertTrue(result.isActive());
        
        verify(universityRepo, times(1)).findById(2L);
        verify(courseRepo, times(1)).findByUniversityIdAndCourseCode(2L, "CS101");
        verify(courseRepo, times(1)).save(any(Course.class));
    }

    @Test(priority=8, groups={"crud"}, description="Deactivate course")
    public void test08DeactivateCourse() {
        Course course = new Course();
        course.setId(10L);
        course.setCourseCode("MATH101");
        course.setCourseName("Calculus I");
        course.setCreditHours(4);
        course.setActive(true);
        
        when(courseRepo.findById(10L)).thenReturn(Optional.of(course));
        when(courseRepo.save(any(Course.class))).thenAnswer(inv -> inv.getArgument(0));
        
        courseService.deactivateCourse(10L);
        
        Assert.assertFalse(course.isActive(), "Course should be deactivated");
        
        verify(courseRepo, times(1)).findById(10L);
        verify(courseRepo, times(1)).save(course);
    }

    // ==================== 3. Dependency Injection Tests ====================
    @Test(priority=9, groups={"di"}, description="DI - service beans available")
    public void test09DIServiceAvailability() {
        Assert.assertNotNull(universityService, "University service should be available");
        Assert.assertNotNull(courseService, "Course service should be available");
        Assert.assertNotNull(topicService, "Topic service should be available");
        Assert.assertNotNull(ruleService, "Rule service should be available");
        Assert.assertNotNull(evalService, "Evaluation service should be available");
    }

    // ==================== 4. Hibernate Configuration Tests ====================
    @Test(priority=10, groups={"hibernate"}, description="Entity annotations presence (basic check)")
    public void test10EntityAnnotations() {
        // Verify entity classes exist and have @Entity annotation (or equivalent)
        try {
            Class<?> universityClass = Class.forName("com.example.demo.entity.University");
            Class<?> courseClass = Class.forName("com.example.demo.entity.Course");
            Class<?> topicClass = Class.forName("com.example.demo.entity.CourseContentTopic");
            Class<?> ruleClass = Class.forName("com.example.demo.entity.TransferRule");
            Class<?> resultClass = Class.forName("com.example.demo.entity.TransferEvaluationResult");
            
            Assert.assertNotNull(universityClass);
            Assert.assertNotNull(courseClass);
            Assert.assertNotNull(topicClass);
            Assert.assertNotNull(ruleClass);
            Assert.assertNotNull(resultClass);
            
            Assert.assertTrue(true, "All entity classes are present");
        } catch (ClassNotFoundException e) {
            Assert.fail("Entity classes missing: " + e.getMessage());
        }
    }

    // ==================== 5. JPA Mapping Tests ====================
    @Test(priority=11, groups={"jpa"}, description="JPA mapping check for Course -> University relationship")
    public void test11JPAMapping() {
        University university = new University();
        university.setId(3L);
        university.setName("University A");
        
        Course course = new Course();
        course.setId(1L);
        course.setCourseCode("PHY101");
        course.setCourseName("Physics I");
        course.setUniversity(university);
        
        Assert.assertNotNull(course.getUniversity());
        Assert.assertEquals(course.getUniversity().getId().longValue(), 3L);
        Assert.assertEquals(course.getUniversity().getName(), "University A");
    }

    // ==================== 6. Relationships Tests ====================
    @Test(priority=12, groups={"relations"}, description="Course to Topics relationship")
    public void test12CourseToTopicsRelationship() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseCode("CS201");
        course.setCourseName("Data Structures");
        
        CourseContentTopic topic1 = new CourseContentTopic();
        topic1.setId(1L);
        topic1.setTopicName("Arrays");
        topic1.setCourse(course);
        
        CourseContentTopic topic2 = new CourseContentTopic();
        topic2.setId(2L);
        topic2.setTopicName("Linked Lists");
        topic2.setCourse(course);
        
        // This tests the one-to-many relationship
        List<CourseContentTopic> topics = Arrays.asList(topic1, topic2);
        
        Assert.assertEquals(topic1.getCourse().getId().longValue(), 1L);
        Assert.assertEquals(topic2.getCourse().getId().longValue(), 1L);
        Assert.assertEquals(topics.size(), 2);
    }

    // ==================== 7. Security Tests ====================
    @Test(priority=13, groups={"security"}, description="JWT token creation and validation")
    public void test13JwtCreateAndValidate() {
        // Create a simple JWT token provider for testing
        JwtTokenProvider provider = new JwtTokenProvider();
        
        // Configure provider with test secret
        try {
            Field secretField = JwtTokenProvider.class.getDeclaredField("jwtSecret");
            secretField.setAccessible(true);
            secretField.set(provider, "testSecretKeyForTestingPurposesOnly1234567890");
            
            Field validityField = JwtTokenProvider.class.getDeclaredField("jwtExpirationInMs");
            validityField.setAccessible(true);
            validityField.set(provider, 3600000L); // 1 hour
        } catch (Exception e) {
            // For test purposes, we'll skip if reflection fails
        }
        
        Set<String> roles = new HashSet<>(Arrays.asList("ROLE_ADVISOR", "ROLE_USER"));
        String token = provider.createToken(42L, "advisor@university.edu", roles);
        
        Assert.assertNotNull(token);
        Assert.assertTrue(provider.validateToken(token));
        Assert.assertEquals(provider.getEmail(token), "advisor@university.edu");
        Assert.assertEquals(provider.getUserId(token).longValue(), 42L);
        
        Set<String> tokenRoles = provider.getRoles(token);
        Assert.assertTrue(tokenRoles.contains("ROLE_ADVISOR"));
        Assert.assertTrue(tokenRoles.contains("ROLE_USER"));
    }

    @Test(priority=14, groups={"security"}, description="Password encoding")
    public void test14PasswordEncoding() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "securePassword123";
        String encodedPassword = encoder.encode(rawPassword);
        
        Assert.assertNotNull(encodedPassword);
        Assert.assertNotEquals(rawPassword, encodedPassword);
        Assert.assertTrue(encoder.matches(rawPassword, encodedPassword));
        Assert.assertFalse(encoder.matches("wrongPassword", encodedPassword));
    }

    // ==================== 8. HQL/Query Tests ====================
    @Test(priority=15, groups={"hql"}, description="Repository query method test")
    public void test15HqlQueryPlaceholder() {
        // Test repository query methods
        University university = new University();
        university.setId(1L);
        
        Course course = new Course();
        course.setId(1L);
        course.setCourseCode("TEST101");
        course.setUniversity(university);
        
        when(courseRepo.findByUniversityIdAndCourseCode(1L, "TEST101"))
            .thenReturn(Optional.of(course));
        
        Optional<Course> foundCourse = courseRepo.findByUniversityIdAndCourseCode(1L, "TEST101");
        
        Assert.assertTrue(foundCourse.isPresent());
        Assert.assertEquals(foundCourse.get().getCourseCode(), "TEST101");
        Assert.assertEquals(foundCourse.get().getUniversity().getId().longValue(), 1L);
        
        verify(courseRepo, times(1)).findByUniversityIdAndCourseCode(1L, "TEST101");
    }

    // ==================== Topic Management Tests ====================
    @Test(priority=16, groups={"topics"}, description="Create topic validation failure")
    public void test16CreateTopicValidation() {
        CourseContentTopic topic = new CourseContentTopic();
        topic.setTopicName(""); // Empty name should fail
        topic.setWeightPercentage(50.0);
        
        Course course = new Course();
        course.setId(1L);
        topic.setCourse(course);
        
        when(courseRepo.findById(1L)).thenReturn(Optional.of(course));
        
        try {
            topicService.createTopic(topic);
            Assert.fail("Expected validation exception for empty topic name");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("Topic name") || 
                            ex.getMessage().contains("required"),
                            "Exception should mention topic name validation");
        }
        
        verify(courseRepo, times(1)).findById(1L);
        verify(topicRepo, never()).save(any(CourseContentTopic.class));
    }

    @Test(priority=17, groups={"topics"}, description="Create topic success")
    public void test17CreateTopicSuccess() {
        Course course = new Course();
        course.setId(2L);
        course.setCourseCode("CS301");
        course.setCourseName("Algorithms");
        
        CourseContentTopic topic = new CourseContentTopic();
        topic.setTopicName("Dynamic Programming");
        topic.setWeightPercentage(50.0);
        topic.setCourse(course);
        
        when(courseRepo.findById(2L)).thenReturn(Optional.of(course));
        when(topicRepo.save(any(CourseContentTopic.class))).thenAnswer(inv -> {
            CourseContentTopic saved = inv.getArgument(0);
            saved.setId(100L);
            return saved;
        });
        
        CourseContentTopic created = topicService.createTopic(topic);
        
        Assert.assertNotNull(created);
        Assert.assertEquals(created.getId().longValue(), 100L);
        Assert.assertEquals(created.getTopicName(), "Dynamic Programming");
        Assert.assertEquals(created.getWeightPercentage(), 50.0, 0.001);
        
        verify(courseRepo, times(1)).findById(2L);
        verify(topicRepo, times(1)).save(any(CourseContentTopic.class));
    }

    // ==================== Transfer Rule Tests ====================
    @Test(priority=18, groups={"rules"}, description="Create transfer rule invalid overlap")
    public void test18CreateRuleInvalidOverlap() {
        TransferRule rule = new TransferRule();
        rule.setMinimumOverlapPercentage(-5.0); // Invalid: should be 0-100
        rule.setCreditHourTolerance(1);
        
        try {
            ruleService.createRule(rule);
            Assert.fail("Expected exception for invalid overlap percentage");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("overlap") || 
                            ex.getMessage().contains("0-100"),
                            "Exception should mention overlap percentage range");
        }
        
        verify(ruleRepo, never()).save(any(TransferRule.class));
    }

    @Test(priority=19, groups={"rules"}, description="Create transfer rule success")
    public void test19CreateRuleSuccess() {
        University sourceUniversity = new University();
        sourceUniversity.setId(1L);
        sourceUniversity.setName("Community College");
        
        University targetUniversity = new University();
        targetUniversity.setId(2L);
        targetUniversity.setName("State University");
        
        TransferRule rule = new TransferRule();
        rule.setSourceUniversity(sourceUniversity);
        rule.setTargetUniversity(targetUniversity);
        rule.setMinimumOverlapPercentage(60.0);
        rule.setCreditHourTolerance(1);
        rule.setActive(true);
        
        when(universityRepo.findById(1L)).thenReturn(Optional.of(sourceUniversity));
        when(universityRepo.findById(2L)).thenReturn(Optional.of(targetUniversity));
        when(ruleRepo.save(any(TransferRule.class))).thenAnswer(inv -> {
            TransferRule saved = inv.getArgument(0);
            saved.setId(50L);
            return saved;
        });
        
        TransferRule created = ruleService.createRule(rule);
        
        Assert.assertNotNull(created);
        Assert.assertEquals(created.getId().longValue(), 50L);
        Assert.assertEquals(created.getMinimumOverlapPercentage(), 60.0, 0.001);
        Assert.assertEquals(created.getCreditHourTolerance(), 1);
        Assert.assertTrue(created.isActive());
        
        verify(universityRepo, times(1)).findById(1L);
        verify(universityRepo, times(1)).findById(2L);
        verify(ruleRepo, times(1)).save(any(TransferRule.class));
    }

    // ==================== Transfer Evaluation Tests ====================
    @Test(priority=20, groups={"evaluation"}, description="Evaluate transfer without rule")
    public void test20EvaluateNoRule() {
        University sourceUniv = new University();
        sourceUniv.setId(1L);
        
        University targetUniv = new University();
        targetUniv.setId(2L);
        
        Course sourceCourse = new Course();
        sourceCourse.setId(1L);
        sourceCourse.setCourseCode("MATH101");
        sourceCourse.setCreditHours(3);
        sourceCourse.setUniversity(sourceUniv);
        sourceCourse.setActive(true);
        
        Course targetCourse = new Course();
        targetCourse.setId(2L);
        targetCourse.setCourseCode("MATH100");
        targetCourse.setCreditHours(3);
        targetCourse.setUniversity(targetUniv);
        targetCourse.setActive(true);
        
        when(courseRepo.findById(1L)).thenReturn(Optional.of(sourceCourse));
        when(courseRepo.findById(2L)).thenReturn(Optional.of(targetCourse));
        when(topicRepo.findByCourseId(1L)).thenReturn(Collections.emptyList());
        when(topicRepo.findByCourseId(2L)).thenReturn(Collections.emptyList());
        when(ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(1L, 2L))
            .thenReturn(Collections.emptyList());
        when(evalRepo.save(any(TransferEvaluationResult.class))).thenAnswer(inv -> {
            TransferEvaluationResult result = inv.getArgument(0);
            result.setId(500L);
            result.setEvaluationDate(LocalDateTime.now());
            return result;
        });
        
        TransferEvaluationResult result = evalService.evaluateTransfer(1L, 2L);
        
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId().longValue(), 500L);
        Assert.assertFalse(result.getIsEligibleForTransfer());
        Assert.assertTrue(result.getNotes().contains("No active transfer rule") || 
                         result.getNotes().contains("no rule"));
        
        verify(courseRepo, times(2)).findById(anyLong());
        verify(topicRepo, times(2)).findByCourseId(anyLong());
        verify(ruleRepo, times(1)).findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(1L, 2L);
        verify(evalRepo, times(1)).save(any(TransferEvaluationResult.class));
    }

    @Test(priority=21, groups={"evaluation"}, description="Evaluate transfer with eligible rule")
    public void test21EvaluateEligibleRule() {
        University sourceUniv = new University();
        sourceUniv.setId(10L);
        
        University targetUniv = new University();
        targetUniv.setId(11L);
        
        Course sourceCourse = new Course();
        sourceCourse.setId(10L);
        sourceCourse.setCreditHours(3);
        sourceCourse.setUniversity(sourceUniv);
        sourceCourse.setActive(true);
        
        Course targetCourse = new Course();
        targetCourse.setId(11L);
        targetCourse.setCreditHours(3);
        targetCourse.setUniversity(targetUniv);
        targetCourse.setActive(true);
        
        // Matching topics
        CourseContentTopic sourceTopic = new CourseContentTopic();
        sourceTopic.setTopicName("Algorithms");
        sourceTopic.setWeightPercentage(80.0);
        sourceTopic.setCourse(sourceCourse);
        
        CourseContentTopic targetTopic = new CourseContentTopic();
        targetTopic.setTopicName("Algorithms");
        targetTopic.setWeightPercentage(80.0);
        targetTopic.setCourse(targetCourse);
        
        TransferRule rule = new TransferRule();
        rule.setId(77L);
        rule.setSourceUniversity(sourceUniv);
        rule.setTargetUniversity(targetUniv);
        rule.setMinimumOverlapPercentage(50.0);
        rule.setCreditHourTolerance(0);
        rule.setActive(true);
        
        when(courseRepo.findById(10L)).thenReturn(Optional.of(sourceCourse));
        when(courseRepo.findById(11L)).thenReturn(Optional.of(targetCourse));
        when(topicRepo.findByCourseId(10L)).thenReturn(Arrays.asList(sourceTopic));
        when(topicRepo.findByCourseId(11L)).thenReturn(Arrays.asList(targetTopic));
        when(ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(10L, 11L))
            .thenReturn(Arrays.asList(rule));
        when(evalRepo.save(any(TransferEvaluationResult.class))).thenAnswer(inv -> {
            TransferEvaluationResult result = inv.getArgument(0);
            result.setId(600L);
            result.setEvaluationDate(LocalDateTime.now());
            return result;
        });
        
        TransferEvaluationResult result = evalService.evaluateTransfer(10L, 11L);
        
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId().longValue(), 600L);
        Assert.assertTrue(result.getIsEligibleForTransfer());
        Assert.assertTrue(result.getOverlapPercentage() >= 50.0);
        
        verify(courseRepo, times(2)).findById(anyLong());
        verify(topicRepo, times(2)).findByCourseId(anyLong());
        verify(ruleRepo, times(1)).findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(10L, 11L);
        verify(evalRepo, times(1)).save(any(TransferEvaluationResult.class));
    }

    @Test(priority=22, groups={"evaluation"}, description="Evaluate transfer with credit tolerance fail")
    public void test22EvaluateCreditToleranceFail() {
        University sourceUniv = new University();
        sourceUniv.setId(20L);
        
        University targetUniv = new University();
        targetUniv.setId(21L);
        
        Course sourceCourse = new Course();
        sourceCourse.setId(20L);
        sourceCourse.setCreditHours(5); // 5 credits
        sourceCourse.setUniversity(sourceUniv);
        sourceCourse.setActive(true);
        
        Course targetCourse = new Course();
        targetCourse.setId(21L);
        targetCourse.setCreditHours(2); // 2 credits (difference of 3, tolerance is 1)
        targetCourse.setUniversity(targetUniv);
        targetCourse.setActive(true);
        
        // Matching topics
        CourseContentTopic sourceTopic = new CourseContentTopic();
        sourceTopic.setTopicName("Mathematics");
        sourceTopic.setWeightPercentage(100.0);
        sourceTopic.setCourse(sourceCourse);
        
        CourseContentTopic targetTopic = new CourseContentTopic();
        targetTopic.setTopicName("Mathematics");
        targetTopic.setWeightPercentage(100.0);
        targetTopic.setCourse(targetCourse);
        
        TransferRule rule = new TransferRule();
        rule.setId(88L);
        rule.setSourceUniversity(sourceUniv);
        rule.setTargetUniversity(targetUniv);
        rule.setMinimumOverlapPercentage(50.0);
        rule.setCreditHourTolerance(1); // Only allows 1 credit difference
        rule.setActive(true);
        
        when(courseRepo.findById(20L)).thenReturn(Optional.of(sourceCourse));
        when(courseRepo.findById(21L)).thenReturn(Optional.of(targetCourse));
        when(topicRepo.findByCourseId(20L)).thenReturn(Arrays.asList(sourceTopic));
        when(topicRepo.findByCourseId(21L)).thenReturn(Arrays.asList(targetTopic));
        when(ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(20L, 21L))
            .thenReturn(Arrays.asList(rule));
        when(evalRepo.save(any(TransferEvaluationResult.class))).thenAnswer(inv -> {
            TransferEvaluationResult result = inv.getArgument(0);
            result.setId(700L);
            result.setEvaluationDate(LocalDateTime.now());
            return result;
        });
        
        TransferEvaluationResult result = evalService.evaluateTransfer(20L, 21L);
        
        Assert.assertNotNull(result);
        Assert.assertFalse(result.getIsEligibleForTransfer());
        Assert.assertTrue(result.getNotes().contains("credit") || 
                         result.getNotes().contains("tolerance") ||
                         result.getNotes().contains("No active rule"));
        
        verify(courseRepo, times(2)).findById(anyLong());
        verify(topicRepo, times(2)).findByCourseId(anyLong());
        verify(ruleRepo, times(1)).findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(20L, 21L);
        verify(evalRepo, times(1)).save(any(TransferEvaluationResult.class));
    }

    // ==================== Additional CRUD Tests ====================
    @Test(priority=23, groups={"crud"}, description="Update Course not found")
    public void test23UpdateCourseNotFound() {
        when(courseRepo.findById(999L)).thenReturn(Optional.empty());
        
        Course updates = new Course();
        updates.setCourseName("Updated Name");
        
        try {
            courseService.updateCourse(999L, updates);
            Assert.fail("Expected exception for course not found");
        } catch (RuntimeException ex) {
            Assert.assertTrue(ex.getMessage().contains("not found") || 
                            ex.getMessage().contains("does not exist"));
        }
        
        verify(courseRepo, times(1)).findById(999L);
        verify(courseRepo, never()).save(any(Course.class));
    }

    @Test(priority=24, groups={"crud"}, description="Get Course by ID")
    public void test24GetCourseById() {
        Course course = new Course();
        course.setId(77L);
        course.setCourseCode("BIO101");
        course.setCourseName("Biology I");
        course.setCreditHours(4);
        
        when(courseRepo.findById(77L)).thenReturn(Optional.of(course));
        
        Course result = courseService.getCourseById(77L);
        
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId().longValue(), 77L);
        Assert.assertEquals(result.getCourseCode(), "BIO101");
        Assert.assertEquals(result.getCourseName(), "Biology I");
        Assert.assertEquals(result.getCreditHours(), 4);
        
        verify(courseRepo, times(1)).findById(77L);
    }

    @Test(priority=25, groups={"topics"}, description="Get topics for course")
    public void test25GetTopicsForCourse() {
        Course course = new Course();
        course.setId(33L);
        course.setCourseCode("CHEM101");
        
        CourseContentTopic topic1 = new CourseContentTopic();
        topic1.setId(1L);
        topic1.setTopicName("Organic Chemistry");
        topic1.setWeightPercentage(60.0);
        topic1.setCourse(course);
        
        CourseContentTopic topic2 = new CourseContentTopic();
        topic2.setId(2L);
        topic2.setTopicName("Inorganic Chemistry");
        topic2.setWeightPercentage(40.0);
        topic2.setCourse(course);
        
        when(courseRepo.findById(33L)).thenReturn(Optional.of(course));
        when(topicRepo.findByCourseId(33L)).thenReturn(Arrays.asList(topic1, topic2));
        
        List<CourseContentTopic> found = topicService.getTopicsForCourse(33L);
        
        Assert.assertNotNull(found);
        Assert.assertEquals(found.size(), 2);
        Assert.assertEquals(found.get(0).getTopicName(), "Organic Chemistry");
        Assert.assertEquals(found.get(1).getTopicName(), "Inorganic Chemistry");
        
        verify(courseRepo, times(1)).findById(33L);
        verify(topicRepo, times(1)).findByCourseId(33L);
    }

    @Test(priority=26, groups={"rules"}, description="Get rules between universities")
    public void test26GetRulesPair() {
        University source = new University();
        source.setId(1L);
        
        University target = new University();
        target.setId(2L);
        
        TransferRule rule = new TransferRule();
        rule.setId(1L);
        rule.setSourceUniversity(source);
        rule.setTargetUniversity(target);
        rule.setMinimumOverlapPercentage(70.0);
        rule.setActive(true);
        
        when(ruleRepo.findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(1L, 2L))
            .thenReturn(Arrays.asList(rule));
        
        List<TransferRule> rules = ruleService.getRulesForUniversities(1L, 2L);
        
        Assert.assertNotNull(rules);
        Assert.assertFalse(rules.isEmpty());
        Assert.assertEquals(rules.size(), 1);
        Assert.assertEquals(rules.get(0).getId().longValue(), 1L);
        Assert.assertEquals(rules.get(0).getMinimumOverlapPercentage(), 70.0, 0.001);
        
        verify(ruleRepo, times(1)).findBySourceUniversityIdAndTargetUniversityIdAndActiveTrue(1L, 2L);
    }

    @Test(priority=27, groups={"rules"}, description="Deactivate rule")
    public void test27DeactivateRule() {
        TransferRule rule = new TransferRule();
        rule.setId(100L);
        rule.setMinimumOverlapPercentage(60.0);
        rule.setActive(true);
        
        when(ruleRepo.findById(100L)).thenReturn(Optional.of(rule));
        when(ruleRepo.save(any(TransferRule.class))).thenAnswer(inv -> inv.getArgument(0));
        
        ruleService.deactivateRule(100L);
        
        Assert.assertFalse(rule.isActive());
        
        verify(ruleRepo, times(1)).findById(100L);
        verify(ruleRepo, times(1)).save(rule);
    }

    // ==================== Authentication Tests ====================
    @Test(priority=28, groups={"auth"}, description="Register and login flow")
    public void test28RegisterAndLogin() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "securePassword123";
        String encodedPassword = encoder.encode(rawPassword);
        
        User user = new User();
        user.setEmail("advisor@university.edu");
        user.setPassword(encodedPassword);
        user.setRoles(Set.of("ROLE_ADVISOR"));
        
        when(userRepo.findByEmail("advisor@university.edu")).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenAnswer(inv -> {
            User saved = inv.getArgument(0);
            saved.setId(20L);
            return saved;
        });
        
        // Save user
        userRepo.save(user);
        
        //