import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {

    int score = 0;

    @Override
    public void start(Stage stage) {

        // Load MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        TextField idField = new TextField();
        idField.setPromptText("Enter Student ID");

        Button startBtn = new Button("Start Quiz");
        Label msg = new Label();

        VBox root = new VBox(10);
        root.getChildren().addAll(idField, startBtn, msg);

        startBtn.setOnAction(e -> {
            String studentId = idField.getText().trim();
            if (checkStudent(studentId)) {
                int lastDigit = getLastDigit(studentId);
                showQuiz(stage, studentId, lastDigit);
            } else {
                msg.setText("Invalid Student ID");
            }
        });

        stage.setScene(new Scene(root, 400, 250));
        stage.setTitle("Student Quiz");
        stage.show();
    }

    // Determine the last digit of Student ID
    int getLastDigit(String studentId) {
        char last = studentId.charAt(studentId.length() - 1);
        if (Character.isDigit(last)) {
            return Character.getNumericValue(last);
        }
        return -1;
    }

    void showQuiz(Stage stage, String studentId, int topicDigit) {

        // Simple 2-question quiz per topic
        String q1Text = "";
        String q2Text = "";
        String[] q1Options = new String[4];
        String[] q2Options = new String[4];
        final int[] q1Answer = new int[1]; // final wrapper to use inside lambda
        final int[] q2Answer = new int[1];

        // Define questions based on last digit
        switch (topicDigit) {
            case 1: // Arithmetic
                q1Text = "১) ১০ + ৫ = ?";
                q1Options = new String[]{"১৫", "২০", "১০", "৫"};
                q1Answer[0] = 0;
                q2Text = "২) ১২ × ২ = ?";
                q2Options = new String[]{"২৪", "২২", "১৪", "২৬"};
                q2Answer[0] = 0;
                break;
            case 2: // Bangladesh Studies
                q1Text = "১) বাংলাদেশের স্বাধীনতা সাল কত?";
                q1Options = new String[]{"১৯৭১", "১৯৭৫", "১৯৬৫", "১৯৮১"};
                q1Answer[0] = 0;
                q2Text = "২) জাতীয় সংগীতের রচয়িতা কে?";
                q2Options = new String[]{"রবীন্দ্রনাথ ঠাকুর", "জয়নুল আবেদিন", "কাজী নজরুল ইসলাম", "সেলিনা হোসেন"};
                q2Answer[0] = 0;
                break;
            case 3: // Biology
                q1Text = "১) মানুষের হাড় কতটি?";
                q1Options = new String[]{"২০৬", "১৮৬", "১৫০", "২২৬"};
                q1Answer[0] = 0;
                q2Text = "২) রক্তের লাল রঙের উপাদান?";
                q2Options = new String[]{"হিমোগ্লোবিন", "প্লাজমা", "প্লেটলেট", "হেপাটাইটিস"};
                q2Answer[0] = 0;
                break;
            case 4: // Physics
                q1Text = "১) আলোর গতি কত?";
                q1Options = new String[]{"৩×10^৮ m/s", "৫×10^৮ m/s", "১×10^৮ m/s", "২×10^৮ m/s"};
                q1Answer[0] = 0;
                q2Text = "২) বৈদ্যুতিক কারেন্টের একক?";
                q2Options = new String[]{"আম্পিয়ার", "ওহম", "ভোল্ট", "জুল"};
                q2Answer[0] = 0;
                break;
            case 5: // Chemistry
                q1Text = "১) পানির রাসায়নিক সূত্র?";
                q1Options = new String[]{"H₂O", "CO₂", "NaCl", "O₂"};
                q1Answer[0] = 0;
                q2Text = "২) সোডিয়াম চিহ্ন?";
                q2Options = new String[]{"Na", "S", "K", "Mg"};
                q2Answer[0] = 0;
                break;
            case 6: // Bangla Grammar
                q1Text = "১) 'আমি' কোন অংশের শব্দ?";
                q1Options = new String[]{"সর্বনাম", "বিশেষ্য", "ক্রিয়া", "বিশেষণ"};
                q1Answer[0] = 0;
                q2Text = "২) 'দৌড়াল' কোন ক্রিয়াপদ?";
                q2Options = new String[]{"ক্রিয়া", "বিশেষ্য", "সর্বনাম", "বিশেষণ"};
                q2Answer[0] = 0;
                break;
            case 7: // Geography
                q1Text = "১) বিশ্বের সবচেয়ে বড় মহাসাগর?";
                q1Options = new String[]{"প্রশান্ত মহাসাগর", "অ্যাটলান্টিক", "ভারত মহাসাগর", "আর্কটিক"};
                q1Answer[0] = 0;
                q2Text = "২) বাংলাদেশের রাজধানী?";
                q2Options = new String[]{"ঢাকা", "চট্টগ্রাম", "খুলনা", "রাজশাহী"};
                q2Answer[0] = 0;
                break;
            case 8: // History
                q1Text = "১) ফ্রান্সে বিপ্লবের সাল?";
                q1Options = new String[]{"১৭৮৯", "১৭৬৫", "১৮০৪", "১৭৯৯"};
                q1Answer[0] = 0;
                q2Text = "২) মুঘল সম্রাট";
                q2Options = new String[]{"শাহজাহান", "আকবর", "বাবর", "হুমায়ুন"};
                q2Answer[0] = 0;
                break;
            case 9: // Economics
                q1Text = "১) মুদ্রার মূল্য নির্ধারণ করে কোন বিষয়?";
                q1Options = new String[]{"চাহিদা ও যোগান", "শিক্ষা", "অর্থনীতি নয়", "সরকারি নীতি নয়"};
                q1Answer[0] = 0;
                q2Text = "২) GDP মানে?";
                q2Options = new String[]{"মোট দেশীয় উৎপাদন", "জাতীয় আয়", "মুদ্রা", "বাজার মূল্য"};
                q2Answer[0] = 0;
                break;
            case 0: // International GK
                q1Text = "১) জাতিসংঘের সদর দপ্তর কোথায়?";
                q1Options = new String[]{"নিউ ইয়র্ক", "জেনেভা", "লন্ডন", "প্যারিস"};
                q1Answer[0] = 0;
                q2Text = "২) ইউরোপের সবচেয়ে বড় দেশ?";
                q2Options = new String[]{"রাশিয়া", "জার্মানি", "ফ্রান্স", "ইতালি"};
                q2Answer[0] = 0;
                break;
        }

        // Build Quiz GUI
        Label q1Label = new Label(q1Text);
        RadioButton[] q1Buttons = new RadioButton[4];
        ToggleGroup g1 = new ToggleGroup();
        for (int i = 0; i < 4; i++) {
            q1Buttons[i] = new RadioButton(q1Options[i]);
            q1Buttons[i].setToggleGroup(g1);
        }

        Label q2Label = new Label(q2Text);
        RadioButton[] q2Buttons = new RadioButton[4];
        ToggleGroup g2 = new ToggleGroup();
        for (int i = 0; i < 4; i++) {
            q2Buttons[i] = new RadioButton(q2Options[i]);
            q2Buttons[i].setToggleGroup(g2);
        }

        Button submit = new Button("Submit");

        submit.setOnAction(e -> {
            score = 0;
            if (q1Buttons[q1Answer[0]].isSelected()) score++;
            if (q2Buttons[q2Answer[0]].isSelected()) score++;

            saveResult(studentId, score);

            VBox resultBox = new VBox(10);
            resultBox.getChildren().add(new Label("Your Score: " + score));
            stage.setScene(new Scene(resultBox, 300, 200));
        });

        VBox quizBox = new VBox(10);
        quizBox.getChildren().addAll(
                q1Label, q1Buttons[0], q1Buttons[1], q1Buttons[2], q1Buttons[3],
                q2Label, q2Buttons[0], q2Buttons[1], q2Buttons[2], q2Buttons[3],
                submit
        );

        stage.setScene(new Scene(quizBox, 500, 400));
    }

    boolean checkStudent(String id) {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/quizdb",
                "root",
                "pookie"
        )) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM student WHERE LOWER(student_id)=LOWER(?)"
            );
            ps.setString(1, id.trim());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    void saveResult(String id, int score) {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/quizdb",
                "root",
                "pookie"
        )) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO result(student_id, score) VALUES (?, ?)"
            );
            ps.setString(1, id.trim());
            ps.setInt(2, score);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
