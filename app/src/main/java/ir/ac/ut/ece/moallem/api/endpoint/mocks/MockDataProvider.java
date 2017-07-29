package ir.ac.ut.ece.moallem.api.endpoint.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.ac.ut.ece.moallem.api.model.Category;
import ir.ac.ut.ece.moallem.api.model.Course;
import ir.ac.ut.ece.moallem.api.model.EducationProgram;
import ir.ac.ut.ece.moallem.api.model.Student;
import ir.ac.ut.ece.moallem.api.model.Teacher;
import ir.ac.ut.ece.moallem.api.model.UserReference;

/**
 * Created by mushtu on 7/10/17.
 */

public class MockDataProvider {

    public static Course hesaban() {
        Course hesaban = new Course("حسابان", "hesaban.jpg");
        hesaban.setId(1L);
        hesaban.setCategory(highSchoolCategory());
        return hesaban;
    }

    public static Course physics3() {
        Course physics3 = new Course("فیزیک ۳", "physics_3.jpg");
        physics3.setId(3L);
        physics3.setCategory(highSchoolCategory());
        return physics3;
    }

    public static Course jabr() {
        Course jabr = new Course("جبر و احتمال", "jabr.jpg");
        jabr.setId(2L);
        jabr.setCategory(highSchoolCategory());
        return jabr;
    }

    public static Course physics2() {
        Course physics2 = new Course("فیزیک ۲", "physics_2.jpg");
        physics2.setId(4L);
        physics2.setCategory(highSchoolCategory());
        return physics2;
    }

    public static Course operatingSystem() {
        Course course = new Course(5L, "سیستم عامل");
        course.setCategory(bscCategory());
        return course;
    }

    public static Course dm() {
        Course course = new Course(6L, "ریاضیات گسسته");
        course.setCategory(bscCategory());
        return course;
    }

    public static Course dld() {
        Course course = new Course(7L, "مدارهای منطقی");
        course.setCategory(bscCategory());
        return course;
    }

    public static Course mathPrimary2() {
        Course course = new Course(8L, "ریاضی(دوم دبستان)");
        course.setCategory(primarySchoolCategory());
        return course;
    }

    public static Course mathMiddle3() {
        Course course = new Course(9L, "ریاضی(سوم راهنمایی)");
        course.setCategory(middleSchoolCategory());
        return course;

    }


    public static Teacher teacherMojtaba() {
        Teacher mojtaba = new Teacher();
        mojtaba.setRating(1);
        mojtaba.setFirstName("مجتبی");
        mojtaba.setLastName("یزدانی");
        mojtaba.setId(1L);
        mojtaba.setMobile("09331340958");
        mojtaba.setAvatarUrl("mushtu.jpg");
        mojtaba.setAddress("تهران، ونک");
        UserReference reference = new UserReference();
        reference.setTeacher(mojtaba);
        reference.setId(1L);
        Student student1 = new Student();
        student1.setId(1L);
        student1.setAddress("تهران، سهروردی");
        student1.setFirstName("محمد");
        student1.setLastName("محمدی");
        reference.setStudent(student1);
        List<UserReference> mojtabaRef = new ArrayList<>();
        mojtabaRef.add(reference);
        mojtaba.setReferences(mojtabaRef);
        mojtaba.setRegisterTime(new Date().getTime());
        mojtaba.setShortDescription("سابقه ۵ سال تدریس ریاضیات پایه و فیزیک را دارم. جلسه دو ساعته ۵۰ تا ۶۰ تومن");
        mojtaba.setTeachingCourses(createMockCourses());
        return mojtaba;
    }


    public static Teacher teacherAli() {
        Teacher ali = new Teacher();
        ali.setRating(3);
        ali.setFirstName("علی");
        ali.setLastName("امیری");
        ali.setMobile("09366768774");
        ali.setAvatarUrl("iraj.jpg");
        ali.setAddress("تهران، امیراباد");
        ali.setRegisterTime(new Date().getTime());
        ali.setShortDescription("فارغ التحصیل دانشگاه تهران در رشته مهندسی فناوری اطلاعات هستم. دارای مدال برنز المپیاد ریاضی و ۳ سال سابقه تدریس ریاضیات پایه دارم. جلسه ۱.۵ ساعته ۷۰ تومن");
        ali.setTeachingCourses(createMockCourses());
        return ali;
    }

