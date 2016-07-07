package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            StackPane root = new StackPane(createTableView());

            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private EventHandler<KeyEvent> createOptionTableKeyEventHandler(final TableView tableView) {
        return new EventHandler<KeyEvent>() {

            @Override
            public void handle(final KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ENTER || keyEvent.getCode() == KeyCode.SPACE) {

                    final ComplexObject option = (ComplexObject) tableView.getSelectionModel().getSelectedItem();
                    if (option != null) {
                        option.setSomeBoolean(!option.getSomeBoolean());
                    }

                    keyEvent.consume();
                }
            }
        };
    }
    
    private TableView<ComplexObject> createTableView() {
        TableView<ComplexObject> tableView = new TableView<>();
        tableView.setOnKeyPressed(createOptionTableKeyEventHandler(tableView));
        
        TableColumn column1 = new TableColumn<>("Boolean");
        column1.setCellValueFactory(new PropertyValueFactory<ComplexObject, Boolean>("someBoolean"));
        column1.setCellFactory(CheckBoxTableCell.forTableColumn(column1));
        column1.setEditable(true);
        
        TableColumn column2 = new TableColumn<>("String");
        column2.setCellValueFactory(new PropertyValueFactory<ComplexObject, Boolean>("someText"));
        column2.setCellFactory(createNameCellFactory());
        
//        tableView.setOnMouseClicked(
//                new EventHandler<Event>() {
//                       @Override
//                       public void handle(Event event) {
//            System.out.println("Hallo!"); 
//            }
//        });

    // column2.addEventHandler(eventType, eventHandler);

    tableView.getColumns().addAll(column1,column2);tableView.setEditable(true);

    tableView.setItems(

    createItemList());
        return tableView;
    }
    protected Callback<TableColumn<ComplexObject, String>, TableCell<ComplexObject, String>> createNameCellFactory() {
        return new Callback<TableColumn<ComplexObject, String>, TableCell<ComplexObject, String>>() {

            @Override
            public TableCell<ComplexObject, String> call(final TableColumn<ComplexObject, String> param) {

                return new TableCell<ComplexObject, String>() {

                    @Override
                    protected void updateItem(final String item, final boolean empty) {

                        super.updateItem(item, empty);
                        if (!empty) {
                            setText(item);
                            setOnMouseClicked(new EventHandler<Event>() {
                                    @Override
                                    public void handle(final Event mouseEvent) {
                                        final ComplexObject option = (ComplexObject) getTableRow().getItem();                                 
                                        option.setSomeBoolean(!option.getSomeBoolean());
                                    }
                                });
                        }
                    }
                };
            }
        };
    }
    public class ComplexObject {

        public ComplexObject(Boolean pBoolean, String pString) {
            this.someBoolean = pBoolean;
            this.someText = pString;
        }

        private Boolean someBoolean;
        private String someText;

        public Boolean getSomeBoolean() {
            return someBoolean;
        }

        public void setSomeBoolean(Boolean someBoolean) {
            this.someBoolean = someBoolean;
        }

        public String getSomeText() {
            return someText;
        }

        public void setSomeText(String someText) {
            this.someText = someText;
        }

    }

    private ObservableList<ComplexObject> createItemList() {
        ObservableList<ComplexObject> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.add(new ComplexObject(Boolean.TRUE, "EintragEins"));
        observableArrayList.add(new ComplexObject(Boolean.FALSE, "EintragZwei"));
        observableArrayList.add(new ComplexObject(Boolean.FALSE, "EintragDrei"));
        
        return observableArrayList;
    }
}
