package com.ohss.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DummyDataInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public DummyDataInitializer(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        
        // Dummy examiners & ADMIN SEEDER
        String[][] examiners = {
            {"Admin",    "User",     "admin@ohss.com",    "0000000000", "ADMIN"},
            {"Nikos",    "Zachos",    "nikos@ohss.com",    "2101111111", "EXAMINER"},
            {"Aggeliki", "Zachou", "aggeliki@ohss.com", "2102222222", "EXAMINER"},
            {"Dimitris", "Kalyvas", "dimitris@ohss.com", "2103333333", "EXAMINER"},
            {"Giannis", "Kalyvas", "giannis@ohss.com", "2104444444", "EXAMINER"}
        };

        for (String[] examiner : examiners) {
            jdbcTemplate.update(
                """
                INSERT INTO examiners
                (first_name, last_name, email, phone, password_hash, role)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                examiner[0],
                examiner[1],
                examiner[2],
                examiner[3],
                passwordEncoder.encode(examiner[2].split("@")[0] + "123"), // password: email prefix + "123"
                examiner[4]
            );
        }

        // Dummy sessions
        Object[][] sessions = {
            {"2026-04-10", "Attica", "1st Technical High School Athens", 2},
            {"2026-04-11", "Attica", "2nd Technical High School Athens", 3},
            {"2026-04-12", "Thessaloniki", "1st Technical High School Thessaloniki", 4},
            {"2026-04-13", "Patras", "Patras Technical Institute", 5}
        };

        for (Object[] session : sessions) {
            jdbcTemplate.update(
                """
                INSERT INTO sessions
                (date, region, school, examiner_id)
                VALUES (?, ?, ?, ?)
                """,
                session[0],
                session[1],
                session[2],
                session[3]
            );
        }

        String[] alcohol = {"NEVER","OCCASIONALLY","REGULARLY","UNKNOWN"};
        String[] brushing = {"ONCE_DAILY","TWICE_DAILY","THREE_OR_MORE","RARELY","UNKNOWN"};
        String[] smoking = {"CURRENT","FORMER","NEVER","UNKNOWN"};

        java.util.Random random = new java.util.Random();

        for (int sessionId = 1; sessionId <= 4; sessionId++) {

            for (int i = 0; i < 5; i++) {

                jdbcTemplate.update(
                    """
                    INSERT INTO examination
                    (alcohol_consumption, birth_year, brushing_frequency,
                    cardiovascular_disease, cpi, diabetes, dt, ft,
                    gender, last_visit, mt, smoking_status,
                    examiner_id, session_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """,
                    alcohol[random.nextInt(alcohol.length)],
                    2007 + random.nextInt(15),
                    brushing[random.nextInt(brushing.length)],
                    random.nextBoolean(),
                    random.nextInt(5),
                    random.nextBoolean(),
                    random.nextInt(6),
                    random.nextInt(4),
                    random.nextBoolean() ? "MALE" : "FEMALE",
                    java.time.LocalDateTime.now(),
                    random.nextInt(4),
                    smoking[random.nextInt(smoking.length)],
                    2 + random.nextInt(4),   // examiner_id
                    sessionId
                );
            }
        }

    }
}