    public static Teacher teacherSajad() {
        Teacher sajad = new Teacher();
        sajad.setRating(2);
        sajad.setFirstName("سجاد");
        sajad.setLastName("کریمی");
        sajad.setMobile("09126785432");
        sajad.setAvatarUrl("sajad.jpg");
        sajad.setAddress("تهران، هفت تیر");
        sajad.setRegisterTime(new Date().getTime());
        sajad.setShortDescription("تخصصم در ارایه ی روش های تست زنی و نکات مهم درس برای مدت زمان کوتاه است. جلسه ۲ ساعته ۱۰۰ تومن");
        sajad.setTeachingCourses(createMockCourses());
        return sajad;
    }


    public static Student studentReza() {
        Student reza = new Student();
        reza.setRegisterTime(new Date().getTime());
        reza.setAddress("تهران، مجیدیه");
        reza.setFirstName("رضا");
        reza.setLastName("اسماعیل زاده");
        reza.setMobile("۰۹۱۲۶۷۵۴۳۲۳");
        reza.setId(1L);
        reza.setShortDescription("دانش آموز رشته ریاضی فیزیک هستم.");
        reza.setAvatarUrl("reza.jpg");
        return reza;
    }

    public static List<Teacher> createTeachers(long courseId) {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacherMojtaba());
        teachers.add(teacherAli());
        teachers.add(teacherSajad());
        return teachers;

    }


    public static List<Course> createMockCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(hesaban());
        courses.add(jabr());
        courses.add(physics2());
        courses.add(physics3());
        courses.add(operatingSystem());
        courses.add(dm());
        courses.add(dld());
        courses.add(mathMiddle3());
        courses.add(mathPrimary2());

        return courses;
    }

    public static List<EducationProgram> createMockedBscPrograms() {
        List<EducationProgram> programs = new ArrayList<>();
        EducationProgram p1 = new EducationProgram();
        p1.setId(1L);
        p1.setName("مهندسی کامپیوتر");
        EducationProgram p2 = new EducationProgram();
        p2.setId(2L);
        p2.setName("مهندسی برق");

        EducationProgram p3 = new EducationProgram();
        p3.setId(3L);
        p3.setName("ریاضی کاربردی");
        programs.add(p1);
        programs.add(p2);
        programs.add(p3);
        return programs;
    }

    public static Category createCategoriesTree() {
        Category root = new Category(1L, "root");
        Category education = new Category(2L, "تحصیلی", root);
        Category sport = new Category(3L, "ورزشی", root);
        Category cooking = new Category(4L, "آشپزی", root);
        Category art = new Category(5L, "هنری", root);
        root.addSubCategory(education);
        root.addSubCategory(sport);
        root.addSubCategory(cooking);
        root.addSubCategory(art);
        Category primarySchool = new Category(6L, "ابتدایی", education);
        Category middleSchool = new Category(7L, "راهنمایی", education);
        Category highSchool = new Category(8L, "دبیرستان", education);
        Category bsc = new Category(9L, "کارشناسی", education);
        Category master = new Category(10L, "کارشناسی ارشد", education);
        education.addSubCategory(primarySchool);
        education.addSubCategory(middleSchool);
        education.addSubCategory(highSchool);
        education.addSubCategory(bsc);
        education.addSubCategory(master);
        return root;
    }

    public static Category highSchoolCategory() {
        Category root = new Category(1L, "root");
        Category education = new Category(2L, "تحصیلی", root);
        Category sport = new Category(3L, "ورزشی", root);
        Category cooking = new Category(4L, "آشپزی", root);
        Category art = new Category(5L, "هنری", root);
        root.addSubCategory(education);
        root.addSubCategory(sport);
        root.addSubCategory(cooking);
        root.addSubCategory(art);
        Category primarySchool = new Category(6L, "ابتدایی", education);
        Category middleSchool = new Category(7L, "راهنمایی", education);
        Category highSchool = new Category(8L, "دبیرستان", education);
        Category bsc = new Category(9L, "کارشناسی", education);
        Category master = new Category(10L, "کارشناسی ارشد", education);
        education.addSubCategory(primarySchool);
        education.addSubCategory(middleSchool);
        education.addSubCategory(highSchool);
        education.addSubCategory(bsc);
        education.addSubCategory(master);
        return highSchool;
    }

    public static Category bscCategory() {
        Category root = new Category(1L, "root");
        Category education = new Category(2L, "تحصیلی", root);
        Category sport = new Category(3L, "ورزشی", root);
        Category cooking = new Category(4L, "آشپزی", root);
        Category art = new Category(5L, "هنری", root);
        root.addSubCategory(education);
        root.addSubCategory(sport);
        root.addSubCategory(cooking);
        root.addSubCategory(art);
        Category primarySchool = new Category(6L, "ابتدایی", education);
        Category middleSchool = new Category(7L, "راهنمایی", education);
        Category highSchool = new Category(8L, "دبیرستان", education);
        Category bsc = new Category(9L, "کارشناسی", education);
        Category master = new Category(10L, "کارشناسی ارشد", education);
        education.addSubCategory(primarySchool);
        education.addSubCategory(middleSchool);
        education.addSubCategory(highSchool);
        education.addSubCategory(bsc);
        education.addSubCategory(master);
        return bsc;
    }

    public static Category educationCategory() {
        Category root = new Category(1L, "root");
        Category education = new Category(2L, "تحصیلی", root);
        Category sport = new Category(3L, "ورزشی", root);
        Category cooking = new Category(4L, "آشپزی", root);
        Category art = new Category(5L, "هنری", root);
        root.addSubCategory(education);
        root.addSubCategory(sport);
        root.addSubCategory(cooking);
        root.addSubCategory(art);
        Category primarySchool = new Category(6L, "ابتدایی", education);
        Category middleSchool = new Category(7L, "راهنمایی", education);
        Category highSchool = new Category(8L, "دبیرستان", education);
        Category bsc = new Category(9L, "کارشناسی", education);
        Category master = new Category(10L, "کارشناسی ارشد", education);
        education.addSubCategory(primarySchool);
        education.addSubCategory(middleSchool);
        education.addSubCategory(highSchool);
        education.addSubCategory(bsc);
        education.addSubCategory(master);
        return education;
    }

    public static Category primarySchoolCategory() {
        Category root = new Category(1L, "root");
        Category education = new Category(2L, "تحصیلی", root);
        Category sport = new Category(3L, "ورزشی", root);
        Category cooking = new Category(4L, "آشپزی", root);
        Category art = new Category(5L, "هنری", root);
        root.addSubCategory(education);
        root.addSubCategory(sport);
        root.addSubCategory(cooking);
        root.addSubCategory(art);
        Category primarySchool = new Category(6L, "ابتدایی", education);
        Category middleSchool = new Category(7L, "راهنمایی", education);
        Category highSchool = new Category(8L, "دبیرستان", education);
        Category bsc = new Category(9L, "کارشناسی", education);
        Category master = new Category(10L, "کارشناسی ارشد", education);
        education.addSubCategory(primarySchool);
        education.addSubCategory(middleSchool);
        education.addSubCategory(highSchool);
        education.addSubCategory(bsc);
        education.addSubCategory(master);
        return primarySchool;
    }

    public static Category middleSchoolCategory() {
        Category root = new Category(1L, "root");
        Category education = new Category(2L, "تحصیلی", root);
        Category sport = new Category(3L, "ورزشی", root);
        Category cooking = new Category(4L, "آشپزی", root);
        Category art = new Category(5L, "هنری", root);
        root.addSubCategory(education);
        root.addSubCategory(sport);
        root.addSubCategory(cooking);
        root.addSubCategory(art);
        Category primarySchool = new Category(6L, "ابتدایی", education);
        Category middleSchool = new Category(7L, "راهنمایی", education);
        Category highSchool = new Category(8L, "دبیرستان", education);
        Category bsc = new Category(9L, "کارشناسی", education);
        Category master = new Category(10L, "کارشناسی ارشد", education);
        education.addSubCategory(primarySchool);
        education.addSubCategory(middleSchool);
        education.addSubCategory(highSchool);
        education.addSubCategory(bsc);
        education.addSubCategory(master);
        return middleSchool;
    }

    public static Course findCourse(long courseId) {
        for (Course course : MockDataProvider.createMockCourses()) {
            if (course.getId() == courseId)
                return course;
        }
        return null;
    }
}
