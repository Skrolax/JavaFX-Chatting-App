module com.socketprogramming.chattingapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.socketprogramming.chattingapp to javafx.fxml;
    exports com.socketprogramming.chattingapp;
}